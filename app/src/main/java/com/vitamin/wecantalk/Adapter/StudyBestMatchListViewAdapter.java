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

import com.vitamin.wecantalk.POJO.StudyBestMatchListViewPOJO;
import com.vitamin.wecantalk.R;

import java.util.ArrayList;

/**
 * Created by JongHwa on 2018-04-15.
 */

public class StudyBestMatchListViewAdapter extends BaseAdapter {

    private ArrayList<StudyBestMatchListViewPOJO> list;

    public StudyBestMatchListViewAdapter(ArrayList<StudyBestMatchListViewPOJO> list){
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
        Context mContext = viewGroup.getContext();

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.study_best_match_prefab, viewGroup, false);
        }

        ImageView img = view.findViewById(R.id.study_best_match_img);
        TextView name = view.findViewById(R.id.study_best_match_name);
        TextView language = view.findViewById(R.id.study_best_match_language);
        TextView location = view.findViewById(R.id.study_best_match_location);
        TextView login_time = view.findViewById(R.id.study_best_match_login_time);
        TextView local_time = view.findViewById(R.id.study_best_match_local_time);

        img.setImageDrawable(list.get(i).getImg());
        img.setBackground(new ShapeDrawable(new OvalShape()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            img.setClipToOutline(true);
        }

        name.setText(list.get(i).getName());
        language.setText(list.get(i).getLanguage());
        location.setText(list.get(i).getLocation());
        login_time.setText(list.get(i).getLogin_time());
        local_time.setText(list.get(i).getLocal_time());

        return view;
    }
}
