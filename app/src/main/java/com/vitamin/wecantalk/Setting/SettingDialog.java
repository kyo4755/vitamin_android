package com.vitamin.wecantalk.Setting;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Window;

import com.vitamin.wecantalk.R;

/**
 * Created by SKY on 2017-11-28.
 */

public class SettingDialog extends Dialog implements DialogInterface.OnClickListener {
    public SettingDialog(Context context){
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.settingbutton_setting);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

    }
}
