package com.vitamin.wecantalk.Adapter;

import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vitamin.wecantalk.POJO.CommunityListViewPOJO;
import com.vitamin.wecantalk.R;

import java.util.ArrayList;

/**
 * Created by JongHwa on 2018-04-15.
 */

public class CommunityListViewAdapter extends BaseAdapter {

    private ArrayList<CommunityListViewPOJO> list;

    public CommunityListViewAdapter(ArrayList<CommunityListViewPOJO> list){
        this.list = list;
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
        final Context mContext = viewGroup.getContext();

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.community_listview_prefab, viewGroup, false);
        }

        final ImageView img = view.findViewById(R.id.community_img);
        TextView room_title = view.findViewById(R.id.community_room_title);
        TextView recent_msg = view.findViewById(R.id.community_last_msg);
        TextView recent_time = view.findViewById(R.id.community_last_time);

        img.setImageDrawable(list.get(i).getImg());
        img.setBackground(new ShapeDrawable(new OvalShape()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            img.setClipToOutline(true);
        }

        room_title.setText(list.get(i).getTitle());
        recent_msg.setText(list.get(i).getRecent_msg());
        recent_time.setText(list.get(i).getRecent_time());

        return view;
    }

    public void addItem(CommunityListViewPOJO pojo){
        list.add(pojo);
    }
}
