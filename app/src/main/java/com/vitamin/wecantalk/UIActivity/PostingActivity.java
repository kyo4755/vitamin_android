package com.vitamin.wecantalk.UIActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.vitamin.wecantalk.R;



public class PostingActivity  extends AppCompatActivity {
    ImageView image;
    private static final int SELECT_PICTURE = 1;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sns_post);

       image = (ImageView) findViewById(R.id.image);

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



    }
}
