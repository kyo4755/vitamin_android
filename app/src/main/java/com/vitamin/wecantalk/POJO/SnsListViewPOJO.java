package com.vitamin.wecantalk.POJO;

import android.graphics.drawable.Drawable;

/**
 * Created by 215 on 2018-04-11.
 */

public class SnsListViewPOJO {


    private Drawable pofile;
    private String date;
    private Drawable image;
    private String name;
    private String context;

    public Drawable getPofile() {
        return pofile;
    }

    public void setPofile(Drawable pofile) {
        this.pofile = pofile;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
