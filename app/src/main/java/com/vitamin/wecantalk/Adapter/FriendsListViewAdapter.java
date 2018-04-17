package com.vitamin.wecantalk.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vitamin.wecantalk.POJO.FriendsListViewPOJO;
import com.vitamin.wecantalk.R;

import java.util.ArrayList;

public class FriendsListViewAdapter extends BaseAdapter {

    private ArrayList<FriendsListViewPOJO> listViewItemList = new ArrayList<FriendsListViewPOJO>();

    public FriendsListViewAdapter(){

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


        iconImageView.setImageDrawable(friendsListViewPOJO.getIcon());
        titleTextView.setText(friendsListViewPOJO.getTitle());
        descTextView.setText(friendsListViewPOJO.getDesc());

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

    public void addItem(Drawable icon,String title, String desc){
        FriendsListViewPOJO item = new FriendsListViewPOJO();

        item.setIcon(icon);
        item.setTitle(title);
        item.setDesc(desc);

        listViewItemList.add(item);
    }
}
