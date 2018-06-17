package com.vitamin.wecantalk.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.vitamin.wecantalk.Adapter.FriendsListViewAdapter;
import com.vitamin.wecantalk.Common.Config;
import com.vitamin.wecantalk.Common.GlobalInfo;
import com.vitamin.wecantalk.POJO.FriendsListViewPOJO;
import com.vitamin.wecantalk.R;
import com.vitamin.wecantalk.UIActivity.CommunityRoomActivity;
import com.vitamin.wecantalk.UIActivity.FindIdActivity;
import com.vitamin.wecantalk.UIActivity.StartActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by JongHwa on 2018-04-13.
 */

public class FriendsFragment extends Fragment {

    ListView listview;
    FriendsListViewAdapter adapter;
    ImageView my_img;
    TextView my_name, my_status_msg;

    Context context;
    Button b1;
    Button b2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = container.getContext();
        View view = inflater.inflate(R.layout.fragment_friends, null);

        adapter = new FriendsListViewAdapter();
        getFriendsList();

        listview = view.findViewById(R.id.listview1);
        listview.setAdapter(adapter);

        b1 = view.findViewById(R.id.test_find_friend);
        b2 = view.findViewById(R.id.test_find_id_friend);

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), FindIdActivity.class);
                startActivity(it);
            }
        });


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FriendsProfileFragment fragment = FriendsProfileFragment.newInstance(10, 5, false, false, (FriendsListViewPOJO) adapter.getItem(i));
                fragment.show(getFragmentManager(), "blur_sample");
            }
        });

        my_img = view.findViewById(R.id.friend_my_img);
        my_name = view.findViewById(R.id.friend_my_name);
        my_status_msg = view.findViewById(R.id.friend_my_status_msg);

        if (GlobalInfo.my_profile.getImage().equals("null")) {
            Glide.with(container.getContext())
                    .load(R.drawable.default_user)
                    .centerCrop()
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .bitmapTransform(new CropCircleTransformation(container.getContext()))
                    .into(my_img);
        } else {
            String imgStr = Config.Server_URL + "users/getPhoto?id=" + GlobalInfo.my_profile.getImage();
            Glide.with(container.getContext())
                    .load(imgStr)
                    .centerCrop()
                    .bitmapTransform(new CropCircleTransformation(container.getContext()))
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(my_img);
        }

        my_name.setText(GlobalInfo.my_profile.getName());

        if (GlobalInfo.my_profile.getStatus_msg().equals("null")) {
            my_status_msg.setVisibility(View.INVISIBLE);
        } else {
            my_status_msg.setText(GlobalInfo.my_profile.getStatus_msg());
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getFriendsList();
        setMyStatus();
    }

    private void getFriendsList(){
        AQuery aQuery = new AQuery(context);
        String friends_list_url = Config.Server_URL + "friends/getList";

        Map<String, Object> params = new LinkedHashMap<>();

        params.put("id", GlobalInfo.my_profile.getId());

        aQuery.ajax(friends_list_url, params, String.class, new AjaxCallback<String>() {
            @Override
            public void callback(String url, String result, AjaxStatus status) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String result_code = jsonObject.get("result").toString();

                    if (result_code.equals("0001"))
                        Toast.makeText(context, "아이디를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    else {
                        GlobalInfo.friends_list = new ArrayList<>();
                        JSONArray jsonArray = new JSONArray(jsonObject.get("friends_list").toString());
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jObject = jsonArray.getJSONObject(i);
                            FriendsListViewPOJO pojo = new FriendsListViewPOJO();
                            pojo.setId(jObject.getString("id"));
                            pojo.setName(jObject.getString("name"));
                            pojo.setEmail(jObject.getString("email"));
                            pojo.setNation(jObject.getString("nation"));
                            pojo.setLocation(jObject.getString("location"));
                            pojo.setPrefer_language(jObject.getString("prefer_language"));
                            pojo.setStatus_msg(jObject.getString("status_msg"));
                            pojo.setPhone_number(jObject.getString("phone_number"));
                            pojo.setImage(jObject.get("image").toString());

                            GlobalInfo.friends_list.add(pojo);
                        }

                        adapter.setList(GlobalInfo.friends_list);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setMyStatus(){
        my_status_msg.setText(GlobalInfo.my_profile.getStatus_msg());
        my_name.setText(GlobalInfo.my_profile.getName());
    }
}

