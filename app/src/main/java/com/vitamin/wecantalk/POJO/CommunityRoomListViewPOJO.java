package com.vitamin.wecantalk.POJO;

/**
 * Created by JongHwa on 2018-04-17.
 */

public class CommunityRoomListViewPOJO {

    private String img;
    private String name;
    private String id;
    private String msg;
    private String time;
    private int where;

    public int getWhere() {
        return where;
    }

    public void setWhere(int where) {
        this.where = where;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}
}
