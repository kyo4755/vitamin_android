package com.vitamin.wecantalk.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.vitamin.wecantalk.Common.Config;
import com.vitamin.wecantalk.POJO.CommunityListViewPOJO;
import com.vitamin.wecantalk.POJO.FriendsListViewPOJO;
import com.vitamin.wecantalk.POJO.SnsListViewPOJO;
import com.vitamin.wecantalk.R;
import com.vitamin.wecantalk.UIActivity.CommentActivity;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class SnsListViewAdapter extends BaseAdapter {


    private ArrayList<SnsListViewPOJO> listViewItemSns;

    public void setArItem(ArrayList<SnsListViewPOJO> listViewItemSns) {this.listViewItemSns = listViewItemSns;}

    public SnsListViewAdapter() {
        listViewItemSns = new ArrayList<SnsListViewPOJO>();
    }

    @Override
    public int getCount() {
        return listViewItemSns.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.prefab_sns_listview, parent, false);
        }


        final ImageView heart_black = convertView.findViewById(R.id.sns_heart_black);
        final ImageView heart_red = convertView.findViewById(R.id.sns_heart_red);
        heart_black.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                heart_black.setVisibility(View.GONE);
                heart_red.setVisibility(View.VISIBLE);
            }
        });
        heart_red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                heart_black.setVisibility(View.VISIBLE);
                heart_red.setVisibility(View.GONE);
            }
        });

        TextView sns_count = convertView.findViewById(R.id.sns_count);
        TextView sns_name = convertView.findViewById(R.id.sns_name);
        TextView sns_date = convertView.findViewById(R.id.sns_date);
        TextView sns_context = convertView.findViewById(R.id.sns_context);
        ImageView sns_image = convertView.findViewById(R.id.sns_image);
        ImageView sns_profile = convertView.findViewById(R.id.sns_profile);

        final SnsListViewPOJO listViewItem = listViewItemSns.get(position);

        ImageView sns_comment = convertView.findViewById(R.id.sns_comment);

        sns_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(context, CommentActivity.class);
                it.putExtra("index",listViewItem.getIndex());
                context.startActivity(it);
            }
        });

        if(listViewItem.getUser_image().equals("null")) {
            Glide.with(context)
                    .load(R.drawable.default_user)
                    .centerCrop()
                    .bitmapTransform(new CropCircleTransformation(context))
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(sns_profile);
        }
        else {
            String imgPro = Config.Server_URL + "users/getPhoto?id=" + listViewItem.getUser_image();
            Glide.with(context)
                    .load(imgPro)
                    .centerCrop()
                    .bitmapTransform(new CropCircleTransformation(context))
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(sns_profile);
        }

        if(listViewItem.getContent_image().equals("null")) {
            sns_image.setVisibility(View.GONE);
        }
        else{
            String imgCon = Config.Server_URL + "sns/getPhoto?id=" + listViewItem.getContent_image();
            Glide.with(context)
                    .load(imgCon)
                    .centerCrop()
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(sns_image);
        }

        sns_name.setText(listViewItem.getName());
        sns_date.setText(listViewItem.getDate());
        sns_context.setText(listViewItem.getContent_text());
        sns_count.setText(listViewItem.getComment_count());

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return listViewItemSns.get(position);
    }

    public void addItem(SnsListViewPOJO pojo){
        listViewItemSns.add(pojo);
        notifyDataSetChanged();
    }


}
