package com.vitamin.wecantalk.Setting;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.vitamin.wecantalk.R;

/**
 * Created by SKY on 2017-11-30.
 */

public class ChangeInfoSetting extends Dialog implements DialogInterface.OnClickListener {
    Button check;
    Button change;
    TextView userID;
    EditText spw,sname,sphone,smail;
    public ChangeInfoSetting(Context context){

        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.settingbutton_changeinfo);
        userID = (TextView) findViewById(R.id.userID);
        change = (Button)findViewById(R.id.change);
        sname = (EditText)findViewById(R.id.sname);
        smail = (EditText)findViewById(R.id.smail);
        sphone = (EditText)findViewById(R.id.sphone);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

    }
}
