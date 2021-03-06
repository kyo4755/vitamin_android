package com.vitamin.wecantalk.Common;

import com.vitamin.wecantalk.POJO.FriendsListViewPOJO;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;

/**
 * Created by Kim Jong-Hwa on 2018-05-05.
 */

public class GlobalInfo {

    public static ArrayList<FriendsListViewPOJO> friends_list;
    public static FriendsListViewPOJO my_profile = new FriendsListViewPOJO();


    public static String getUTF8Decode(String msg){
        try{
            return URLDecoder.decode(msg, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
