package com.vitamin.wecantalk.UIActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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

import java.util.LinkedHashMap;
import java.util.Map;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by anemo on 2018-05-22.
 */

public class FindIdActivity extends AppCompatActivity {

    TextView myName;
    TextView findName;
    TextView tmp;
    EditText editText;
    ImageButton button;
    ImageView imageView;
    Button b;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_id_friends);

        editText = findViewById(R.id.friend_find_id_edittext);
        myName = findViewById(R.id.friend_find_id_myid);
        findName = findViewById(R.id.friends_find_id_name);
        tmp = findViewById(R.id.friends_find_id_tmp);
        button = findViewById(R.id.friend_find_id_add_button);
        imageView = findViewById(R.id.friends_find_id_image);

        imageView.setVisibility(View.INVISIBLE);
        findName.setVisibility(View.INVISIBLE);
        button.setVisibility(View.INVISIBLE);

        b=findViewById(R.id.test_button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myName.setVisibility(View.GONE);
                tmp.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);
                findName.setVisibility(View.VISIBLE);
                button.setVisibility(View.VISIBLE);

                String strText = editText.getText().toString();

                AQuery aQuery = new AQuery(FindIdActivity.this);
                String find_friend_url = Config.Server_URL + "find_friend";

                Map<String, Object> params = new LinkedHashMap<>();

                params.put("anid", strText);

                aQuery.ajax(find_friend_url, params, String.class, new AjaxCallback<String>() {
                    @Override
                    public void callback(String url, String result, AjaxStatus status) {
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            String result_code = jsonObject.get("result").toString();
                            if (result_code.equals("0000")) {//정상 나올때
                                String a = jsonObject.get("detail_info").toString();
                                JSONObject detail_info = new JSONObject(a);
                                String id = detail_info.getString("id");
                                String name = detail_info.getString("name");
                                String img = detail_info.getString("image");
                                findName.setText(name);

                                String imgStr = Config.Server_URL + "user_photo?id=" + img;
                                Glide.with(FindIdActivity.this)
                                        .load(imgStr)
                                        .centerCrop()
                                        .bitmapTransform(new CropCircleTransformation(FindIdActivity.this))
                                        .skipMemoryCache(true)
                                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                                        .error(R.drawable.default_user)
                                        .into(imageView);
                            } else {
                                Toast.makeText(FindIdActivity.this, "이상현상", Toast.LENGTH_SHORT).show();
                            }
                                /*}else if(result_code.equals("0001")){//anid 안보냈을때

                                }else if(result_code.equals("0002")){//검색결과업승ㄹ때

                                }else if(result_code.equals("0100")){//get으로 보냇ㅇㄹ대

                                }*/

                        } catch (Exception e) {

                        }
                    }
                });
            }
        });


        editText.setImeOptions(EditorInfo.IME_ACTION_DONE);



        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (EditorInfo.IME_ACTION_DONE == actionId) {
                    myName.setVisibility(View.GONE);
                    tmp.setVisibility(View.GONE);
                    imageView.setVisibility(View.VISIBLE);
                    findName.setVisibility(View.VISIBLE);
                    button.setVisibility(View.VISIBLE);

                    String strText = editText.getText().toString();

                    AQuery aQuery = new AQuery(FindIdActivity.this);
                    String find_friend_url = Config.Server_URL + "find_friend";

                    Map<String, Object> params = new LinkedHashMap<>();

                    params.put("anid", strText);

                    aQuery.ajax(find_friend_url, params, String.class, new AjaxCallback<String>() {
                        @Override
                        public void callback(String url, String result, AjaxStatus status) {
                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                String result_code = jsonObject.get("result").toString();
                                if (result_code.equals("0000")) {//정상 나올때
                                    String a = jsonObject.get("detail_info").toString();
                                    JSONObject detail_info = new JSONObject(a);
                                    String id = detail_info.getString("id");
                                    String name = detail_info.getString("name");
                                    String img = detail_info.getString("image");

                                    findName.setText(name);

                                    String imgStr = Config.Server_URL + "user_photo?id=" + GlobalInfo.my_profile.getImage();
                                    Glide.with(FindIdActivity.this)
                                            .load(imgStr)
                                            .centerCrop()
                                            .bitmapTransform(new CropCircleTransformation(FindIdActivity.this))
                                            .skipMemoryCache(true)
                                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                                            .error(R.drawable.default_user)
                                            .into(imageView);
                                } else {
                                    Toast.makeText(FindIdActivity.this, "이상현상", Toast.LENGTH_SHORT).show();
                                }
                                /*}else if(result_code.equals("0001")){//anid 안보냈을때

                                }else if(result_code.equals("0002")){//검색결과업승ㄹ때

                                }else if(result_code.equals("0100")){//get으로 보냇ㅇㄹ대

                                }*/

                            } catch (Exception e) {

                            }
                        }
                    });


                }
                return true;
            }
        });
    }
}
