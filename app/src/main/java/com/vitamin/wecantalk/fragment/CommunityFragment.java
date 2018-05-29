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
import com.vitamin.wecantalk.Adapter.CommunityListViewAdapter;
import com.vitamin.wecantalk.Common.Config;
import com.vitamin.wecantalk.Common.GlobalInfo;
import com.vitamin.wecantalk.UIActivity.CommunityRoomActivity;
import com.vitamin.wecantalk.POJO.CommunityListViewPOJO;
import com.vitamin.wecantalk.R;
import com.vitamin.wecantalk.UIActivity.StartActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
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

        adapter = new CommunityListViewAdapter(createPOJO());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CommunityListViewPOJO pojo = (CommunityListViewPOJO) adapter.getItem(i);

                Intent it = new Intent(getActivity(), CommunityRoomActivity.class);
                it.putExtra("img",pojo.getImg());
                it.putExtra("name", pojo.getTitle());
                it.putExtra("anid", pojo.getAnid());
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
                                String chat_delete_url = Config.Server_URL + "chat_delete";

                                Map<String, Object> params = new LinkedHashMap<>();

                                params.put("id", GlobalInfo.my_profile.getId());
                                params.put("anid", pojo.getAnid());

                                aQuery.ajax(chat_delete_url, params, String.class, new AjaxCallback<String>() {
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
        return view;
    }

    private ArrayList<CommunityListViewPOJO> createPOJO() {

        final ArrayList<CommunityListViewPOJO> list = new ArrayList<>();

        AQuery aQuery = new AQuery(context);
        String chat_list_url = Config.Server_URL + "chat_list";

        Map<String, Object> params = new LinkedHashMap<>();

        params.put("id", GlobalInfo.my_profile.getId());

        aQuery.ajax(chat_list_url, params, String.class, new AjaxCallback<String>() {
            @Override
            public void callback(String url, String result, AjaxStatus status) {

                try {

                    JSONObject jsonObject = new JSONObject(result);
                    String result_code = jsonObject.get("result").toString();

                    if (result_code.equals("0000")) {
                        Toast.makeText(context, "정상.", Toast.LENGTH_SHORT).show();

                        JSONArray jsonArray = new JSONArray(jsonObject.get("chat_list").toString());
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jObject = jsonArray.getJSONObject(i);
                            String anid = jObject.getString("anid");
                            String recent_msg = jObject.getString("recent_msg");
                            String image_code=jObject.getString("img");
                            JSONObject detail_jObject = new JSONObject(recent_msg);
                            String f_name = detail_jObject.getString("f_name");
                            String date_now = detail_jObject.getString("date_now");
                            String time_now = detail_jObject.getString("time_now");
                            String msg = detail_jObject.getString("msg");

                            String title = f_name;

                            CommunityListViewPOJO pojo = new CommunityListViewPOJO();
                            pojo.setAnid(anid);
                            pojo.setImg(image_code);
                            pojo.setTitle(title);
                            pojo.setRecent_time(time_now);
                            pojo.setRecent_msg(msg);

                            list.add(pojo);
                        }
                    }

                    else if(result_code.equals("0001")) Toast.makeText(context, "입력받은 ID가 없음.", Toast.LENGTH_SHORT).show();
                    else if(result_code.equals("0002")) Toast.makeText(context, "채팅방이 없음 왕따쓰.", Toast.LENGTH_SHORT).show();
                    else{}

                } catch (Exception e) {

                }
            }
        });
        return list;
    }
}
