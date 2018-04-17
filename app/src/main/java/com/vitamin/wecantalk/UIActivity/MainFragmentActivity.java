package com.vitamin.wecantalk.UIActivity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.vitamin.wecantalk.R;
import com.vitamin.wecantalk.fragment.*;

/**
 * Created by JongHwa on 2018-04-13.
 */

public class MainFragmentActivity extends AppCompatActivity implements View.OnClickListener{

    Button friends, community, study, setting;
    Fragment friendsFragment, communityFragment, studyFragment, settingFragment;

    private int btnIndex = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        friends = findViewById(R.id.main_fragment_btn_friends);
        community = findViewById(R.id.main_fragment_btn_community);
        study = findViewById(R.id.main_fragment_btn_study);
        setting = findViewById(R.id.main_fragment_btn_setting);

        friends.setOnClickListener(this);
        community.setOnClickListener(this);
        study.setOnClickListener(this);
        setting.setOnClickListener(this);

        friendsFragment = new FriendsFragment();
        communityFragment = new CommunityFragment();
        studyFragment = new StudyFragment();
        settingFragment = new SettingFragment();

        settingFragment(friendsFragment);

        friends.setBackgroundColor(getResources().getColor(R.color.bg_friends));
        friends.setTextColor(getResources().getColor(R.color.white));
    }

    @Override
    public void onClick(View view) {
        Fragment fragment = null;
        Button btn = null;

        resetButtonColor(btnIndex);

        switch (view.getId()){
            case R.id.main_fragment_btn_friends:
                fragment = friendsFragment;
                btn = friends;
                btnIndex = 1;
                break;
            case R.id.main_fragment_btn_community:
                fragment = communityFragment;
                btn = community;
                btnIndex = 2;
                break;
            case R.id.main_fragment_btn_study:
                fragment = studyFragment;
                btn = study;
                btnIndex = 3;
                break;
            case R.id.main_fragment_btn_setting:
                fragment = settingFragment;
                btn = setting;
                btnIndex = 4;
                break;
        }

        settingFragment(fragment);
        changeButtonColor(btn, btnIndex);
    }

    private void settingFragment(Fragment fragment){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.main_fragment, fragment);
        fragmentTransaction.commit();
    }

    private void changeButtonColor(Button btn, int index){

        switch (index){
            case 1: btn.setBackgroundColor(getResources().getColor(R.color.bg_friends));  break;
            case 2: btn.setBackgroundColor(getResources().getColor(R.color.bg_community));  break;
            case 3: btn.setBackgroundColor(getResources().getColor(R.color.bg_study));  break;
            case 4: btn.setBackgroundColor(getResources().getColor(R.color.bg_setting));  break;
        }
        btn.setTextColor(getResources().getColor(R.color.white));
    }


    private void resetButtonColor(int index){
        Button btn = friends;
        switch (index){
            case 1: btn = friends;  break;
            case 2: btn = community;    break;
            case 3: btn = study;    break;
            case 4: btn = setting;  break;
        }

        btn.setBackgroundColor(getResources().getColor(R.color.white));
        btn.setTextColor(getResources().getColor(R.color.black));
    }
}
