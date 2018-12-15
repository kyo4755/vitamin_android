package com.vitamin.wecantalk.UIActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.vitamin.wecantalk.Adapter.FriendsRecognitionListViewAdapter;
import com.vitamin.wecantalk.Common.Config;
import com.vitamin.wecantalk.Common.GlobalInfo;
import com.vitamin.wecantalk.POJO.FriendsListViewPOJO;
import com.vitamin.wecantalk.POJO.FriendsRecognitionListViewPOJO;
import com.vitamin.wecantalk.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class FindImgActivity extends AppCompatActivity {

    Context context;
    private static final int SELECT_PICTURE = 1;
    ImageView imageView;
    ImageView ImageView_btn;
    File selectedPhoto;
    ListView listview;
    FriendsRecognitionListViewAdapter adapter;
    LottieAnimationView loading_animation;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_img_friends);
        context = FindImgActivity.this;

        loading_animation=findViewById(R.id.find_image_animation);
        loading_animation.setAnimation("spinne.json");
        listview=findViewById(R.id.recognition_listview1);

        adapter=new FriendsRecognitionListViewAdapter();

        listview.setAdapter(adapter);
        //listview.setVisibility(View.INVISIBLE);

        imageView = findViewById(R.id.friend_find_img);
        ImageView_btn = findViewById(R.id.friends_find_img_button);

        imageView.setVisibility(View.INVISIBLE);

        ImageView_btn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // 사진 선택
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,
                        "Select Picture"), SELECT_PICTURE);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {

                Uri selectedImageUri = data.getData();
                imageView.setImageURI(selectedImageUri);
                imageView.setVisibility(View.VISIBLE);
                File tmpCacheFile = new File(this.getCacheDir(), UUID.randomUUID() + ".jpg");

                if(fileCopy(selectedImageUri, tmpCacheFile)){
                    selectedPhoto = tmpCacheFile;

                    AQuery aQuery = new AQuery(FindImgActivity.this);
                    String url = Config.Server_URL + "recognition/compare";
                    Map<String, Object> params = new LinkedHashMap<>();
                    params.put("id", GlobalInfo.my_profile.getImage());
                    params.put("image", selectedPhoto);
                    loading_animation.setVisibility(View.VISIBLE);
                    loading_animation.playAnimation();
                    aQuery.ajax(url, params, String.class, new AjaxCallback<String>(){
                        @Override
                        public void callback(String url, String result, AjaxStatus status) {
                            //loading_animation3.setVisibility(View.GONE);
                            try{
                                JSONObject jsonObject = new JSONObject(result);
                                String result_value = jsonObject.get("result").toString();

                                if(result_value.equals("0000")){
                                    loading_animation.setVisibility(View.GONE);
                                    loading_animation.cancelAnimation();
                                    ArrayList<FriendsRecognitionListViewPOJO> friendsRecognitionListViewPOJO= new ArrayList<>();
                                    JSONArray jsonArray = new JSONArray(jsonObject.get("content").toString());
                                    Log.d("asdf",jsonArray.toString());
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jObject = jsonArray.getJSONObject(i);
                                        FriendsRecognitionListViewPOJO pojo = new FriendsRecognitionListViewPOJO();
                                        pojo.setId(jObject.getString("id").toString());
                                        pojo.setName(jObject.getString("name").toString());
                                        pojo.setSimilarity(jObject.getString("similarity").toString());
                                        pojo.setImage(jObject.get("image").toString());

                                        friendsRecognitionListViewPOJO.add(pojo);
                                    }

                                    Collections.sort(friendsRecognitionListViewPOJO, similarSort);
                                    adapter.setList(friendsRecognitionListViewPOJO);

                                } else {
                                    Toast.makeText(FindImgActivity.this, "Result 코드 에러", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e){
                                Toast.makeText(FindImgActivity.this, "서버와의 통신 중 오류가 발생했습니다. 나중에 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

                /*selectedImagePath = getPath(selectedImageUri);

                Bitmap bm = null; //Bitmap 로드
                try {
                    bm = MediaStore.Images.Media.getBitmap(context.getContentResolver(), selectedImageUri);
                    ByteArrayOutputStream  byteArray = new ByteArrayOutputStream();
                    bm.compress(Bitmap.CompressFormat.JPEG, 50, byteArray);
                    byte[] imgbytes = byteArray.toByteArray();
                    String imgStr = new String(Base64.encodeToString(imgbytes, Base64.DEFAULT));

                    String url = "http://13.124.62.147:10230/change_photo";
                    ContentValues values = new ContentValues();
                    values.put("id", GlobalInfo.my_profile.getId());
                    values.put("image", imgStr);
                    RequestTask requestTask = new RequestTask(url, values);
                    String result = requestTask.execute().get();

                    JSONObject jsonObject = new JSONObject(result);
                    String result_value = jsonObject.get("result").toString();

                    if(result_value.equals("0000")){
                        GlobalInfo.my_profile.setImage(imgStr);
                        Glide.with(context)
                                .load(imgbytes)
                                .centerCrop()
                                .bitmapTransform(new CropCircleTransformation(context))
                                .into(pro1);
                    } else {
                        Toast.makeText(context, "서버와의 통신 중 오류가 발생했습니다. 나중에 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
            }
        }

        //listview.setVisibility(View.VISIBLE);
    }
    protected boolean fileCopy(Uri in, File out) {
        try {
            File inFile = new File(in.getPath());
            InputStream is = new FileInputStream(inFile);
            // InputStream is =
            // context.getContentResolver().openInputStream(in);
            FileOutputStream outputStream = new FileOutputStream(out);

            BufferedInputStream bin = new BufferedInputStream(is);
            BufferedOutputStream bout = new BufferedOutputStream(outputStream);

            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = bin.read(buffer, 0, 1024)) != -1) {
                bout.write(buffer, 0, bytesRead);
            }

            bout.close();
            bin.close();

            outputStream.close();
            is.close();
        } catch (IOException e) {
            InputStream is;
            try {
                is = FindImgActivity.this.getContentResolver().openInputStream(in);

                FileOutputStream outputStream = new FileOutputStream(out);

                BufferedInputStream bin = new BufferedInputStream(is);
                BufferedOutputStream bout = new BufferedOutputStream(outputStream);

                int bytesRead = 0;
                byte[] buffer = new byte[1024];
                while ((bytesRead = bin.read(buffer, 0, 1024)) != -1) {
                    bout.write(buffer, 0, bytesRead);
                }

                bout.close();
                bin.close();

                outputStream.close();
                is.close();


            } catch (IOException e1) {
                e1.printStackTrace();
                return false;
            }

        }
        return true;
    }

    Comparator<FriendsRecognitionListViewPOJO> similarSort = new Comparator<FriendsRecognitionListViewPOJO>() {
        @Override
        public int compare(FriendsRecognitionListViewPOJO item1, FriendsRecognitionListViewPOJO item2) {
            return item1.getSimilarity().compareTo(item2.getSimilarity());
        }
    };



}
