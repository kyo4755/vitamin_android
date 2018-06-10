package com.vitamin.wecantalk.UIActivity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.google.firebase.iid.FirebaseInstanceId;
import com.vitamin.wecantalk.Common.Config;
import com.vitamin.wecantalk.Common.GlobalInfo;
import com.vitamin.wecantalk.R;
import com.vitamin.wecantalk.fragment.*;

import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by JongHwa on 2018-04-13.
 */

public class MainFragmentActivity extends AppCompatActivity implements View.OnClickListener{

    Button friends, community, study, setting, sns;
    Fragment friendsFragment, communityFragment, studyFragment, settingFragment, snsFragment;

    private int btnIndex = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setToken();
        friends = findViewById(R.id.main_fragment_btn_friends);
        community = findViewById(R.id.main_fragment_btn_community);
        study = findViewById(R.id.main_fragment_btn_study);
        setting = findViewById(R.id.main_fragment_btn_setting);
        sns = findViewById(R.id.main_fragment_btn_sns);

        friends.setOnClickListener(this);
        community.setOnClickListener(this);
        study.setOnClickListener(this);
        setting.setOnClickListener(this);
        sns.setOnClickListener(this);

        friendsFragment = new FriendsFragment();
        communityFragment = new CommunityFragment();
        studyFragment = new StudyFragment();
        settingFragment = new SettingFragment();
        snsFragment = new FriendsSnsFragment();

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
            case R.id.main_fragment_btn_sns:
                fragment = snsFragment;
                btn = sns;
                btnIndex = 5;
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
            case 5: btn.setBackgroundColor(getResources().getColor(R.color.bg_friends)); break;
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
            case 5: btn = sns;  break;

        }

        btn.setBackgroundColor(getResources().getColor(R.color.white));
        btn.setTextColor(getResources().getColor(R.color.black));
    }

    private void setToken(){
        String token = FirebaseInstanceId.getInstance().getToken();

        AQuery aQuery = new AQuery(MainFragmentActivity.this);
        String token_url = Config.Server_URL + "/users/setToken";

        Map<String, Object> params = new LinkedHashMap<>();

        params.put("id", GlobalInfo.my_profile.getId());
        params.put("token", token);

        aQuery.ajax(token_url, params, String.class, new AjaxCallback<String>(){
            @Override
            public void callback(String url, String result, AjaxStatus status) {
                try{
                    JSONObject jsonObject = new JSONObject(result);
                    String result_code = jsonObject.get("result").toString();
                    if(result_code.equals("0000")){
                        Toast.makeText(MainFragmentActivity.this, "토큰전송성공.", Toast.LENGTH_SHORT).show();}
                    else{
                        Toast.makeText(MainFragmentActivity.this, "오류.", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e){
                    Toast.makeText(MainFragmentActivity.this, "서버와의 통신 중 오류가 발생했습니다. 나중에 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
