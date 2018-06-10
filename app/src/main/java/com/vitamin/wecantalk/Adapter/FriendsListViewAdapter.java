package com.vitamin.wecantalk.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vitamin.wecantalk.Common.Config;
import com.vitamin.wecantalk.Common.GlobalInfo;
import com.vitamin.wecantalk.POJO.FriendsListViewPOJO;
import com.vitamin.wecantalk.R;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class FriendsListViewAdapter extends BaseAdapter {

    private ArrayList<FriendsListViewPOJO> listViewItemList = new ArrayList<FriendsListViewPOJO>();

    public FriendsListViewAdapter(){
        listViewItemList = GlobalInfo.friends_list;
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
            convertView = inflater.inflate(R.layout.prefab_friends_listview,parent,false);
        }
        ImageView iconImageView = convertView.findViewById(R.id.imageView1);
        TextView titleTextView = convertView.findViewById(R.id.textView1);
        TextView descTextView = convertView.findViewById(R.id.textView2);

        FriendsListViewPOJO friendsListViewPOJO = listViewItemList.get(position);

        if(friendsListViewPOJO.getImage().equals("null")){
            Glide.with(context)
                    .load(R.drawable.default_user)
                    .centerCrop()
                    .bitmapTransform(new CropCircleTransformation(context))
                    .into(iconImageView);
        }
        else{
            String imgStr = Config.Server_URL + "users/getPhoto?id=" + friendsListViewPOJO.getImage();
            Glide.with(context)
                    .load(imgStr)
                    .centerCrop()
                    .bitmapTransform(new CropCircleTransformation(context))
                    .into(iconImageView);
        }

        titleTextView.setText(friendsListViewPOJO.getName());
        if(friendsListViewPOJO.getStatus_msg().equals("null")){
            descTextView.setVisibility(View.INVISIBLE);
        }
        else   descTextView.setText(friendsListViewPOJO.getStatus_msg());

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

}
