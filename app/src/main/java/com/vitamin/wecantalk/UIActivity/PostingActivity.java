package com.vitamin.wecantalk.UIActivity;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.AccessController;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static com.androidquery.util.AQUtility.getContext;


public class PostingActivity  extends AppCompatActivity {

    public static final int REQUEST_IMAGE_CODE = 0x0100;
    long now;
    Date date;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    ImageView image, posting_profile;
    TextView userId;
    EditText editContext;
    Button share;
    File selectedPhoto;
    private static final int SELECT_PICTURE = 1;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sns_post);


        editContext = findViewById(R.id.posting_context);
        userId = (TextView) findViewById(R.id.posting_userId);
        userId.setText(GlobalInfo.my_profile.getName());

        posting_profile =(ImageView) findViewById(R.id.posting_profile) ;

            String img_url = Config.Server_URL + "users/getPhoto?id=" + GlobalInfo.my_profile.getImage();
            Glide.with(PostingActivity.this)
                    .load(img_url)
                    .error(R.drawable.default_user)
                    .centerCrop()
                    .bitmapTransform(new CropCircleTransformation(PostingActivity.this))
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(posting_profile);



        image = (ImageView) findViewById(R.id.posting_image);
        image.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                // 사진 선택
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,
                        "Select Picture"), SELECT_PICTURE);
            }
        });

        share = findViewById(R.id.posting_share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    // 이미지이미지이미지
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        now = System.currentTimeMillis();
        date = new Date(now);

        String posting_id = userId.getText().toString();
        String posting_date = mFormat.format(date);
        String posting_context = editContext.getText().toString();

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                File tmpCacheFile = new File(PostingActivity.this.getCacheDir(), UUID.randomUUID() + ".jpg");

                if (fileCopy(selectedImageUri, tmpCacheFile)) {
                    selectedPhoto = tmpCacheFile;

                    if (posting_context.length() == 0)
                        Toast.makeText(PostingActivity.this, "내용을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    else {

                        AQuery aQuery = new AQuery(PostingActivity.this);
                        String url = Config.Server_URL + "sns/insertContent";

                        Map<String, Object> params = new LinkedHashMap<>();

                        params.put("id", posting_id);
                        params.put("date", posting_date);
                        params.put("content_text", posting_context);
                        Log.e("content_text", posting_context.toString());
                        if (selectedPhoto != null) {
                            params.put("content_image", selectedPhoto);
                        }
                        {
                            Log.e("content_text", "123123123123");
                        }

                        aQuery.ajax(url, params, String.class, new AjaxCallback<String>() {
                            @Override
                            public void callback(String url, String result, AjaxStatus status) {
                                try {
                                    JSONObject jsonObject = new JSONObject(result);
                                    String result_value = jsonObject.get("result").toString();
                                    String image_code = jsonObject.get("image").toString();

                                    if (result_value.equals("0000")) {
                                        String imgStr = Config.Server_URL + "users/getPhoto?id=" + image_code;
                                        GlobalInfo.my_profile.setImage(image_code);
                                        Glide.with(PostingActivity.this)
                                                .load(imgStr)
                                                .centerCrop()
                                                .bitmapTransform(new CropCircleTransformation(PostingActivity.this))
                                                .skipMemoryCache(true)
                                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                                .into(image);
                                    } else {
                                        Toast.makeText(PostingActivity.this, "서버와의 통신 중 오류가 발생했습니다. 나중에 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                                    }

                                } catch (Exception e) {

                                }
                            }
                        });
                    }
                }
            }
        }
    }

//                /*selectedImagePath = getPath(selectedImageUri);


        //이미지이지밎이ㅣㅈ밎


//    private void posting() {
//
//        now = System.currentTimeMillis();
//        date = new Date(now);
//
//
//        if (posting_context.length() == 0)
//            Toast.makeText(PostingActivity.this, "내용을 입력해 주세요.", Toast.LENGTH_SHORT).show();
//        else {
//
//                AQuery aQuery = new AQuery(context);
//                String posting_url = Config.Server_URL + "sns/insertContent";
//
//                Map<String, Object> params = new LinkedHashMap<>();
//
//                params.put("id", posting_id);
//                params.put("date", posting_date);
//                params.put("content_text", posting_context);
//                Log.e("content_text", posting_context.toString());
//                if(selectedPhoto!=null) {
//                    params.put("content_image", selectedPhoto);
//                }{
//                Log.e("content_text", "123123123123");
//            }
//
//                aQuery.ajax(posting_url, params, String.class, new AjaxCallback<String>() {
//                    @Override
//                    public void callback(String url, String result, AjaxStatus status) {
//                        try {
//                            JSONObject jsonObject = new JSONObject(result);
//                            String result_code = jsonObject.get("result").toString();
//                            if (result_code.equals("0100")) {
//                                Toast.makeText(PostingActivity.this, "글쓰기 오류 0100", Toast.LENGTH_SHORT).show();
//                            } else if (result_code.equals("0001")) {
//                                Toast.makeText(PostingActivity.this, "글쓰기 오류0001", Toast.LENGTH_SHORT).show();
//                            } else if (result_code.equals("0002")) {
//                                Toast.makeText(PostingActivity.this, "글쓰기 오류0002", Toast.LENGTH_SHORT).show();
//                            } else if (result_code.equals("0003")) {
//                                Toast.makeText(PostingActivity.this, "글쓰기 오류0003", Toast.LENGTH_SHORT).show();
//                            } else {
//
//                                //loading_animation.setVisibility(View.VISIBLE);
//                                //loading_animation.playAnimation();
//
//                                Handler handler = new Handler();
//                                handler.postDelayed(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        //loading_animation.setVisibility(View.GONE);
//                                        //loading_animation.pauseAnimation();
//                                        Toast.makeText(PostingActivity.this, "글쓰기 완료", Toast.LENGTH_SHORT).show();
//                                        finish();
//                                    }
//                                }, 2500);
//
//                            }
//                        } catch (Exception e) {
//                            Toast.makeText(PostingActivity.this, "서버와의 통신 중 오류가 발생했습니다. 나중에 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
//                            Log.e("RegisgerTask", e.toString());
//                        }
//                    }
//                });
//            }
//        }


        public void startGallery () {
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_IMAGE_CODE);
                } else {
                    Intent cameraIntent = new Intent(Intent.ACTION_GET_CONTENT);
                    cameraIntent.setType("image/*");
                    if (cameraIntent.resolveActivity(this.getPackageManager()) != null) {
                        startActivityForResult(cameraIntent, REQUEST_IMAGE_CODE);
                    }
                }
            } else {
                Intent cameraIntent = new Intent(Intent.ACTION_GET_CONTENT);
                cameraIntent.setType("image/*");
                if (cameraIntent.resolveActivity(this.getPackageManager()) != null) {
                    startActivityForResult(cameraIntent, REQUEST_IMAGE_CODE);
                }
            }
        }

        protected boolean fileCopy(Uri in, File out){
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
                    is = getContentResolver().openInputStream(in);

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
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                    return false;
                }

            }

            return true;
        }
    }

