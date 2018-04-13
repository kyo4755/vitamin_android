package com.vitamin.wecantalk.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vitamin.wecantalk.R;

/**
 * Created by JongHwa on 2018-04-13.
 */

public class CommunityFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        return inflater.inflate(R.layout.activity_community, container, false);
    }
}
