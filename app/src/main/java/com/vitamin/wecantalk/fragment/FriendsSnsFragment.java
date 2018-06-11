package com.vitamin.wecantalk.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import com.vitamin.wecantalk.Adapter.SnsListViewAdapter;
import com.vitamin.wecantalk.Adapter.StudyBestMatchListViewAdapter;
import com.vitamin.wecantalk.Common.Config;
import com.vitamin.wecantalk.POJO.SnsListViewPOJO;
import com.vitamin.wecantalk.R;
import com.vitamin.wecantalk.UIActivity.CommentActivity;
import com.vitamin.wecantalk.UIActivity.FindIdActivity;
import com.vitamin.wecantalk.UIActivity.MainFragmentActivity;
import com.vitamin.wecantalk.UIActivity.PostingActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by JongHwa on 2018-04-13.
 */

public class FriendsSnsFragment extends Fragment {
    Context context;

    ListView listView;
    SnsListViewAdapter adapter;

    ImageView posting;
    ImageView find;
    ImageView comment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context = container.getContext();
        View view = inflater.inflate(R.layout.fragment_sns, null);

        listView = view.findViewById(R.id.sns_listview);
        adapter = new SnsListViewAdapter();

        posting = view.findViewById(R.id.sns_posting);
        find = view.findViewById(R.id.sns_find);

        listView.setAdapter(adapter);

        posting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), PostingActivity.class);
                startActivity(it);
            }
        });

        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), FindIdActivity.class);
                startActivity(it);
            }
        });

        return view;
    }

}
