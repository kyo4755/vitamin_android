package com.vitamin.wecantalk.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.vitamin.wecantalk.Adapter.FriendsListViewAdapter;
import com.vitamin.wecantalk.R;

/**
 * Created by JongHwa on 2018-04-13.
 */

public class FriendsFragment extends Fragment {

    ListView listview;
    FriendsListViewAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.activity_friends, null);

        adapter = new FriendsListViewAdapter();

        listview = view.findViewById(R.id.listview1);
        listview.setAdapter(adapter);

        adapter.addItem(getResources().getDrawable(R.drawable.temp_face1),"김관희","나는 가끔 눈물을 흘린다..");
        adapter.addItem(getResources().getDrawable(R.drawable.temp_face2),"김철수","카톡x");
        adapter.addItem(getResources().getDrawable(R.drawable.temp_face3),"박철","안드과제/졸작과제");
        adapter.addItem(getResources().getDrawable(R.drawable.temp_face4),"김관희","나는 가끔 눈물을 흘린다..");
        adapter.addItem(getResources().getDrawable(R.drawable.temp_face5),"김철수","카톡x");


        return view;
    }
}
