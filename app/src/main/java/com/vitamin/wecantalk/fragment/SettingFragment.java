package com.vitamin.wecantalk.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.vitamin.wecantalk.Network.RequestTask;
import com.vitamin.wecantalk.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static android.app.Activity.RESULT_OK;

/**
 * Created by JongHwa on 2018-04-13.
 */


public class SettingFragment extends Fragment{
    ImageView pro1;
    private static final int SELECT_PICTURE = 1;
    private String selectedImagePath;
    Context context = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        context = container.getContext();

        View view = inflater.inflate(R.layout.fragment_setting, null);

        pro1 = (ImageView) view.findViewById(R.id.pro);
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

        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);

                Bitmap bm = null; //Bitmap 로드
                try {
                    bm = MediaStore.Images.Media.getBitmap(context.getContentResolver(), selectedImageUri);
                    ByteArrayOutputStream  byteArray = new ByteArrayOutputStream();
                    bm.compress(Bitmap.CompressFormat.JPEG, 50, byteArray);
                    byte[] imgbytes = byteArray.toByteArray();

                    String url = "http://13.124.62.147:10230/change_photo";
                    ContentValues values = new ContentValues();
                    values.put("id", id값);
                    values.put("image", imgbytes);
                    RequestTask requestTask = new RequestTask(url, values);
                    String abc = requestTask.execute().get();

                    Glide.with(context)
                            .load(imgbytes)
                            .centerCrop()
                            .bitmapTransform(new CropCircleTransformation(context))
                            .into(pro1);

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }


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
}
