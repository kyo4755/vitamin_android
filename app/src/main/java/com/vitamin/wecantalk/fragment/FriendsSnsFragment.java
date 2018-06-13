package com.vitamin.wecantalk.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import com.vitamin.wecantalk.Adapter.SnsListViewAdapter;
import com.vitamin.wecantalk.Adapter.StudyBestMatchListViewAdapter;
import com.vitamin.wecantalk.Common.Config;
import com.vitamin.wecantalk.Common.GlobalInfo;
import com.vitamin.wecantalk.POJO.CommunityListViewPOJO;
import com.vitamin.wecantalk.POJO.SnsListViewPOJO;
import com.vitamin.wecantalk.R;
import com.vitamin.wecantalk.UIActivity.CommentActivity;
import com.vitamin.wecantalk.UIActivity.FindIdActivity;
import com.vitamin.wecantalk.UIActivity.MainFragmentActivity;
import com.vitamin.wecantalk.UIActivity.PostingActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Created by JongHwa on 2018-04-13.
 */

public class FriendsSnsFragment extends Fragment {
    Context context;

    ListView listView;
    SnsListViewAdapter adapter;

    ImageView posting;
    ImageView find;
    ImageView comment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context = container.getContext();
        View view = inflater.inflate(R.layout.fragment_sns, null);

        listView = view.findViewById(R.id.sns_listview);
        adapter = new SnsListViewAdapter();
        listView.setAdapter(adapter);



        posting = view.findViewById(R.id.sns_posting);
        find = view.findViewById(R.id.sns_find);



        posting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), PostingActivity.class);
                startActivity(it);
            }
        });

        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), FindIdActivity.class);
                startActivity(it);
            }
        });
        snscreatePOJO();
        return view;
    }


    private void snscreatePOJO() {

        //final ArrayList<CommunityListViewPOJO> list = new ArrayList<>();

        AQuery aQuery = new AQuery(context);
        String sns_list_url = Config.Server_URL + "sns/getList";

        Map<String, Object> params = new LinkedHashMap<>();

        params.put("startNum", 1);
        params.put("endNum", 10);


        aQuery.ajax(sns_list_url, params, String.class, new AjaxCallback<String>() {
            @Override
            public void callback(String url, String result, AjaxStatus status) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String result_code = jsonObject.get("result").toString();
                    if (result_code.equals("0000")) {
                        Toast.makeText(context, "정상.", Toast.LENGTH_SHORT).show();
                        String temp_sns = jsonObject.get("sns_list").toString();
                        JSONArray jsonArray = new JSONArray(jsonObject.get("sns_list").toString());
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jObject = jsonArray.getJSONObject(i);
                            String index = jObject.getString("index");
                            String id = jObject.getString("id");
                            System.out.println(id);
                            String name = jObject.getString("name");
                            String user_image = jObject.getString("user_image");
                            String prefer_language = jObject.getString("prefer_language");
                            String date = jObject.getString("date");
                            String content_text = jObject.getString("content_text");
                            String content_image = jObject.getString("content_image");
                            String comment_count = jObject.getString("comment_count");

                            SnsListViewPOJO pojo = new SnsListViewPOJO();

                            pojo.setIndex(index);
                            pojo.setId(id);
                            pojo.setName(name);
                            pojo.setUser_image(user_image);
                            pojo.setPrefer_language(prefer_language);
                            pojo.setDate(date);
                            pojo.setContent_text(content_text);
                            pojo.setContent_image(content_image);
                            pojo.setComment_count(comment_count);


                            adapter.addItem(pojo);



                        }
                    }

                    else if(result_code.equals("0001")) Toast.makeText(context, "입력받은 ID가 없음.", Toast.LENGTH_SHORT).show();
                    else if(result_code.equals("0002")) Toast.makeText(context, "채팅방이 없음 왕따쓰.", Toast.LENGTH_SHORT).show();
                    else{}

                } catch (Exception e) {

                }
            }
        });
    }




}
