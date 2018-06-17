package com.vitamin.wecantalk.POJO;

import android.graphics.drawable.Drawable;

/**
 * Created by 215 on 2018-04-11.
 */

public class SnsCommentListViewPOJO {

    private String comment_id;
    private String comment_name;
    private String comment_user_image;
    private String comment_date;
    private String comment_msg;
    private String comment_count;

    public String getComment_count() {
        return comment_count;
    }

    public void setComment_count(String comment_count) {
        this.comment_count = comment_count;
    }



    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }

    public String getComment_name() {
        return comment_name;
    }

    public void setComment_name(String comment_name) {
        this.comment_name = comment_name;
    }

    public String getComment_user_image() {
        return comment_user_image;
    }

    public void setComment_user_image(String comment_user_image) {
        this.comment_user_image = comment_user_image;
    }

    public String getComment_date() {
        return comment_date;
    }

    public void setComment_date(String comment_date) {
        this.comment_date = comment_date;
    }

    public String getComment_msg() {
        return comment_msg;
    }

    public void setComment_msg(String comment_msg) {
        this.comment_msg = comment_msg;
    }
}
