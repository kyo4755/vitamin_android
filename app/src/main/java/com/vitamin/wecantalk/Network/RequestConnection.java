package com.vitamin.wecantalk.Network;

import android.content.ContentValues;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;

/**
 * Created by Kim Jong-Hwa on 2018-05-05.
 */

public class RequestConnection {

    public String request(String url, ContentValues params){
        HttpURLConnection urlConn = null;
        StringBuffer sbParams = new StringBuffer();

        if(params == null)  sbParams.append("");

        else{
            boolean isAnd = false;
            String key, value;

            sbParams.append("?");

            for(Map.Entry<String, Object> parameter : params.valueSet()) {
                key = parameter.getKey();
                value = parameter.getValue().toString();

                if(isAnd)   sbParams.append("&");

                sbParams.append(key).append("=").append(value);

                if(!isAnd){
                    if(params.size() >= 2)  isAnd = true;
                }
            }
        }

        String request_url = url + sbParams;

        try{
            URL request = new URL(request_url);
            urlConn = (HttpURLConnection) request.openConnection();

            urlConn.setRequestMethod("GET");
            urlConn.setRequestProperty("Accept-Chatset", "UTF-8");
            urlConn.setRequestProperty("Context_Type", "application/x-www-form-urlencoded;charset=UTF-8");

            if (urlConn.getResponseCode() != HttpURLConnection.HTTP_OK){
                return null;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), "UTF-8"));

            String line;
            StringBuffer page = new StringBuffer();

            while( (line = reader.readLine()) != null)  page.append(line);

            return page.toString();

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConn != null){  // 코드 모두 실행 후 연결되어 있는 urlConn 해제
                urlConn.disconnect();
            }
        }

        return "end";
    }
}
