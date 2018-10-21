package com.vitamin.wecantalk.POJO;

/**
 * Created by 215 on 2018-04-11.
 */

public class FriendsRecognitionListViewPOJO {

    private String id;
    private String image;
    private String similarity;
    private String name;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getName() {
        return this.name;
    }

    public String getSimilarity() {
        return similarity;
    }

    public void setSimilarity(String similarity) {
        this.similarity = similarity;
    }

}
