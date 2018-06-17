package com.vitamin.wecantalk.UIActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.vitamin.wecantalk.Common.Config;
import com.vitamin.wecantalk.Common.GlobalInfo;
import com.vitamin.wecantalk.R;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import jp.wasabeef.glide.transformations.CropCircleTransformation;


public class NaverDicActivity extends AppCompatActivity {

    EditText before_msg;
    TextView after_msg;
    TextView info_msg;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.naver_dic);

        before_msg = findViewById(R.id.translate_before_msg);
        after_msg = findViewById(R.id.translate_after_msg);
        info_msg = findViewById(R.id.translate_info_msg);

        info_msg.setVisibility(View.GONE);
        after_msg.setVisibility(View.GONE);


        before_msg.setImeOptions(EditorInfo.IME_ACTION_DONE);
        before_msg.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (EditorInfo.IME_ACTION_DONE == actionId) {
                    after_msg.setVisibility(View.VISIBLE);
                    info_msg.setVisibility(View.VISIBLE);

                    String strText = before_msg.getText().toString();

                    AQuery aQuery = new AQuery(NaverDicActivity.this);
                    String translate_url = Config.Server_URL + "translate";

                    Map<String, Object> params = new LinkedHashMap<>();

                    params.put("id", GlobalInfo.my_profile.getId());
                    params.put("msg", strText);

                    aQuery.ajax(translate_url, params, String.class, new AjaxCallback<String>() {
                        @Override
                        public void callback(String url, String result, AjaxStatus status) {
                            String msg = "";
                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                String result_code = jsonObject.get("result").toString();
                                if (result_code.equals("0000")) {
                                    msg = jsonObject.get("translate_msg").toString();
                                    before_msg.setText("");
                                    after_msg.setText(msg);
                                } else {
                                    Toast.makeText(getApplicationContext(), "오류.", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                Log.e("chat_send Error", e.toString());
                                Toast.makeText(getApplicationContext(), "오류.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                return true;
            }
        });
    }
}

