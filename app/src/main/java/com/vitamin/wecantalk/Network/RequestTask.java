package com.vitamin.wecantalk.Network;

import android.content.ContentValues;
import android.os.AsyncTask;

/**
 * Created by Kim Jong-Hwa on 2018-05-05.
 */

public class RequestTask extends AsyncTask<Void, Void, String> {

    private String url;
    private ContentValues values;

    public RequestTask(String url, ContentValues values){
        this.url = url;
        this.values = values;
    }

    @Override
    protected String doInBackground(Void... voids) {
        String result;
        RequestConnection requestConnection = new RequestConnection();

        result = requestConnection.request(url, values);

        return result;
    }
}
