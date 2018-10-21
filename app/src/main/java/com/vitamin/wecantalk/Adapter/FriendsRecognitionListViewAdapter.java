package com.vitamin.wecantalk.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.vitamin.wecantalk.Common.Config;
import com.vitamin.wecantalk.Common.GlobalInfo;
import com.vitamin.wecantalk.POJO.FriendsListViewPOJO;
import com.vitamin.wecantalk.POJO.FriendsRecognitionListViewPOJO;
import com.vitamin.wecantalk.R;

import java.util.ArrayList;

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

        FriendsRecognitionListViewPOJO friendsRecognitionListViewPOJO = listViewItemList.get(position);

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
