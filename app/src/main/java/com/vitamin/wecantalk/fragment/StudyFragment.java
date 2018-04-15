package com.vitamin.wecantalk.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.vitamin.wecantalk.R;
import com.vitamin.wecantalk.StudyActivity;

/**
 * Created by JongHwa on 2018-04-13.
 */

public class StudyFragment extends Fragment {

    TextView tv;
    Button RB;
    Button LB;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.activity_study, null);

        tv=(TextView)view.findViewById(R.id.word_meaning);
        tv.setText("돼지[명사]");

        RB=(Button)view.findViewById(R.id.RButton);
        LB=(Button)view.findViewById(R.id.LButton);

        RB.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view) {
                Intent it = new Intent(getActivity(), StudyActivity.class);
                startActivity(it);
            }
        });

        return view;
    }
}
