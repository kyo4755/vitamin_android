package com.vitamin.wecantalk.Setting;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Window;

import com.vitamin.wecantalk.R;

/**
 * Created by SKY on 2017-11-28.
 */

public class SettingIntroduce extends Dialog implements DialogInterface.OnClickListener {
    public SettingIntroduce(Context context){
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.setting_introducing);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

    }
}
