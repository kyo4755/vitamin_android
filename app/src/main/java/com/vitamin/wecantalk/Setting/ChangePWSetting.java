package com.vitamin.wecantalk.Setting;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.vitamin.wecantalk.R;

/**
 * Created by SKY on 2017-11-29.
 */

public class ChangePWSetting extends Dialog implements DialogInterface.OnClickListener {
    Button check;
    Button change;
    EditText before,after1,after2;
    public ChangePWSetting(Context context){
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.settingbutton_changepw);
        change = (Button)findViewById(R.id.change);
        before = (EditText)findViewById(R.id.before);
        after1 = (EditText)findViewById(R.id.after1);
        after2 = (EditText)findViewById(R.id.after2);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

    }
}
