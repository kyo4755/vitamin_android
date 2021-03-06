package com.vitamin.wecantalk.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.vitamin.wecantalk.Notepad.NoteList;
import com.vitamin.wecantalk.POJO.SnsListViewPOJO;
import com.vitamin.wecantalk.R;
import com.vitamin.wecantalk.Setting.ChangeInfoSetting;
import com.vitamin.wecantalk.Setting.ChangePWSetting;
import com.vitamin.wecantalk.Setting.SettingDialog;
import com.vitamin.wecantalk.Setting.SettingIntroduce;
import com.vitamin.wecantalk.UIActivity.FriendsSnsActivity;
import com.vitamin.wecantalk.UIActivity.NaverDicActivity;
import com.vitamin.wecantalk.UIActivity.StartActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static android.app.Activity.RESULT_OK;

/**
 * Created by JongHwa on 2018-04-13.
 */


public class SettingFragment extends Fragment{
    Button b1;
    TextView f_number;
    TextView introduce;
    TextView introducing;
    TextView notepad_go;
    TextView naver_dic;

    ImageView pro1;
    ImageView pro2;
    private static final int SELECT_PICTURE = 1;
    Context context = null;
    SettingDialog settingDialog;
    ChangePWSetting changePWSetting;
    ChangeInfoSetting changeInfoSetting;
    SettingIntroduce settingIntroduce;

    File selectedPhoto;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        context = container.getContext();

        View view = inflater.inflate(R.layout.fragment_setting, null);

        b1 = (Button) view.findViewById(R.id.logout);
        pro1 = (ImageView) view.findViewById(R.id.pro);
        f_number =(TextView) view.findViewById(R.id.f_number);
        introduce =(TextView) view.findViewById(R.id.go_introduce);
        introducing =(TextView) view.findViewById(R.id.introducing);
        notepad_go =(TextView) view.findViewById(R.id.notepad_go);
        naver_dic=(TextView) view.findViewById(R.id.naver_dic);

        String img_url = Config.Server_URL + "users/getPhoto?id=" + GlobalInfo.my_profile.getImage();
        Glide.with(this)
                .load(img_url)
                .error(R.drawable.default_user)
                .centerCrop()
                .bitmapTransform(new CropCircleTransformation(context))
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(pro1);

        pro1.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View arg0) {
                        // 사진 선택
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent,
                                "Select Picture"), SELECT_PICTURE);
                    }
                });

        b1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent it = new Intent(getActivity(), StartActivity.class);
                startActivity(it);
            }
        });


        notepad_go.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent it = new Intent(getActivity(), NoteList.class);
                startActivity(it);
            }
        });

        naver_dic.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent it = new Intent(getActivity(), NaverDicActivity.class);
                startActivity(it);
            }
        });

        AQuery aQuery = new AQuery(context);
        String find_friend_url = Config.Server_URL + "friends/getNumber";

        Map<String, Object> params = new LinkedHashMap<>();

        params.put("id", GlobalInfo.my_profile.getId());

        aQuery.ajax(find_friend_url, params, String.class, new AjaxCallback<String>() {
            @Override
            public void callback(String url, String result, AjaxStatus status) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String result_code = jsonObject.get("result").toString();
                    if (result_code.equals("0000")) {//정상 나올때
                        String a = jsonObject.get("f_number").toString();
                        f_number.setText(a);
                    }
                } catch (Exception e) {
                    Toast.makeText(context, "이상현상", Toast.LENGTH_SHORT).show();
                }
            }
        });

        introduce.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                settingIntroduce = new SettingIntroduce(getActivity());
                settingIntroduce.show();
                Button change = settingIntroduce.findViewById(R.id.change_introduce);
                Button back = settingIntroduce.findViewById(R.id.back);

                change.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText edit_introduce = settingIntroduce.findViewById(R.id.introduce);
                        final String edit_str = edit_introduce.getText().toString();
                        if(edit_str.length() == 0){
                            Toast.makeText(context, "내용을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                        } else {
                            AQuery aQuery = new AQuery(context);
                            String sns_list_url = Config.Server_URL + "settings/introduce";

                            Map<String, Object> params = new LinkedHashMap<>();
                            params.put("id", GlobalInfo.my_profile.getId());
                            params.put("msg", edit_str);

                            aQuery.ajax(sns_list_url, params, String.class, new AjaxCallback<String>() {
                                @Override
                                public void callback(String url, String result, AjaxStatus status) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(result);
                                        String result_code = jsonObject.get("result").toString();
                                        if (result_code.equals("0000")) {
                                            GlobalInfo.my_profile.setStatus_msg(edit_str);
                                            introducing.setText(edit_str);
                                            settingIntroduce.dismiss();
                                        }
                                        else if(result_code.equals("0001")) Toast.makeText(context, "ID 전송 오류", Toast.LENGTH_SHORT).show();
                                        else if(result_code.equals("0002")) Toast.makeText(context, "MSG 전송 오류", Toast.LENGTH_SHORT).show();
                                        else{
                                            Toast.makeText(context, "Result 코드 에러.", Toast.LENGTH_SHORT).show();
                                        }

                                    } catch (Exception e) {
                                        Toast.makeText(context, "서버와의 통신 중 오류가 발생했습니다. 나중에 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                });

                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        settingIntroduce.dismiss();
                    }
                });
            }
        });

        pro2 = (ImageView) view.findViewById(R.id.setting);
        pro2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
//                Intent intent = new Intent(context, ChangeInfoSetting.class);
//                startActivity(intent);

                settingDialog = new SettingDialog(getActivity());
                settingDialog.show();
                Button changePW = (Button)settingDialog.findViewById(R.id.changePW);
                final Button changeInfo = (Button)settingDialog.findViewById(R.id.changeInfo);
                Button back = (Button)settingDialog.findViewById(R.id.back);


                changePW.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        changePWSetting = new ChangePWSetting(getActivity());
                        changePWSetting.show();
                        Button back = (Button)changePWSetting.findViewById(R.id.back);
                        back.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                changePWSetting.dismiss();
                            }
                        });
                    }
                });

                changeInfo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        changeInfoSetting = new ChangeInfoSetting(getActivity());
                        changeInfoSetting.show();
                        Button back = (Button)changeInfoSetting.findViewById(R.id.back);
                        back.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                changeInfoSetting.dismiss();
                            }
                        });
                    }
                });

                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        settingDialog.dismiss();
                    }
                });

            }
        });

        if(GlobalInfo.my_profile.getStatus_msg().equals("null")){
            introducing.setText("");
        } else {
            introducing.setText(GlobalInfo.my_profile.getStatus_msg());
        }
        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                File tmpCacheFile = new File(context.getCacheDir(), UUID.randomUUID() + ".jpg");

                if(fileCopy(selectedImageUri, tmpCacheFile)){
                    selectedPhoto = tmpCacheFile;

                    AQuery aQuery = new AQuery(context);
                    String url = Config.Server_URL + "users/changePhoto";
                    Map<String, Object> params = new LinkedHashMap<>();
                    params.put("id", GlobalInfo.my_profile.getId());
                    params.put("image", selectedPhoto);

                    aQuery.ajax(url, params, String.class, new AjaxCallback<String>(){
                        @Override
                        public void callback(String url, String result, AjaxStatus status) {
                            try{
                                JSONObject jsonObject = new JSONObject(result);
                                String result_value = jsonObject.get("result").toString();
                                String image_code = jsonObject.get("image").toString();

                                if(result_value.equals("0000")){
                                    String imgStr = Config.Server_URL + "users/getPhoto?id=" + image_code;
                                    GlobalInfo.my_profile.setImage(image_code);
                                    Glide.with(context)
                                            .load(imgStr)
                                            .centerCrop()
                                            .bitmapTransform(new CropCircleTransformation(context))
                                            .skipMemoryCache(true)
                                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                                            .into(pro1);
                                } else {
                                    Toast.makeText(context, "Result 코드 에러", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e){
                                Toast.makeText(context, "서버와의 통신 중 오류가 발생했습니다. 나중에 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
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
    }

    /**
     * 사진의 URI 경로를 받는 메소드
     */
    public String getPath(Uri uri) {
        // uri가 null일경우 null반환
        if( uri == null ) {
            return null;
        }
        // 미디어스토어에서 유저가 선택한 사진의 URI를 받아온다.
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().managedQuery(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        // URI경로를 반환한다.
        return uri.getPath();
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
                is = context.getContentResolver().openInputStream(in);

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
}
