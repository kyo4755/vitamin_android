package com.vitamin.wecantalk.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.vitamin.wecantalk.Adapter.CommunityListViewAdapter;
import com.vitamin.wecantalk.POJO.CommunityListViewPOJO;
import com.vitamin.wecantalk.R;

import java.util.ArrayList;

/**
 * Created by JongHwa on 2018-04-13.
 */

public class CommunityFragment extends Fragment {

    ListView listView;
    CommunityListViewAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.activity_community,null);

        listView = view.findViewById(R.id.community_listview);

        adapter = new CommunityListViewAdapter(createPOJO());
        listView.setAdapter(adapter);

        return view;
    }

    private ArrayList<CommunityListViewPOJO> createPOJO(){

        ArrayList<CommunityListViewPOJO> list = new ArrayList<>();

        CommunityListViewPOJO pojo = new CommunityListViewPOJO(getResources().getDrawable(R.drawable.temp_face1), "David", "Hello!", "14:30");
        list.add(pojo);

        pojo = new CommunityListViewPOJO(getResources().getDrawable(R.drawable.temp_face2), "Johnson", "Where are you from?", "07:14");
        list.add(pojo);

        pojo = new CommunityListViewPOJO(getResources().getDrawable(R.drawable.temp_face3), "Jessica", "Who are you?", "21:54");
        list.add(pojo);

        pojo = new CommunityListViewPOJO(getResources().getDrawable(R.drawable.temp_face4), "Bread", "Do you like bread?", "09:09");
        list.add(pojo);

        pojo = new CommunityListViewPOJO(getResources().getDrawable(R.drawable.temp_face5), "Angelina", "I want to work hard", "11:22");
        list.add(pojo);

        return list;
    }
}
