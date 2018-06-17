package com.vitamin.wecantalk.UIActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.vitamin.wecantalk.Adapter.SnsListViewAdapter;
import com.vitamin.wecantalk.Common.Config;
import com.vitamin.wecantalk.POJO.SnsListViewPOJO;
import com.vitamin.wecantalk.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.androidquery.util.AQUtility.getContext;

/**
 * Created by anemo on 2018-06-15.
 */

public class FriendsSnsActivity extends AppCompatActivity {

//    Context context;
    ListView listView;
    SnsListViewAdapter adapter;

    TextView who;

    ImageView comment;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_sns);

        Intent it = getIntent();
        String str = it.getExtras().getString("id");   //프로필 id값 받아오기!!


//        getContext();




        listView = findViewById(R.id.pro_sns_listview);
        adapter = new SnsListViewAdapter();
        listView.setAdapter(adapter);

        who =(TextView)findViewById(R.id.who);
        who.setText(str);

        snscreatePOJO(str);

    }

    private void snscreatePOJO(String str) {

        //final ArrayList<CommunityListViewPOJO> list = new ArrayList<>();

        AQuery aQuery = new AQuery(FriendsSnsActivity.this);
        String sns_list_url = Config.Server_URL + "sns/getFriendSNSList";

        Map<String, Object> params = new LinkedHashMap<>();


        params.put("id",str);


        aQuery.ajax(sns_list_url, params, String.class, new AjaxCallback<String>() {
            @Override
            public void callback(String url, String result, AjaxStatus status) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String result_code = jsonObject.get("result").toString();
                    if (result_code.equals("0000")) {
                        String temp_sns = jsonObject.get("sns_list").toString();
                        String name = jsonObject.getString("name");
                        String image = jsonObject.getString("image");
                        JSONArray jsonArray = new JSONArray(jsonObject.get("sns_list").toString());
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jObject = jsonArray.getJSONObject(i);
                            String index = jObject.getString("index");
                            String date = jObject.getString("date");
                            String content_text = jObject.getString("content_text");
                            String content_image = jObject.getString("content_image");
                            String comment_count = jObject.getString("comment_count");

                            SnsListViewPOJO pojo = new SnsListViewPOJO();

                            pojo.setUser_image(image);
                            pojo.setIndex(index);
                            pojo.setName(name);
                            pojo.setDate(date);
                            pojo.setContent_text(content_text);
                            pojo.setContent_image(content_image);
                            pojo.setComment_count(comment_count);

                            adapter.addItem(pojo);

                        }
                    }

                    else if(result_code.equals("0001")) Toast.makeText(FriendsSnsActivity.this, "입력받은 ID가 없음.", Toast.LENGTH_SHORT).show();
                    else if(result_code.equals("0002")) Toast.makeText(FriendsSnsActivity.this, "채팅방이 없음 왕따쓰.", Toast.LENGTH_SHORT).show();
                    else{}

                } catch (Exception e) {

                }
            }
        });
    }



}
