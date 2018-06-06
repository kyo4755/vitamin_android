package com.vitamin.wecantalk.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vitamin.wecantalk.Common.Config;
import com.vitamin.wecantalk.POJO.CommunityListViewPOJO;
import com.vitamin.wecantalk.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by JongHwa on 2018-04-15.
 */

public class CommunityListViewAdapter extends BaseAdapter {

    private ArrayList<CommunityListViewPOJO> list;

    public CommunityListViewAdapter(){
        list = new ArrayList<CommunityListViewPOJO>();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Context mContext = viewGroup.getContext();

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.prefab_community_listview, viewGroup, false);
        }

        ImageView img = view.findViewById(R.id.community_img);
        TextView room_title = view.findViewById(R.id.community_room_title);
        TextView recent_msg = view.findViewById(R.id.community_last_msg);
        TextView recent_time = view.findViewById(R.id.community_last_time);

        CommunityListViewPOJO communityListViewPOJO = list.get(i);

        String image_code=communityListViewPOJO.getImg();

        String imgURL = Config.Server_URL + "user_photo?id=" + image_code;

        if(image_code==null){
            Glide.with(mContext)
                    .load(R.drawable.default_user)
                    .centerCrop()
                    .bitmapTransform(new CropCircleTransformation(mContext))
                    .into(img);
        }else{
            Glide.with(mContext)
                    .load(imgURL)
                    .centerCrop()
                    .bitmapTransform(new CropCircleTransformation(mContext))
                    .into(img);
        }

        room_title.setText(list.get(i).getTitle());
        recent_msg.setText(list.get(i).getRecent_msg());
        recent_time.setText(list.get(i).getRecent_time());

        return view;
    }

    public void addItem(CommunityListViewPOJO pojo){
        list.add(pojo);
        notifyDataSetChanged();
    }
}
