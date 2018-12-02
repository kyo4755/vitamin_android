package com.vitamin.wecantalk.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.vitamin.wecantalk.Common.Config;
import com.vitamin.wecantalk.Common.GlobalInfo;
import com.vitamin.wecantalk.POJO.FriendsListViewPOJO;
import com.vitamin.wecantalk.POJO.FriendsRecognitionListViewPOJO;
import com.vitamin.wecantalk.R;
import com.vitamin.wecantalk.UIActivity.FindIdActivity;
import com.vitamin.wecantalk.UIActivity.FindImgActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class FriendsRecognitionListViewAdapter extends BaseAdapter {

    private ArrayList<FriendsRecognitionListViewPOJO> listViewItemList = new ArrayList<FriendsRecognitionListViewPOJO>();

    public FriendsRecognitionListViewAdapter(){
        listViewItemList = new ArrayList<>();
    }

    @Override
    public int getCount(){
        return listViewItemList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.prefab_find_img_friends_listview,parent,false);
        }


        ImageView iconImageView = convertView.findViewById(R.id.imageView1);
        TextView titleTextView = convertView.findViewById(R.id.textView1);
        TextView similarTextView = convertView.findViewById(R.id.textView2);
        ImageButton imageButton = convertView.findViewById(R.id.addbtn);

        final FriendsRecognitionListViewPOJO friendsRecognitionListViewPOJO = listViewItemList.get(position);




        if(friendsRecognitionListViewPOJO.getImage().equals("null")){
            Glide.with(context)
                    .load(R.drawable.default_user)
                    .centerCrop()
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .bitmapTransform(new CropCircleTransformation(context))
                    .into(iconImageView);
        }
        else{
            String imgStr = Config.Server_URL + "users/getPhoto?id=" + friendsRecognitionListViewPOJO.getImage();
            Glide.with(context)
                    .load(imgStr)
                    .centerCrop()
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .bitmapTransform(new CropCircleTransformation(context))
                    .into(iconImageView);
        }

        imageButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // 사진 선택
                AQuery aQuery = new AQuery(context);
                String add_friend_url=Config.Server_URL + "friends/add";
                Map<String, Object> params = new LinkedHashMap<>();
                params.put("myid",GlobalInfo.my_profile.getId());
                params.put("anid",friendsRecognitionListViewPOJO.getId());
                aQuery.ajax(add_friend_url, params, String.class, new AjaxCallback<String>() {
                    @Override
                    public void callback(String url, String result, AjaxStatus status) {
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            String result_code = jsonObject.get("result").toString();
                            if (result_code.equals("0000")) {//정상 나올때
                                Toast.makeText(context, "친구등록 성공", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "친구등록 에러", Toast.LENGTH_SHORT).show();
                            }
                                /*}else if(result_code.equals("0001")){//anid 안보냈을때

                                }else if(result_code.equals("0002")){//검색결과업승ㄹ때

                                }else if(result_code.equals("0100")){//get으로 보냇ㅇㄹ대

                                }*/

                        } catch (Exception e) {

                        }
                    }
                });
            }
        });


        similarTextView.setText(friendsRecognitionListViewPOJO.getSimilarity());
        titleTextView.setText(friendsRecognitionListViewPOJO.getName());

        return convertView;

    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public Object getItem(int position){
        return listViewItemList.get(position);
    }

    public void setList(ArrayList<FriendsRecognitionListViewPOJO> list){
        this.listViewItemList = list;
        notifyDataSetChanged();
    }

}
