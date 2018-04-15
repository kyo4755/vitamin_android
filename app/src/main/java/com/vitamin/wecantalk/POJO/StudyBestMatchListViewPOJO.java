package com.vitamin.wecantalk.POJO;

import android.graphics.drawable.Drawable;

/**
 * Created by JongHwa on 2018-04-15.
 */

public class StudyBestMatchListViewPOJO {

    private Drawable img;
    private String name;
    private String language;
    private String location;
    private String login_time;
    private String local_time;


    public StudyBestMatchListViewPOJO(Drawable img, String name, String language, String location, String login_time, String local_time){
        this.img = img;
        this.name = name;
        this.language = language;
        this.location = location;
        this.login_time = login_time;
        this.local_time = local_time;
    }

    public Drawable getImg() {
        return img;
    }

    public void setImg(Drawable img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLogin_time() {
        return login_time;
    }

    public void setLogin_time(String login_time) {
        this.login_time = login_time;
    }

    public String getLocal_time() {
        return local_time;
    }

    public void setLocal_time(String local_time) {
        this.local_time = local_time;
    }
}
