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
    /*Button temp_btn;
    ImageView temp_img;*/

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

        /*temp_btn = view.findViewById(R.id.study_temp_button);
        temp_img = view.findViewById(R.id.study_temp_img);

        temp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = new AlphaAnimation(0, 1);
                animation.setDuration(1000);

                if(temp_img.getVisibility() == View.GONE){
                    temp_img.setVisibility(View.VISIBLE);
                    temp_img.setAnimation(animation);
                }
                else temp_img.setVisibility(View.GONE);

            }
        });*/

        return view;
    }
}
