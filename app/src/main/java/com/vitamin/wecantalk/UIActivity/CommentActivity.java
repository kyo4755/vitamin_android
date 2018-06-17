package com.vitamin.wecantalk.UIActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.vitamin.wecantalk.Adapter.SnsCommentListViewAdapter;
import com.vitamin.wecantalk.Adapter.SnsListViewAdapter;
import com.vitamin.wecantalk.Common.Config;
import com.vitamin.wecantalk.Common.GlobalInfo;
import com.vitamin.wecantalk.POJO.SnsCommentListViewPOJO;
import com.vitamin.wecantalk.POJO.SnsListViewPOJO;
import com.vitamin.wecantalk.R;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Comment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class CommentActivity extends AppCompatActivity {

    long now;
    Date date;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    ImageView heart_black, heart_red;
    EditText edittext;
    Button input_button;
    ListView listView;
    ImageView sns_profile, sns_image;
    TextView sns_name, sns_date, sns_context;
    TextView sns_count_string;

    SnsCommentListViewAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sns_comment_layout);



        listView = findViewById(R.id.sns_comment_list);
        adapter = new SnsCommentListViewAdapter();
        listView.setAdapter(adapter);

        heart_black = findViewById(R.id.sns_heart_black);
        heart_red = findViewById(R.id.sns_heart_red);
        heart_black.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                heart_black.setVisibility(View.GONE);
                heart_red.setVisibility(View.VISIBLE);
            }
        });
        heart_red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                heart_black.setVisibility(View.VISIBLE);
                heart_red.setVisibility(View.GONE);
            }
        });

        edittext = findViewById(R.id.comment_edittext);
        input_button = findViewById(R.id.comment_input_button);

        input_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment_write();
            }
        });

        sns_profile = findViewById(R.id.sns_profile);
        sns_image = findViewById(R.id.sns_image);
        sns_name = findViewById(R.id.sns_name);
        sns_date = findViewById(R.id.sns_date);
        sns_context = findViewById(R.id.sns_context);
        sns_count_string = findViewById(R.id.sns_count_string);

        contentCreatePOJO();
        commentCreatePOJO();

    }

    private void contentCreatePOJO() {
        AQuery aQuery = new AQuery(this);
        String sns_comment_list_url = Config.Server_URL + "sns/getContent";

        Map<String, Object> params = new LinkedHashMap<>();

        Intent it = getIntent();

        params.put("index", it.getStringExtra("index"));

        aQuery.ajax(sns_comment_list_url, params, String.class, new AjaxCallback<String>() {
            @Override
            public void callback(String url, String result, AjaxStatus status) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String result_code = jsonObject.get("result").toString();
                    if (result_code.equals("0000")) {
                        JSONObject jObject = new JSONObject(jsonObject.getString("content"));
                        if(jObject.getString("user_image").equals("null")){
                            Glide.with(CommentActivity.this)
                                    .load(R.drawable.default_user)
                                    .centerCrop()
                                    .skipMemoryCache(true)
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .bitmapTransform(new CropCircleTransformation(CommentActivity.this))
                                    .into(sns_profile);
                        } else {
                            String imgPro = Config.Server_URL + "users/getPhoto?id=" + jObject.getString("user_image");
                            Glide.with(CommentActivity.this)
                                    .load(imgPro)
                                    .centerCrop()
                                    .skipMemoryCache(true)
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .bitmapTransform(new CropCircleTransformation(CommentActivity.this))
                                    .into(sns_profile);
                        }

                        if(jObject.getString("content_img").equals("null")){
                            sns_image.setVisibility(View.GONE);
                        } else {
                            String imgPro = Config.Server_URL + "sns/getPhoto?id=" + jObject.getString("content_img");
                            Glide.with(CommentActivity.this)
                                    .load(imgPro)
                                    .centerCrop()
                                    .skipMemoryCache(true)
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .into(sns_image);
                        }


                        sns_name.setText(jObject.getString("user_name"));
                        sns_context.setText(jObject.getString("content_msg"));

                        SimpleDateFormat original_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        SimpleDateFormat cut_format = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분");
                        Date origin_date = original_format.parse(jObject.getString("content_date"));
                        String new_date = cut_format.format(origin_date);
                        sns_date.setText(new_date);

                    } else if (result_code.equals("0001"))
                        Toast.makeText(CommentActivity.this, "0001.", Toast.LENGTH_SHORT).show();
                    else if (result_code.equals("0002"))
                        Toast.makeText(CommentActivity.this, "0002.", Toast.LENGTH_SHORT).show();
                    else {
                    }

                } catch (Exception e) {

                }
            }
        });
    }

    private void commentCreatePOJO() {
        AQuery aQuery = new AQuery(this);
        String sns_comment_list_url = Config.Server_URL + "sns/getCommentList";

        Map<String, Object> params = new LinkedHashMap<>();

        Intent it = getIntent();

        params.put("index", it.getStringExtra("index"));

        aQuery.ajax(sns_comment_list_url, params, String.class, new AjaxCallback<String>() {
            @Override
            public void callback(String url, String result, AjaxStatus status) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String result_code = jsonObject.get("result").toString();
                    if (result_code.equals("0000")) {
                        JSONArray jsonArray = new JSONArray(jsonObject.get("sns_comment_list").toString());
                        System.out.println(jsonArray.length());
                        sns_count_string.setText(jsonArray.length() + "");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jObject = jsonArray.getJSONObject(i);

                            String id = jObject.getString("id");
                            String user_name = jObject.getString("name");
                            String user_image = jObject.getString("user_image");
                            String date = jObject.getString("date");
                            String comment = jObject.getString("comment");

                            SnsCommentListViewPOJO pojo = new SnsCommentListViewPOJO();

                            pojo.setComment_id(id);
                            pojo.setComment_name(user_name);
                            pojo.setComment_user_image(user_image);
                            pojo.setComment_date(date);
                            pojo.setComment_msg(comment);

                            adapter.addItem(pojo);

                        }
                    } else if (result_code.equals("0001"))
                        Toast.makeText(CommentActivity.this, "0001.", Toast.LENGTH_SHORT).show();
                    else if (result_code.equals("0002"))
                        Toast.makeText(CommentActivity.this, "0002.", Toast.LENGTH_SHORT).show();
                    else {
                        Toast.makeText(CommentActivity.this, "Result 코드 에러.", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Toast.makeText(CommentActivity.this, "서버와의 통신 중 오류가 발생했습니다. 나중에 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
    }

    private void comment_write() {
        now = System.currentTimeMillis();
        date = new Date(now);
        String comment_id = GlobalInfo.my_profile.getId();
        final String comment_date = mFormat.format(date);
        final String comment_edittext = edittext.getText().toString();

        if (edittext.length() == 0)
            Toast.makeText(CommentActivity.this, "내용을 입력해 주세요.", Toast.LENGTH_SHORT).show();
        else {
            AQuery aQuery = new AQuery(CommentActivity.this);
            String url = Config.Server_URL + "sns/insertComment";

            Map<String, Object> params = new LinkedHashMap<>();

            Intent it = getIntent();
            params.put("index", it.getStringExtra("index"));
            params.put("id", comment_id);
            params.put("date", comment_date);
            params.put("msg", comment_edittext);

            aQuery.ajax(url, params, String.class, new AjaxCallback<String>() {
                @Override
                public void callback(String url, String result, AjaxStatus status) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        String result_value = jsonObject.get("result").toString();

                        if (result_value.equals("0000")) {
                            SnsCommentListViewPOJO data = new SnsCommentListViewPOJO();
                            data.setComment_date(comment_date);
                            data.setComment_id(GlobalInfo.my_profile.getId());
                            data.setComment_msg(comment_edittext);
                            data.setComment_name(GlobalInfo.my_profile.getName());
                            data.setComment_user_image(GlobalInfo.my_profile.getImage());

                            adapter.addItem(data);

                            Toast.makeText(CommentActivity.this, "댓글 등록이 완료 되었습니다.", Toast.LENGTH_SHORT).show();
                        } else if (result_value.equals("0001")) {
                            Toast.makeText(CommentActivity.this, "0001.", Toast.LENGTH_SHORT).show();
                        } else if (result_value.equals("0002")) {
                            Toast.makeText(CommentActivity.this, "0002.", Toast.LENGTH_SHORT).show();
                        } else if (result_value.equals("0003")) {
                            Toast.makeText(CommentActivity.this, "0003.", Toast.LENGTH_SHORT).show();
                        } else if (result_value.equals("0004")) {
                            Toast.makeText(CommentActivity.this, "0004.", Toast.LENGTH_SHORT).show();
                        } else if (result_value.equals("0100")) {
                            Toast.makeText(CommentActivity.this, "0100.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(CommentActivity.this, "Result 코드 에러", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        Toast.makeText(CommentActivity.this, "서버와의 통신 중 오류가 발생했습니다. 나중에 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
