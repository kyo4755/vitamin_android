package com.vitamin.wecantalk.POJO;

import android.graphics.drawable.Drawable;

/**
 * Created by 215 on 2018-04-11.
 */

public class FriendsListViewPOJO {
    private Drawable iconDrawable;
    private String titleStr;
    private String descStr;

    public void setIcon(Drawable icon) {
        this.iconDrawable = icon;
    }

    public void setTitle(String title) {
        titleStr = title;
    }

    public void setDesc(String desc) {
        this.descStr = desc;
    }

    public Drawable getIcon() {
        return this.iconDrawable;
    }

    public String getTitle() {
        return this.titleStr;
    }

    public String getDesc() {
        return this.descStr;
    }
}
