package com.vitamin.wecantalk.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.vitamin.wecantalk.Adapter.StudyBestMatchListViewAdapter;
import com.vitamin.wecantalk.POJO.StudyBestMatchListViewPOJO;
import com.vitamin.wecantalk.R;

import java.util.ArrayList;

/**
 * Created by JongHwa on 2018-04-13.
 */

public class StudyFragment extends Fragment {

    ListView listView;
    StudyBestMatchListViewAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_study, null);

        listView = view.findViewById(R.id.study_best_match_listview);
        adapter = new StudyBestMatchListViewAdapter(null);

        //listView.setAdapter(adapter);

        return view;
    }
}
