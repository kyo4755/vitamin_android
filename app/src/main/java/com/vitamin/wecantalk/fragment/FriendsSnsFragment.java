package com.vitamin.wecantalk.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.vitamin.wecantalk.Adapter.SnsListViewAdapter;
import com.vitamin.wecantalk.Adapter.StudyBestMatchListViewAdapter;
import com.vitamin.wecantalk.R;
import com.vitamin.wecantalk.UIActivity.FindIdActivity;
import com.vitamin.wecantalk.UIActivity.PostingActivity;

/**
 * Created by JongHwa on 2018-04-13.
 */

public class FriendsSnsFragment extends Fragment {

    ListView listView;
    SnsListViewAdapter adapter;

    ImageView posting;
    Button find;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sns, null);

        listView = view.findViewById(R.id.sns_listview);
        adapter = new SnsListViewAdapter();


        posting = view.findViewById(R.id.sns_posting);
        find = view.findViewById(R.id.sns_find);

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



        listView.setAdapter(adapter);
        adapter.addItem(getResources().getDrawable(R.drawable.pro), "김관희", "나는 가끔 눈물을 흘린다..", "Hi", getResources().getDrawable(R.drawable.pro));
        adapter.addItem(getResources().getDrawable(R.drawable.vitamin), "하이루", "나는 가끔 눈물을 흘린다..", "Hasdfasdfsadfasdfsdasadfㄴㅁㅇ람ㄴ어리ㅏㄴㅇㅁ리ㅏ넝미런ㅁ이런ㅇ미러ㅣㄴㅇㅁ러ㅣㄴㅁ어림ㄴ어ㅣ런ㅁ이러님ㅇ러ㅣㅁㄴㄹㅇi", getResources().getDrawable(R.drawable.vitamin));


        return view;
    }
}
