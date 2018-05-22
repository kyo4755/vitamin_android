package com.vitamin.wecantalk.UIActivity;

import android.content.Intent;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.vitamin.wecantalk.Common.Config;
import com.vitamin.wecantalk.Common.GlobalInfo;
import com.vitamin.wecantalk.POJO.FriendsListViewPOJO;
import com.vitamin.wecantalk.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    EditText user_id, user_pw;
    TextView register_btn;
    Button login_btn;
    ImageView logo_img;
    ConstraintLayout login_panel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        user_id = findViewById(R.id.start_input_id_edittext);
        user_pw = findViewById(R.id.start_input_pw_edittext);

        login_btn = findViewById(R.id.start_input_login_button);
        register_btn = findViewById(R.id.start_input_register);
        register_btn.setText(Html.fromHtml("<u>" + getResources().getString(R.string.login_register) + "</u>"));

        logo_img = findViewById(R.id.start_logo_image);
        logo_img.setOnClickListener(this);

        login_panel = findViewById(R.id.start_input_panel);

        login_btn.setOnClickListener(this);
        register_btn.setOnClickListener(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startAnimation();
            }
        }, 3000);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.start_input_login_button:
                tryLogin();
                break;
            case R.id.start_input_register:
                Intent it = new Intent(StartActivity.this, RegisterActivity.class);
                startActivity(it);
                finish();
                break;
            case R.id.start_logo_image:
                startActivity(new Intent(StartActivity.this, PopActivity.class));
                break;
        }
    }

    private void startAnimation(){
        logo_img.animate().translationY(-500).withLayer();

        Animation animation = new AlphaAnimation(0, 1);
        animation.setDuration(1000);
        login_panel.setVisibility(View.VISIBLE);
        login_panel.setAnimation(animation);
    }

    private void tryLogin() {
        String id = user_id.getText().toString();
        String pw = user_pw.getText().toString();

        if(id.length() == 0) {
            if(pw.length() == 0)    Toast.makeText(StartActivity.this, "아이디와 비밀번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
            else                    Toast.makeText(StartActivity.this, "아이디를 입력해 주세요.", Toast.LENGTH_SHORT).show();
        }
        else {
            if(pw.length() == 0)    Toast.makeText(StartActivity.this, "비밀번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
            else {
                AQuery aQuery = new AQuery(StartActivity.this);
                String login_url = Config.Server_URL + "login";

                Map<String, Object> params = new LinkedHashMap<>();

                params.put("id", id);
                params.put("passwd", pw);

                aQuery.ajax(login_url, params, String.class, new AjaxCallback<String>() {
                    @Override
                    public void callback(String url, String result, AjaxStatus status) {
                        try{
                            JSONObject jsonObject = new JSONObject(result);
                            String result_code = jsonObject.get("result").toString();

                            if(result_code.equals("0001"))      Toast.makeText(StartActivity.this, "아이디를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                            else if(result_code.equals("0002")) Toast.makeText(StartActivity.this, "비밀번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                            else if(result_code.equals("0003")) Toast.makeText(StartActivity.this, "아이디와 비밀번호를 확인해 주세요.", Toast.LENGTH_SHORT).show();
                            else {
                                GlobalInfo.friends_list = new ArrayList<>();
                                JSONArray jsonArray = new JSONArray(jsonObject.get("friends_list").toString());
                                for(int i=0; i<jsonArray.length(); i++) {
                                    JSONObject jObject = jsonArray.getJSONObject(i);
                                    FriendsListViewPOJO pojo = new FriendsListViewPOJO();
                                    pojo.setId(jObject.getString("id"));
                                    pojo.setName(jObject.getString("name"));
                                    pojo.setEmail(jObject.getString("email"));
                                    pojo.setNation(jObject.getString("nation"));
                                    pojo.setLocation(jObject.getString("location"));
                                    pojo.setPrefer_language(jObject.getString("prefer_language"));
                                    pojo.setStatus_msg(jObject.getString("status_msg"));
                                    pojo.setImage(jObject.get("image").toString());

                                    GlobalInfo.friends_list.add(pojo);
                                }

                                JSONObject myJsonObject = new JSONObject(jsonObject.get("my_profile").toString());
                                GlobalInfo.my_profile.setId(myJsonObject.get("id").toString());
                                GlobalInfo.my_profile.setName(myJsonObject.get("name").toString());
                                GlobalInfo.my_profile.setEmail(myJsonObject.get("email").toString());
                                GlobalInfo.my_profile.setNation(myJsonObject.get("nation").toString());
                                GlobalInfo.my_profile.setLocation(myJsonObject.get("location").toString());
                                GlobalInfo.my_profile.setPrefer_language(myJsonObject.get("prefer_language").toString());
                                GlobalInfo.my_profile.setStatus_msg(myJsonObject.get("status_msg").toString());
                                GlobalInfo.my_profile.setImage(myJsonObject.get("image").toString());

                                Intent it = new Intent(StartActivity.this, MainFragmentActivity.class);
                                startActivity(it);
                                finish();
                            }

                        } catch (Exception e){
                            Toast.makeText(StartActivity.this, "서버와의 통신 중 오류가 발생했습니다. 나중에 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                            Log.e("LoginTask", e.toString());
                        }
                    }
                });
            }
        }
    }
}
