package com.vitamin.wecantalk.POJO;

import android.graphics.drawable.Drawable;

/**
 * Created by JongHwa on 2018-04-15.
 */

public class CommunityListViewPOJO {

    private String room_number;
    private String img;
    private String anid;
    private String title;
    private String recent_msg;
    private String recent_time;

    public String getRoom_number() {return room_number; }

    public void setRoom_number(String room_number) {this.room_number = room_number;}

    public String getAnid() {
        return anid;
    }

    public void setAnid(String anid) {
        this.anid = anid;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRecent_msg() {return recent_msg;}

    public void setRecent_msg(String recent_msg) {
        this.recent_msg = recent_msg;
    }

    public String getRecent_time() {
        return recent_time;
    }

    public void setRecent_time(String recent_time) {
        this.recent_time = recent_time;
    }
}
