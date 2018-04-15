package com.vitamin.wecantalk;

import android.content.Intent;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    EditText user_id, user_pw;
    TextView register_btn;
    Button login_btn;
    ImageView logo_img;
    ConstraintLayout login_panel;

    ImageView logo_pop;

    String id, pw;

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
        login_panel = findViewById(R.id.start_input_panel);

        login_btn.setOnClickListener(this);
        register_btn.setOnClickListener(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startAnimation();
            }
        }, 3000);


        ImageView logo_pop= (ImageView) findViewById(R.id.start_logo_image);
        logo_pop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartActivity.this, Pop.class));
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.start_input_login_button:
                Intent it = new Intent(StartActivity.this, MainFragmentActivity.class);
                startActivity(it);
                finish();
                break;
            case R.id.start_input_register:
                break;
        }
    }

    private boolean checkInsertIdPw(){
        id = user_id.getText().toString();
        pw = user_pw.getText().toString();

        return id.length() == 0 || pw.length() == 0;
    }

    private void checkIdPwFromServer(){
        if(checkInsertIdPw()){
            Toast.makeText(StartActivity.this, "아이디와 비밀번호를 입력해 주세요.",Toast.LENGTH_SHORT).show();
        }
        else{

        }
    }

    private void startAnimation(){
        logo_img.animate().translationY(-500).withLayer();

        Animation animation = new AlphaAnimation(0, 1);
        animation.setDuration(1000);
        login_panel.setVisibility(View.VISIBLE);
        login_panel.setAnimation(animation);
    }
}
