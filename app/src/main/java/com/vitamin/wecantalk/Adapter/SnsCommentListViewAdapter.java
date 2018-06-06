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

import com.vitamin.wecantalk.POJO.SnsCommentListViewPOJO;
import com.vitamin.wecantalk.POJO.SnsListViewPOJO;
import com.vitamin.wecantalk.R;
import com.vitamin.wecantalk.UIActivity.CommentActivity;

import java.util.ArrayList;

public class SnsCommentListViewAdapter extends BaseAdapter {

    private ArrayList<SnsCommentListViewPOJO> listViewItemList;

    public SnsCommentListViewAdapter() {
        listViewItemList = new ArrayList<SnsCommentListViewPOJO>();
    }

    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.prefab_sns_comment_listview, parent, false);
        }

        ImageView sns_profile = convertView.findViewById(R.id.commentid_img);
        TextView sns_name = convertView.findViewById(R.id.write_name);
        TextView sns_date = convertView.findViewById(R.id.write_time);
        TextView sns_context = convertView.findViewById(R.id.write_content);

        SnsCommentListViewPOJO listViewItem = listViewItemList.get(position);

        sns_profile.setImageDrawable(listViewItem.getPofile());
        sns_name.setText(listViewItem.getName());
        sns_date.setText(listViewItem.getDate());
        sns_context.setText(listViewItem.getContext());


        return convertView;

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    public void addItem(Drawable pofile, String name, String date, String context) {
        SnsCommentListViewPOJO item = new SnsCommentListViewPOJO();

        item.setPofile(pofile);
        item.setName(name);
        item.setDate(date);
        item.setContext(context);

        listViewItemList.add(item);
    }
}
