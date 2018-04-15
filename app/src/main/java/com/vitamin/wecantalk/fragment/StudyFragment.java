package com.vitamin.wecantalk.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.vitamin.wecantalk.Adapter.StudyBestMatchListViewAdapter;
import com.vitamin.wecantalk.POJO.StudyBestMatchListViewPOJO;
import com.vitamin.wecantalk.R;
import com.vitamin.wecantalk.StudyActivity;

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

        View view = inflater.inflate(R.layout.activity_study, null);

        listView = view.findViewById(R.id.study_best_match_listview);
        adapter = new StudyBestMatchListViewAdapter(createPojo());

        listView.setAdapter(adapter);

        return view;
    }

    private ArrayList<StudyBestMatchListViewPOJO> createPojo(){
        ArrayList<StudyBestMatchListViewPOJO> list = new ArrayList<>();

        StudyBestMatchListViewPOJO pojo = new StudyBestMatchListViewPOJO(getResources().getDrawable(R.drawable.temp_face1), "Sam", "EN > KR", "투링이, 뉴질랜드", "한 시간 전", "새벽 3시 39분");
        list.add(pojo);

        pojo = new StudyBestMatchListViewPOJO(getResources().getDrawable(R.drawable.temp_face2), "술탄", "EN > JP", "위치정보 없음", "접속 중", "오전 11시 39분");
        list.add(pojo);

        pojo = new StudyBestMatchListViewPOJO(getResources().getDrawable(R.drawable.temp_face3), "Erik", "EN, MI > KR", "투랑이, 뉴질랜드", "10분 전", "밤 8시 39분");
        list.add(pojo);

        pojo = new StudyBestMatchListViewPOJO(getResources().getDrawable(R.drawable.temp_face4), "Nia", "EN > KR", "버지니아비치, 미국", "18일 전", "새벽 4시 50분");
        list.add(pojo);

        pojo = new StudyBestMatchListViewPOJO(getResources().getDrawable(R.drawable.temp_face5), "Matt", "EN > KR", "미국", "한 시간 전", "새벽 1시 51분");
        list.add(pojo);

        return list;
    }
}
