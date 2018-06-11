package com.vitamin.wecantalk.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vitamin.wecantalk.Common.Config;
import com.vitamin.wecantalk.POJO.CommunityListViewPOJO;
import com.vitamin.wecantalk.POJO.FriendsListViewPOJO;
import com.vitamin.wecantalk.POJO.SnsListViewPOJO;
import com.vitamin.wecantalk.R;
import com.vitamin.wecantalk.UIActivity.CommentActivity;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class SnsListViewAdapter extends BaseAdapter {


    private ArrayList<SnsListViewPOJO> listViewItemSns = new ArrayList<SnsListViewPOJO>();

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

        TextView sns_name = convertView.findViewById(R.id.sns_name);
        TextView sns_date = convertView.findViewById(R.id.sns_date);
        TextView sns_context = convertView.findViewById(R.id.sns_context);
        ImageView sns_image = convertView.findViewById(R.id.sns_image);
        ImageView sns_profile = convertView.findViewById(R.id.sns_profile);

        SnsListViewPOJO listViewItem = listViewItemSns.get(position);



        ImageView sns_comment = convertView.findViewById(R.id.sns_comment);

        sns_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(context, CommentActivity.class);
                context.startActivity(it);
            }
        });

        SnsListViewPOJO snsListViewPOJO = listViewItemSns.get(position);

        if(snsListViewPOJO.getUser_image().equals("null")){
            sns_image.setVisibility(View.INVISIBLE);
        }
        else{
            String imgPro = Config.Server_URL + "users/getPhoto?id=" + snsListViewPOJO.getUser_image();
            Glide.with(context)
                    .load(imgPro)
                    .centerCrop()
                    .bitmapTransform(new CropCircleTransformation(context))
                    .into(sns_profile);

            String imgCon = Config.Server_URL + "sns/getPhoto?id=" + snsListViewPOJO.getContent_image();
            Glide.with(context)
                    .load(imgCon)
                    .centerCrop()
                    .bitmapTransform(new CropCircleTransformation(context))
                    .into(sns_image);



        }

        sns_name.setText(listViewItem.getName());
        sns_date.setText(listViewItem.getDate());
        sns_context.setText(listViewItem.getContent_text());





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
