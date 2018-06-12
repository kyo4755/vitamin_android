package com.vitamin.wecantalk.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.bumptech.glide.Glide;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.vitamin.wecantalk.Adapter.CommunityListViewAdapter;
import com.vitamin.wecantalk.Common.Config;
import com.vitamin.wecantalk.Common.GlobalInfo;
import com.vitamin.wecantalk.MyFirebaseInstanceIDService;
import com.vitamin.wecantalk.UIActivity.CommunityRoomActivity;
import com.vitamin.wecantalk.POJO.CommunityListViewPOJO;
import com.vitamin.wecantalk.R;
import com.vitamin.wecantalk.UIActivity.StartActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by JongHwa on 2018-04-13.
 */

public class CommunityFragment extends Fragment {
    Context context;

    ListView listView;
    CommunityListViewAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context = container.getContext();
        View view = inflater.inflate(R.layout.fragment_community, null);

        listView = view.findViewById(R.id.community_listview);

        adapter = new CommunityListViewAdapter();
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CommunityListViewPOJO pojo = (CommunityListViewPOJO) adapter.getItem(i);

                Intent it = new Intent(getActivity(), CommunityRoomActivity.class);
                ArrayList<String> mem = new ArrayList<>();
                mem.add(pojo.getAnid());
                mem.add(GlobalInfo.my_profile.getId());
                String member="";
                for (String s : mem){
                    member += "," + s;
                }
                member=member.substring(0, member.length()-1);
                it.putExtra("member", member);
                it.putExtra("room_num", pojo.getRoom_number());
                it.putExtra("title", pojo.getTitle());

                startActivity(it);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, final View view, int i, long l) {
                final CommunityListViewPOJO pojo = (CommunityListViewPOJO) adapter.getItem(i);

                final AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("AlertDialog Title");
                builder.setMessage("대화방을 나가시겠습니까?");
                builder.setPositiveButton("예",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                AQuery aQuery = new AQuery(context);
                                String chattings_delete_url = Config.Server_URL + "chattings/delete";

                                Map<String, Object> params = new LinkedHashMap<>();

                                params.put("id", GlobalInfo.my_profile.getId());
                                params.put("room_number", pojo.getRoom_number());

                                aQuery.ajax(chattings_delete_url, params, String.class, new AjaxCallback<String>() {
                                    @Override
                                    public void callback(String url, String result, AjaxStatus status) {

                                        try {
                                            JSONObject jsonObject = new JSONObject(result);
                                            String result_code = jsonObject.get("result").toString();
                                            if (result_code.equals("0000")) {
                                                Toast.makeText(context, "정상.", Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (Exception e) {
                                            Toast.makeText(context, "서버와의 통신 중 오류가 발생했습니다. 나중에 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                                            Log.e("LoginTask", e.toString());
                                        }
                                    }
                                });
                            }
                        });
                builder.setNegativeButton("아니오",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                builder.show();
                return true;
            }
        });

        createPOJO();

        return view;
    }

    private void createPOJO() {

        //final ArrayList<CommunityListViewPOJO> list = new ArrayList<>();

        AQuery aQuery = new AQuery(context);
        String chattings_list_url = Config.Server_URL + "chattings/getList";

        Map<String, Object> params = new LinkedHashMap<>();

        params.put("id", GlobalInfo.my_profile.getId());

        aQuery.ajax(chattings_list_url, params, String.class, new AjaxCallback<String>() {
            @Override
            public void callback(String url, String result, AjaxStatus status) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String result_code = jsonObject.get("result").toString();
                    if (result_code.equals("0000")) {
                        Toast.makeText(context, "정상.", Toast.LENGTH_SHORT).show();
                        String temp_str = jsonObject.get("chat_list").toString();
                        JSONArray jsonArray = new JSONArray(jsonObject.get("chat_list").toString());
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jObject = jsonArray.getJSONObject(i);
                            String room_num = jObject.getString("room_num");
                            String date = jObject.getString("date");
                            String msg = jObject.getString("msg");
                            String friends_detail = jObject.getString("friends_detail");

                            ArrayList<String> mem = new ArrayList<>();
                            JSONArray jsonArray2 = new JSONArray(friends_detail);
                            for (int j = 0; j < jsonArray2.length(); j++) {
                                JSONObject jObject2 = jsonArray2.getJSONObject(j);
                                String id = jObject2.getString("id");
                                if(id.equals(GlobalInfo.my_profile.getId())) continue;

                                String f_name = jObject2.getString("name");
                                String img = jObject2.getString("image");

                                String title = "";
                                mem.add(f_name);
                                Collections.sort(mem);
                                for (String s : mem){
                                    title += s + ",";
                                }
                                title=title.substring(0, title.length()-1);

                                CommunityListViewPOJO pojo = new CommunityListViewPOJO();
                                pojo.setAnid(id);
                                pojo.setImg(img);
                                pojo.setTitle(title);
                                pojo.setRecent_time(date);
                                pojo.setRecent_msg(msg);
                                pojo.setRoom_number(room_num);

                                adapter.addItem(pojo);

                                Log.d("asdf","title :"+pojo.getTitle());
                                Log.d("asdf","id :"+pojo.getAnid());
                                Log.d("asdf","room_num :"+pojo.getRoom_number());
                                Log.d("asdf","msg :" +pojo.getRecent_msg());
                            }
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
