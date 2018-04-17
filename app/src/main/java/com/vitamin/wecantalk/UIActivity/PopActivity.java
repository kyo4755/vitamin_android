package com.vitamin.wecantalk.UIActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.vitamin.wecantalk.R;

public class PopActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * 0.9), (int) (height * 0.85));
    }

}
