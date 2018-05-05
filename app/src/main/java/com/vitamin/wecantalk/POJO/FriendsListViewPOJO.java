package com.vitamin.wecantalk.POJO;

/**
 * Created by 215 on 2018-04-11.
 */

public class FriendsListViewPOJO {

    private String id;
    private String email;
    private String nation;
    private String location;
    private String prefer_language;
    private String image;
    private String name;
    private String status_msg;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPrefer_language() {
        return prefer_language;
    }

    public void setPrefer_language(String prefer_language) {
        this.prefer_language = prefer_language;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setName(String title) {
        name = title;
    }

    public void setStatus_msg(String desc) {
        this.status_msg = desc;
    }

    public String getName() {
        return this.name;
    }

    public String getStatus_msg() {
        return this.status_msg;
    }
}
