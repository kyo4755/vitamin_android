package com.vitamin.wecantalk.UIActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberFormattingTextWatcher;

import android.util.Log;
import android.view.View;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
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

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    EditText id,pw,phone,name,email;
    Spinner nation,location,prefer_language,email_spinner;
    Button id_check,register;
    LottieAnimationView loading_animation;

    String email_form = "naver.com";
    String nation_select, location_select, prefer_language_select;

    int check = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        id = findViewById(R.id.register_input_id_edittext);
        pw = findViewById(R.id.register_input_pw_edittext);
        phone = findViewById(R.id.register_input_phone_edittext);
        name = findViewById(R.id.register_input_name_edittext);
        email = findViewById(R.id.register_input_email_edittext);
        nation = findViewById(R.id.register_input_nation_spinner);
        nation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nation_select = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        location = findViewById(R.id.register_input_location_spinner);
        location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                location_select = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        prefer_language = findViewById(R.id.register_input_prefer_language_spinner);
        prefer_language.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                prefer_language_select = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        email_spinner = findViewById(R.id.email_spinner);
        email_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                email_form = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        id_check = findViewById(R.id.id_check);
        id_check.setOnClickListener(this);
        register = findViewById(R.id.register_button);
        register.setOnClickListener(this);

        phone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        loading_animation = findViewById(R.id.loading_animation);
        loading_animation.setAnimation("little_balls.json");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_check:
                idCheck();
                break;
            case R.id.register_button:
                if(id.getText().toString().isEmpty()){
                    Toast.makeText(RegisterActivity.this, "아이디를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    break;
                }
                else if (pw.getText().toString().isEmpty()){
                    Toast.makeText(RegisterActivity.this, "비밀번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    break;
                }
                else if (phone.getText().toString().isEmpty()){
                    Toast.makeText(RegisterActivity.this, "전화번호룰 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    break;
                }
                else if (name.getText().toString().isEmpty()){
                    Toast.makeText(RegisterActivity.this, "이름을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    break;
                }
                else if (email.getText().toString().isEmpty()){
                    Toast.makeText(RegisterActivity.this, "이메일을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    break;
                }
                else if (check==0){
                    Toast.makeText(RegisterActivity.this, "중복확인을 해주세요.", Toast.LENGTH_SHORT).show();
                    break;
                }
                else if (check==1){
                    Toast.makeText(RegisterActivity.this, "중복확인을 해주세요.", Toast.LENGTH_SHORT).show();
                    break;
                }
                else if (check==1){
                    Toast.makeText(RegisterActivity.this, "이미 가입하였습니다.", Toast.LENGTH_SHORT).show();
                    break;
                }
                else if (check==2){
                    register();
                    break;
                }

        }
    }

    private void idCheck() {
        final String register_id = id.getText().toString();
        check = 1;
        if(register_id.length() == 0)    Toast.makeText(RegisterActivity.this, "아이디를 입력해 주세요.", Toast.LENGTH_SHORT).show();
        else {
            AQuery aQuery = new AQuery(RegisterActivity.this);
            final String id_check_url = Config.Server_URL + "id_check";

            Map<String, Object> params = new LinkedHashMap<>();

            final ContentValues values = new ContentValues();
            params.put("id", register_id);

            aQuery.ajax(id_check_url, params, String.class, new AjaxCallback<String>(){
                @Override
                public void callback(String url, String result, AjaxStatus status) {
                    try{
                        JSONObject jsonObject = new JSONObject(result);
                        String result_code = jsonObject.get("result").toString();
                        if(result_code.equals("0001")){
                            Toast.makeText(RegisterActivity.this, "아이디를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                        }
                        else if(result_code.equals("0002")){
                            Toast.makeText(RegisterActivity.this, "중복되는 아이디가 존재합니다.", Toast.LENGTH_SHORT).show();
                        } else {
                            check = 2;
                            AlertDialog.Builder alert = new AlertDialog.Builder(RegisterActivity.this);
                            alert.setTitle("중복확인");
                            alert.setMessage("사용 가능한 아이디 입니다." + register_id +"를 사용하시겠습니까?");
                            alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    id.setClickable(false);
                                    id.setFocusable(false);
                                }
                            });
                            alert.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    check = 1;
                                }
                            });
                            alert.show();

                        }
                    } catch (Exception e){
                        Toast.makeText(RegisterActivity.this, "서버와의 통신 중 오류가 발생했습니다. 나중에 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                        Log.e("IdTask", e.toString());
                    }
                }
            });
        }
    }

    private void register() {
        String register_id = id.getText().toString();
        String register_pw = pw.getText().toString();
        String register_name = name.getText().toString();
        String register_phone = phone.getText().toString();
        String register_email = email.getText().toString() + "@" + email_form;
        String register_nation = nation_select;
        String register_location = location_select;
        String register_prefer_language = prefer_language_select;

        if(register_id.length() == 0)    Toast.makeText(RegisterActivity.this, "아이디를 입력해 주세요.", Toast.LENGTH_SHORT).show();
        else {
            AQuery aQuery = new AQuery(RegisterActivity.this);
            String register_url = Config.Server_URL + "register";

            Map<String, Object> params = new LinkedHashMap<>();

            params.put("id", register_id);
            params.put("passwd", register_pw);
            params.put("name", register_name);
            params.put("phone_number", register_phone);
            params.put("email", register_email);
            params.put("nation", register_nation);
            params.put("location", register_location);
            params.put("prefer_language", register_prefer_language);

            aQuery.ajax(register_url, params, String.class, new AjaxCallback<String>(){
                @Override
                public void callback(String url, String result, AjaxStatus status) {
                    try{
                        JSONObject jsonObject = new JSONObject(result);
                        String result_code = jsonObject.get("result").toString();
                        if(result_code.equals("0001")){Toast.makeText(RegisterActivity.this, "아이디를 입력해 주세요.", Toast.LENGTH_SHORT).show();}
                        else if(result_code.equals("0002")){Toast.makeText(RegisterActivity.this, "비밀번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();}
                        else if(result_code.equals("0003")){Toast.makeText(RegisterActivity.this, "이름을 입력해 주세요.", Toast.LENGTH_SHORT).show();}
                        else if(result_code.equals("0004")){Toast.makeText(RegisterActivity.this, "이메일을 입력해 주세요.", Toast.LENGTH_SHORT).show();}
                        else if(result_code.equals("0005")){Toast.makeText(RegisterActivity.this, "번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();}
                        else if(result_code.equals("0006")){Toast.makeText(RegisterActivity.this, "국가를 입력해 주세요.", Toast.LENGTH_SHORT).show();}
                        else if(result_code.equals("0007")){Toast.makeText(RegisterActivity.this, "지역을 입력해 주세요.", Toast.LENGTH_SHORT).show();}
                        else if(result_code.equals("0008")){Toast.makeText(RegisterActivity.this, "언어를 입력해 주세요.", Toast.LENGTH_SHORT).show();}
                        else{
                            check = 3;
                            loading_animation.setVisibility(View.VISIBLE);
                            loading_animation.playAnimation();

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    loading_animation.setVisibility(View.GONE);
                                    loading_animation.pauseAnimation();
                                    Toast.makeText(RegisterActivity.this, "회원가입이 완료가 되었습니다. 로그인 해주세요.", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }, 2500);

                        }
                    } catch (Exception e){
                        Toast.makeText(RegisterActivity.this, "서버와의 통신 중 오류가 발생했습니다. 나중에 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                        Log.e("RegisgerTask", e.toString());
                    }
                }
            });
        }
    }
    private void auto_login() {
        String auto_id = id.getText().toString();
        String auto_pw = pw.getText().toString();
        System.out.print("------------------------------------------------------");
        System.out.print(auto_id);
        System.out.print(auto_pw);
        if (auto_id.length() == 0) {
            if (auto_pw.length() == 0)
                Toast.makeText(RegisterActivity.this, "아이디와 비밀번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
            else Toast.makeText(RegisterActivity.this, "아이디를 입력해 주세요.", Toast.LENGTH_SHORT).show();
        } else {
            if (auto_pw.length() == 0)
                Toast.makeText(RegisterActivity.this, "비밀번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
            else {
                AQuery aQuery = new AQuery(RegisterActivity.this);
                String login_url = Config.Server_URL + "login";

                Map<String, Object> params = new LinkedHashMap<>();

                params.put("id", auto_id);
                params.put("passwd", auto_pw);

                aQuery.ajax(login_url, params, String.class, new AjaxCallback<String>() {
                    @Override
                    public void callback(String url, String result, AjaxStatus status) {
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            String result_code = jsonObject.get("result").toString();

                            if (result_code.equals("0001"))
                                Toast.makeText(RegisterActivity.this, "아이디를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                            else if (result_code.equals("0002"))
                                Toast.makeText(RegisterActivity.this, "비밀번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                            else if (result_code.equals("0003"))
                                Toast.makeText(RegisterActivity.this, "아이디와 비밀번호를 확인해 주세요.", Toast.LENGTH_SHORT).show();
                            else {
                                GlobalInfo.friends_list = new ArrayList<>();
                                JSONArray jsonArray = new JSONArray(jsonObject.get("friends_list").toString());
                                for (int i = 0; i < jsonArray.length(); i++) {
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

                                Intent it = new Intent(RegisterActivity.this, MainFragmentActivity.class);
                                startActivity(it);
                                finish();
                            }

                        } catch (Exception e) {
                            Toast.makeText(RegisterActivity.this, "서버와의 통신 중 오류가 발생했습니다. 나중에 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                            Log.e("LoginTask", e.toString());
                        }
                    }
                });
            }
        }
    }

}
