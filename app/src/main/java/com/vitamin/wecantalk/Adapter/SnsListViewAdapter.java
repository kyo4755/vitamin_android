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

import com.vitamin.wecantalk.POJO.SnsListViewPOJO;
import com.vitamin.wecantalk.R;
import com.vitamin.wecantalk.UIActivity.CommentActivity;

import java.util.ArrayList;

public class SnsListViewAdapter extends BaseAdapter {


    private ArrayList<SnsListViewPOJO> listViewItemList = new ArrayList<SnsListViewPOJO>();

    public SnsListViewAdapter() {

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
            convertView = inflater.inflate(R.layout.prefab_sns_listview, parent, false);
        }
        ImageView sns_profile = convertView.findViewById(R.id.sns_profile);
        TextView sns_name = convertView.findViewById(R.id.sns_name);
        TextView sns_date = convertView.findViewById(R.id.sns_date);
        TextView sns_context = convertView.findViewById(R.id.sns_context);
        ImageView sns_image = convertView.findViewById(R.id.sns_image);

        SnsListViewPOJO listViewItem = listViewItemList.get(position);

        sns_profile.setImageDrawable(listViewItem.getPofile());
        sns_name.setText(listViewItem.getName());
        sns_date.setText(listViewItem.getDate());
        sns_context.setText(listViewItem.getContext());
        sns_image.setImageDrawable(listViewItem.getImage());

        ImageView sns_comment = convertView.findViewById(R.id.sns_comment);
        sns_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(context, CommentActivity.class);
                context.startActivity(it);
            }
        });

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

    public void addItem(Drawable pofile, String name, String date, String context, Drawable image) {
        SnsListViewPOJO item = new SnsListViewPOJO();

        item.setPofile(pofile);
        item.setName(name);
        item.setDate(date);
        item.setContext(context);
        item.setImage(image);

        listViewItemList.add(item);
    }
}
