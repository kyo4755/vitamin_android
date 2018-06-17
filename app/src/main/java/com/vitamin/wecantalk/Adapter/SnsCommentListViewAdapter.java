package com.vitamin.wecantalk.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vitamin.wecantalk.Common.Config;
import com.vitamin.wecantalk.POJO.SnsCommentListViewPOJO;
import com.vitamin.wecantalk.POJO.SnsListViewPOJO;
import com.vitamin.wecantalk.R;
import com.vitamin.wecantalk.UIActivity.CommentActivity;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class SnsCommentListViewAdapter extends BaseAdapter {

    private ArrayList<SnsCommentListViewPOJO> listViewItemListComment = new ArrayList<SnsCommentListViewPOJO>();

    public void setArItem(ArrayList<SnsCommentListViewPOJO> listViewItemListComment) {this.listViewItemListComment = listViewItemListComment;}

    public SnsCommentListViewAdapter() {
        listViewItemListComment = new ArrayList<SnsCommentListViewPOJO>();
    }

    @Override
    public int getCount() {
        return listViewItemListComment.size();
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.prefab_sns_comment_listview, parent, false);
        }

        ImageView comment_img = convertView.findViewById(R.id.commentid_img);
        TextView comment_name = convertView.findViewById(R.id.write_name);
        TextView comment_date = convertView.findViewById(R.id.write_time);
        TextView comment_msg = convertView.findViewById(R.id.write_content);

        SnsCommentListViewPOJO commentlistViewItem = listViewItemListComment.get(position);

        if(commentlistViewItem.getComment_user_image().equals("null")) {
            Glide.with(context)
                    .load(R.drawable.default_user)
                    .centerCrop()
                    .bitmapTransform(new CropCircleTransformation(context))
                    .into(comment_img);
        }
        else {
            String imgPro = Config.Server_URL + "users/getPhoto?id=" + commentlistViewItem.getComment_user_image();
            Glide.with(context)
                    .load(imgPro)
                    .centerCrop()
                    .bitmapTransform(new CropCircleTransformation(context))
                    .into(comment_img);
        }

        comment_name.setText(commentlistViewItem.getComment_id());
        comment_date.setText(commentlistViewItem.getComment_date());
        comment_msg.setText(commentlistViewItem.getComment_msg());

        return convertView;

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return listViewItemListComment.get(position);
    }

    public void addItem(SnsCommentListViewPOJO pojo){
        listViewItemListComment.add(pojo);
        notifyDataSetChanged();
    }
}
