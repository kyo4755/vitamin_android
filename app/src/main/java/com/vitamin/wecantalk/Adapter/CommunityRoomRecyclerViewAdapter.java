package com.vitamin.wecantalk.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.vitamin.wecantalk.Common.Config;
import com.vitamin.wecantalk.POJO.CommunityRoomListViewPOJO;
import com.vitamin.wecantalk.R;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by Kim Jong-Hwa on 2018-06-12.
 */

public class CommunityRoomRecyclerViewAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder> {

    private static final int VIEW_LEFT = 1;
    private static final int VIEW_RIGHT = 2;

    private ArrayList<CommunityRoomListViewPOJO> list;

    private Context mContext;

    public CommunityRoomRecyclerViewAdapter(Context context){
        this.mContext = context;
        list = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if (viewType == VIEW_LEFT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.prefab_community_room_listview_receive, parent, false);
            return new ReceiveMessageHolder(view);
        }
        else if (viewType == VIEW_RIGHT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.prefab_community_room_listview_send, parent, false);
            return new SendMessageHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final CommunityRoomListViewPOJO data= list.get(position);

        switch (holder.getItemViewType()) {
            case VIEW_LEFT:
                ((ReceiveMessageHolder)holder).bind(data);
                break;
            case VIEW_RIGHT:
                ((SendMessageHolder)holder).bind(data);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int pos){
        return list.get(pos).getWhere();
    }

    private class ReceiveMessageHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView name;
        TextView msg;
        TextView time;

        public ReceiveMessageHolder(View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.prefab_community_room_receive_img);
            name = itemView.findViewById(R.id.prefab_community_room_receive_name);
            msg = itemView.findViewById(R.id.prefab_community_room_receive_msg);
            time = itemView.findViewById(R.id.prefab_community_room_receive_time);
        }

        void bind(CommunityRoomListViewPOJO data) {
            String imgStr = Config.Server_URL + "users/getPhoto?id=" + data.getImg();

            if(data.getImg().equals("null")){
                Glide.with(mContext)
                        .load(R.drawable.default_user)
                        .centerCrop()
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .bitmapTransform(new CropCircleTransformation(mContext))
                        .into(img);
            } else {
                Glide.with(mContext)
                        .load(imgStr)
                        .centerCrop()
                        .bitmapTransform(new CropCircleTransformation(mContext))
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .error(R.drawable.default_user)
                        .into(img);
            }

            name.setText(data.getName());
            msg.setText(data.getMsg());
            time.setText(data.getTime());
        }
    }

    private class SendMessageHolder extends RecyclerView.ViewHolder {

        TextView msg;
        TextView time;

        public SendMessageHolder(View itemView) {
            super(itemView);

            msg = itemView.findViewById(R.id.prefab_community_room_send_msg);
            time = itemView.findViewById(R.id.prefab_community_room_send_time);
        }

        void bind(CommunityRoomListViewPOJO data) {
            msg.setText(data.getMsg());
            time.setText(data.getTime());
        }
    }

    public void setAllData(ArrayList<CommunityRoomListViewPOJO> list){
        this.list = list;
        notifyDataSetChanged();
    }

    public void addData(CommunityRoomListViewPOJO data){
        list.add(data);
        notifyItemChanged(list.size() - 1);
    }

    public CommunityRoomListViewPOJO getItem(int position){
        return list.get(position);
    }

}
