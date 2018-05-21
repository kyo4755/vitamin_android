package com.vitamin.wecantalk.UIActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.vitamin.wecantalk.Common.GlobalInfo;
import com.vitamin.wecantalk.Network.RequestTask;
import com.vitamin.wecantalk.POJO.FriendsListViewPOJO;
import com.vitamin.wecantalk.R;

import org.json.JSONArray;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    EditText id,pw,phone,name,email;
    Spinner nation,location,prefer_language,email_spinner;
    Button id_check,register;

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

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
                else if (check==2){
                    register();
                    break;
                }

        }
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void idCheck() {
        String register_id = id.getText().toString();
        check = 1;
        if(register_id.length() == 0)    Toast.makeText(RegisterActivity.this, "아이디를 입력해 주세요.", Toast.LENGTH_SHORT).show();
        else {
            String id_check_url = "http://13.124.62.147:10230/id_check";
            ContentValues values = new ContentValues();
            values.put("id", register_id);

            RequestTask requestTask = new RequestTask(id_check_url, values);

            try{
                String result = requestTask.execute().get();
                JSONObject jsonObject = new JSONObject(result);
                String result_code = jsonObject.get("result").toString();
                if(result_code.equals("0001")){
                    Toast.makeText(RegisterActivity.this, "아이디를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                }
                else if(result_code.equals("0002")){
                    Toast.makeText(RegisterActivity.this, "중복되는 아이디가 존재합니다.", Toast.LENGTH_SHORT).show();
                } else {
                    check = 2;
                    AlertDialog.Builder alert = new AlertDialog.Builder(this);
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
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
            String register_url = "http://13.124.62.147:10230/register";
            ContentValues values = new ContentValues();
            values.put("id", register_id);
            values.put("passwd", register_pw);
            values.put("name", register_name);
            values.put("phone_number", register_phone);
            values.put("email", register_email);
            values.put("nation", register_nation);
            values.put("location", register_location);
            values.put("prefer_language", register_prefer_language);

            RequestTask requestTask = new RequestTask(register_url, values);

            try{
                String result = requestTask.execute().get();
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
                Intent it = new Intent(RegisterActivity.this, StartActivity.class);
                startActivity(it);
                finish();
            } catch (Exception e){
                Toast.makeText(RegisterActivity.this, "서버와의 통신 중 오류가 발생했습니다. 나중에 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                Log.e("RegisgerTask", e.toString());
            }
        }
    }
}
