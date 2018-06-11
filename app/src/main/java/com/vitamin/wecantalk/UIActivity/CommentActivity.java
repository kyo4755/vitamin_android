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
    EditText edittext;
    Button input_button;
    TextView userId;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sns_comment_layout);

        ListView listView = findViewById(R.id.comment_listview);
        final SnsCommentListViewAdapter adapter = new SnsCommentListViewAdapter();
        listView.setAdapter(adapter);

        userId = findViewById(R.id.comment_userId);
        userId.setText(GlobalInfo.my_profile.getName());

        edittext = findViewById(R.id.comment_edittext);
        input_button = findViewById(R.id.comment_input_button);

        input_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               comment_write();
            }
        });

        //final ArrayList<CommunityListViewPOJO> list = new ArrayList<>();

        AQuery aQuery = new AQuery(this);
        String sns_list_url = Config.Server_URL + "sns/getCommentList";

        Map<String, Object> params = new LinkedHashMap<>();

        params.put("index", 1);

        aQuery.ajax(sns_list_url, params, String.class, new AjaxCallback<String>() {
            @Override
            public void callback(String url, String result, AjaxStatus status) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String result_code = jsonObject.get("result").toString();
                    if (result_code.equals("0000")) {
                        Toast.makeText(CommentActivity.this, "정상.", Toast.LENGTH_SHORT).show();
                        String temp_sns = jsonObject.get("sns_list").toString();
                        JSONArray jsonArray = new JSONArray(jsonObject.get("sns_list").toString());
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jObject = jsonArray.getJSONObject(i);

                            String id = jObject.getString("id");
                            String user_name = jObject.getString("user_name");
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
                    }

                    else if(result_code.equals("0001")) Toast.makeText(CommentActivity.this, "입력받은 ID가 없음.", Toast.LENGTH_SHORT).show();
                    else if(result_code.equals("0002")) Toast.makeText(CommentActivity.this, "채팅방이 없음 왕따쓰.", Toast.LENGTH_SHORT).show();
                    else{}

                } catch (Exception e) {

                }
            }
        });
    }

    private void comment_write() {

        now = System.currentTimeMillis();
        date = new Date(now);

        String comment_id = userId.getText().toString();
        String comment_date = mFormat.format(date);
        String comment_edittext = edittext.getText().toString();


                    if (edittext.length() == 0)
                        Toast.makeText(CommentActivity.this, "내용을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    else {

                        AQuery aQuery = new AQuery(CommentActivity.this);
                        String url = Config.Server_URL + "sns/insertComment";

                        Map<String, Object> params = new LinkedHashMap<>();

                        params.put("index", 1);
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
                                       /* String imgStr = Config.Server_URL + "users/getPhoto?id=" + image_code;
                                        GlobalInfo.my_profile.setImage(image_code);
                                        Glide.with(CommentActivity.this)
                                                .load(imgStr)
                                                .centerCrop()
                                                .bitmapTransform(new CropCircleTransformation(CommentActivity.this))
                                                .skipMemoryCache(true)
                                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                                .into(image);*/
                                    }
                                    else if(result_value.equals("0001")){Toast.makeText(CommentActivity.this, "0001.", Toast.LENGTH_SHORT).show();}
                                    else if(result_value.equals("0002")){Toast.makeText(CommentActivity.this, "0002.", Toast.LENGTH_SHORT).show();}
                                    else if(result_value.equals("0003")){Toast.makeText(CommentActivity.this, "0003.", Toast.LENGTH_SHORT).show();}
                                    else if(result_value.equals("0004")){Toast.makeText(CommentActivity.this, "0004.", Toast.LENGTH_SHORT).show();}
                                    else if(result_value.equals("0100")){Toast.makeText(CommentActivity.this, "0100.", Toast.LENGTH_SHORT).show();}
                                    else {
                                        Toast.makeText(CommentActivity.this, "서버와의 통신 중 오류가 발생했습니다. 나중에 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                                    }

                                } catch (Exception e) {

                                }
                            }
                        });
                    }







    }


}
