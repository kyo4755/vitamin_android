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
import com.vitamin.wecantalk.Adapter.CommunityRoomListViewAdapter;
import com.vitamin.wecantalk.Common.Config;
import com.vitamin.wecantalk.Common.GlobalInfo;
import com.vitamin.wecantalk.POJO.CommunityRoomListViewPOJO;
import com.vitamin.wecantalk.R;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by JongHwa on 2018-04-17.
 */

public class CommunityRoomActivity extends AppCompatActivity {

    ListView listView;
    CommunityRoomListViewAdapter adapter;
    Button sendBtn, backBtn;
    EditText userMsg;
    TextView titleName;

    byte[] img;
    String name;
    String anid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_room);

        Intent it = getIntent();
        img = it.getByteArrayExtra("img");
        name = it.getStringExtra("name");
        anid = it.getStringExtra("anid");
        String recent_msg = it.getStringExtra("recent_msg");
        String recent_time = it.getStringExtra("recent_time");

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

                AQuery aQuery = new AQuery(CommunityRoomActivity.this);
                String chat_send_url = Config.Server_URL + "chat_send";

                Map<String, Object> params = new LinkedHashMap<>();

                params.put("myid", GlobalInfo.my_profile.getId());
                params.put("anid", anid);

                aQuery.ajax(chat_send_url, params, String.class, new AjaxCallback<String>() {
                    @Override
                    public void callback(String url, String result, AjaxStatus status) {

                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            String result_code = jsonObject.get("result").toString();
                            if(result_code.equals("0000")){
                            }else{
                                Toast.makeText(getApplicationContext(), "오류.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("chat_send Error", e.toString());
                        }


//                            String userSendMsg = userMsg.getText().toString();
//                if(userSendMsg.length() != 0) {
//                    CommunityRoomListViewPOJO pojo = new CommunityRoomListViewPOJO();
//                    pojo.setMsg(userSendMsg);
//                    String time = new SimpleDateFormat("HH:mm").format(new Date(System.currentTimeMillis()));
//                    pojo.setTime(time);
//
//                    adapter.addList(pojo);
//
//                    userMsg.setText("");
//                }
                    }
                });
            }
        });
    }

    private ArrayList<CommunityRoomListViewPOJO> createPOJO(){
        ArrayList<CommunityRoomListViewPOJO> list = new ArrayList<>();

        CommunityRoomListViewPOJO pojo = new CommunityRoomListViewPOJO();
        pojo.setName(name);
        pojo.setMsg("Nice to meet you!");
        pojo.setTime("12:39");
        pojo.setWhere(1);

        list.add(pojo);

        pojo = new CommunityRoomListViewPOJO();
        pojo.setImg(null);
        pojo.setName(null);
        pojo.setMsg("Where are you from?");
        pojo.setTime("12:40");
        pojo.setWhere(2);

        list.add(pojo);

        pojo = new CommunityRoomListViewPOJO();
//        pojo.setName(name);
//        pojo.setMsg(recent_msg);
//        pojo.setTime(recent_time);
        pojo.setWhere(1);

        list.add(pojo);

        return list;
    }
}
