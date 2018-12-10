package com.vitamin.wecantalk.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.vitamin.wecantalk.Common.Config;
import com.vitamin.wecantalk.R;

/**
 * Created by JongHwa on 2018-04-13.
 */

public class StudyFragment extends Fragment implements View.OnClickListener{

    Button youglish, wordCloud;
    Fragment youglishFragment, wordCloudFragment;

    private int btnIndex = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_study, null);

        youglish = view.findViewById(R.id.study_fragment_btn_youglish);
        wordCloud = view.findViewById(R.id.study_fragment_btn_word_cloud);

        youglish.setOnClickListener(this);
        wordCloud.setOnClickListener(this);

        youglishFragment = new YouglishFragment();
        wordCloudFragment = new WordCloudFragment();

        settingFragment(wordCloudFragment);

        wordCloud.setBackgroundColor(getResources().getColor(R.color.bg_community));
        wordCloud.setTextColor(getResources().getColor(R.color.white));

        return view;
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = null;
        Button btn = null;

        resetButtonColor(btnIndex);

        switch (v.getId()){
            case R.id.study_fragment_btn_youglish:
                fragment = youglishFragment;
                btn = youglish;
                btnIndex = 1;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Config.Server_URL + "Web/Youglish"));
                startActivity(intent);
                break;
            case R.id.study_fragment_btn_word_cloud:
                fragment = wordCloudFragment;
                btn = wordCloud;
                btnIndex = 2;
                break;
        }

        settingFragment(fragment);
        changeButtonColor(btn, btnIndex);
    }

    private void resetButtonColor(int index){
        Button btn = wordCloud;
        switch (index){
            case 1: btn = youglish;  break;
            case 2: btn = wordCloud;    break;
        }

        btn.setBackgroundColor(getResources().getColor(R.color.white));
        btn.setTextColor(getResources().getColor(R.color.black));
    }

    private void changeButtonColor(Button btn, int index){

        switch (index){
            case 1: btn.setBackgroundColor(getResources().getColor(R.color.bg_friends));  break;
            case 2: btn.setBackgroundColor(getResources().getColor(R.color.bg_community));  break;
        }
        btn.setTextColor(getResources().getColor(R.color.white));
    }

    private void settingFragment(Fragment fragment){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.study_fragment, fragment);
        fragmentTransaction.commit();
    }
}
