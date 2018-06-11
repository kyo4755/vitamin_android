package com.vitamin.wecantalk.UIActivity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vitamin.wecantalk.Adapter.CommunityRoomListViewAdapter;
import com.vitamin.wecantalk.Common.Config;
import com.vitamin.wecantalk.Common.GlobalInfo;
import com.vitamin.wecantalk.POJO.CommunityListViewPOJO;
import com.vitamin.wecantalk.POJO.CommunityRoomListViewPOJO;
import com.vitamin.wecantalk.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
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
        //listView.scrollTo();
        //listView.setTranscriptMode(listView.TRANSCRIPT_MODE_ALWAYS_SCROLL);

        sendBtn = findViewById(R.id.community_room_send_button);
        userMsg = findViewById(R.id.community_room_edittext);

        titleName = findViewById(R.id.community_room_name_title);
        titleName.setText(name);

        //BroadcastReceiver mReceiver=null;
        LocalBroadCastReceiver mReceiver = null;

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

                final String userSendMsg = userMsg.getText().toString();
                if (userSendMsg.length() != 0) {

                    AQuery aQuery = new AQuery(CommunityRoomActivity.this);
                    String chattings_send_url = Config.Server_URL + "chattings/send";

                    Map<String, Object> params = new LinkedHashMap<>();

                    params.put("room_num", room_number);
                    params.put("myid", GlobalInfo.my_profile.getId());
                    params.put("msg", userSendMsg);

                    final String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()));
                    params.put("date", date);

                    aQuery.ajax(chattings_send_url, params, String.class, new AjaxCallback<String>() {
                        @Override
                        public void callback(String url, String result, AjaxStatus status) {

                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                String result_code = jsonObject.get("result").toString();
                                if (result_code.equals("0000")) {
                                    CommunityRoomListViewPOJO pojo=new CommunityRoomListViewPOJO();
                                    pojo.setId(GlobalInfo.my_profile.getId());
                                    pojo.setName(GlobalInfo.my_profile.getName());
                                    pojo.setImg(GlobalInfo.my_profile.getImage());
                                    pojo.setMsg(userSendMsg);
                                    pojo.setWhere(2);

                                    SimpleDateFormat original_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    SimpleDateFormat cut_format = new SimpleDateFormat("HH:mm");
                                    Date origin_date = original_format.parse(date);
                                    String new_date = cut_format.format(origin_date);
                                    pojo.setTime(new_date);

                                    adapter.addList(pojo);
                                    listView.smoothScrollToPosition(adapter.getCount());
                                    //listView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
                                } else {
                                    Toast.makeText(getApplicationContext(), "오류.", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                Log.e("chat_send Error", e.toString());
                                Toast.makeText(getApplicationContext(), "오류.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    userMsg.setText("");
                }
            }
        });

        //IntentFilter intentFilter = new IntentFilter("CUSTOM_EVENT");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("CUSTOM_EVENT");
        mReceiver = new LocalBroadCastReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, intentFilter);
        //registerReceiver(mReceiver, intentFilter);
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
                            try{
                                SimpleDateFormat original_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                SimpleDateFormat cut_format = new SimpleDateFormat("HH:mm");
                                Date origin_date = original_format.parse(date);
                                String new_date = cut_format.format(origin_date);
                                pojo.setTime(new_date);
                            }
                            catch (ParseException e){

                            }
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
//
//    class AscendingObj implements Comparator<CommunityRoomListViewPOJO>{
//
//        @Override
//        public int compare(CommunityRoomListViewPOJO o1, CommunityRoomListViewPOJO o2) {
//
//            return o1.getTime().compareTo()
//        }
//    }

    public class LocalBroadCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String data = intent.getStringExtra("data");
            Log.e("getData", "data : " + data);
            try {
                JSONObject jsonObject = new JSONObject(data);
                String id = jsonObject.getString("id");
                String date = jsonObject.getString("date");
                String msg = jsonObject.getString("msg");
                String name = jsonObject.getString("name");
                String img = jsonObject.getString("image");

                CommunityRoomListViewPOJO pojo=new CommunityRoomListViewPOJO();
                pojo.setId(id);
                Log.e("getData",id);
                pojo.setName(name);
                Log.e("getData",name);
                pojo.setImg(img);
                Log.e("getData",img);
                pojo.setMsg(msg);
                Log.e("getData",msg);
                try{
                    SimpleDateFormat original_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    SimpleDateFormat cut_format = new SimpleDateFormat("HH:mm");
                    Date origin_date = original_format.parse(date);
                    String new_date = cut_format.format(origin_date);
                    pojo.setTime(new_date);
                }
                catch (ParseException e){

                }
                if(pojo.getId().equals(GlobalInfo.my_profile.getId())){ pojo.setWhere(2);}
                else{pojo.setWhere(1);}

                adapter.addList(pojo);
                listView.smoothScrollToPosition(adapter.getCount());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
