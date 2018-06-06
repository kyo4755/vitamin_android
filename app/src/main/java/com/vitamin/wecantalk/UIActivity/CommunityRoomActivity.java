package com.vitamin.wecantalk.UIActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.vitamin.wecantalk.Adapter.CommunityRoomListViewAdapter;
import com.vitamin.wecantalk.Common.Config;
import com.vitamin.wecantalk.Common.GlobalInfo;
import com.vitamin.wecantalk.POJO.CommunityListViewPOJO;
import com.vitamin.wecantalk.POJO.CommunityRoomListViewPOJO;
import com.vitamin.wecantalk.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by JongHwa on 2018-04-17.
 */

public class CommunityRoomActivity extends AppCompatActivity {

    ListView listView;
    CommunityRoomListViewAdapter adapter;
    Button sendBtn, backBtn;
    EditText userMsg;
    TextView titleName;

    String img;
    String name;
    String member;
    ArrayList<String> mem = new ArrayList<>();
    String room_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_room);

        Intent it = getIntent();
        member = it.getStringExtra("member");
        room_number=it.getStringExtra("room_num");

        listView = findViewById(R.id.community_room_listview);
        listView.setDivider(null);
        adapter = new CommunityRoomListViewAdapter(createPOJO());
        listView.setAdapter(adapter);

        sendBtn = findViewById(R.id.community_room_send_button);
        userMsg = findViewById(R.id.community_room_edittext);
        titleName = findViewById(R.id.community_room_name_title);
        titleName.setText(name);


        backBtn = findViewById(R.id.community_room_back_button);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userSendMsg = userMsg.getText().toString();
                if (userSendMsg.length() != 0) {

                    AQuery aQuery = new AQuery(CommunityRoomActivity.this);
                    String chattings_send_url = Config.Server_URL + "chattings/send";

                    Map<String, Object> params = new LinkedHashMap<>();

                    params.put("room_num", room_number);
                    params.put("myid", GlobalInfo.my_profile.getId());
                    params.put("msg", userMsg.getText());
                    params.put("date", new SimpleDateFormat("yyyy/MM/dd/HH:mm:ss").format(new Date(System.currentTimeMillis())));


                    aQuery.ajax(chattings_send_url, params, String.class, new AjaxCallback<String>() {
                        @Override
                        public void callback(String url, String result, AjaxStatus status) {

                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                String result_code = jsonObject.get("result").toString();
                                if (result_code.equals("0000")) {
                                } else {
                                    Toast.makeText(getApplicationContext(), "오류.", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                Log.e("chat_send Error", e.toString());
                                Toast.makeText(getApplicationContext(), "오류.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });

    }

    private ArrayList<CommunityRoomListViewPOJO> createPOJO(){
        final ArrayList<CommunityRoomListViewPOJO> list = new ArrayList<>();

        AQuery aQuery = new AQuery(getApplicationContext());
        String chattings_load_url = Config.Server_URL + "chattings/load";

        Map<String, Object> params = new LinkedHashMap<>();

        params.put("room_num", room_number);

        aQuery.ajax(chattings_load_url, params, String.class, new AjaxCallback<String>() {
            @Override
            public void callback(String url, String result, AjaxStatus status) {

                try {

                    JSONObject jsonObject = new JSONObject(result);
                    String result_code = jsonObject.get("result").toString();
                    if (result_code.equals("0000")) {
                        Toast.makeText(getApplicationContext(), "정상.", Toast.LENGTH_SHORT).show();
                        JSONArray jsonArray = new JSONArray(jsonObject.get("chat_load").toString());
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jObject = jsonArray.getJSONObject(i);
                            String id = jObject.getString("id");
                            String date = jObject.getString("date");
                            String msg = jObject.getString("msg");
                            String name = jObject.getString("name");
                            String img = jObject.getString("image");

                            CommunityRoomListViewPOJO pojo=new CommunityRoomListViewPOJO();
                            pojo.setId(id);
                            pojo.setName(name);
                            pojo.setImg(img);
                            pojo.setMsg(msg);
                            pojo.setTime(date);
                            if(pojo.getId().equals(GlobalInfo.my_profile.getId())){ pojo.setWhere(2);}
                            else{pojo.setWhere(1);}

                            list.add(pojo);
                        }
                    }

                    else if(result_code.equals("0001")) Toast.makeText(getApplicationContext(), "0001에러", Toast.LENGTH_SHORT).show();
                    else if(result_code.equals("0002")) Toast.makeText(getApplicationContext(), "0002에러", Toast.LENGTH_SHORT).show();
                    else{}

                } catch (Exception e) {

                }
            }
        });
        return list;

    }

//   chattings/recent
//    AQuery aQuery = new AQuery(getApplicationContext());
//    String chattings_recent_url = Config.Server_URL + "chattings/recent";
//
//    Map<String, Object> params = new LinkedHashMap<>();
//
//        params.put("id", GlobalInfo.my_profile.getId());
//
//        aQuery.ajax(chattings_recent_url, params, String.class, new AjaxCallback<String>() {
//        @Override
//        public void callback(String url, String result, AjaxStatus status) {
//
//            try {
//
//                JSONObject jsonObject = new JSONObject(result);
//                String result_code = jsonObject.get("result").toString();
//                if (result_code.equals("0000")) {
//                    Toast.makeText(getApplicationContext(), "정상.", Toast.LENGTH_SHORT).show();
//                    JSONArray jsonArray = new JSONArray(jsonObject.get("chat_list").toString());
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject jObject = jsonArray.getJSONObject(i);
//                        String anid = jObject.getString("id");
//                        String msg_detail = jObject.getString("msg_detail");
//                        String image_code=jObject.getString("img");
//                        String room_number=jObject.getString("room_number");
//                        JSONObject detail_jObject = new JSONObject(msg_detail);
//                        String f_name = detail_jObject.getString("f_name");
//                        String id = detail_jObject.getString("id");
//                        String img = detail_jObject.getString("img");
//                        String time_now = detail_jObject.getString("time_now");
//                        String msg = detail_jObject.getString("msg");
//
//                        String title = f_name;
//
//                        CommunityRoomListViewPOJO pojo=new CommunityRoomListViewPOJO();
//                        pojo.setId(anid);
//                        pojo.setImg(image_code);
//                        pojo.setName(title);
//                        pojo.setMsg(time_now);
//                        pojo.setTime(msg);
//                        if(pojo.getId().equals(GlobalInfo.my_profile.getId())){ pojo.setWhere(2);}
//                        else{pojo.setWhere(1);}
//
//                        list.add(pojo);
//                    }
//                }
//
//                else if(result_code.equals("0001")) Toast.makeText(getApplicationContext(), "입력받은 ID가 없음.", Toast.LENGTH_SHORT).show();
//                else if(result_code.equals("0002")) Toast.makeText(getApplicationContext(), "채팅방이 없음 왕따쓰.", Toast.LENGTH_SHORT).show();
//                else{}
//
//            } catch (Exception e) {
//
//            }
//        }
//    });
//        return list;

}
