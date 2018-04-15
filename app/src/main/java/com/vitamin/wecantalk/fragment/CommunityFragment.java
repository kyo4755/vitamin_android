package com.vitamin.wecantalk.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vitamin.wecantalk.R;

/**
 * Created by JongHwa on 2018-04-13.
 */

public class CommunityFragment extends Fragment {

    TextView testView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.activity_community,null);

        testView = view.findViewById(R.id.fragment_community_testText);
        testView.setText("이것은 대화방 프래그먼트 입니다.");

        return view;
    }
}
