package com.example.navigationbtm.ui.gallery;

public class ImageData {
    String catogery,image,date,time,key;

    public ImageData() {
    }

    public ImageData(String catogery, String image, String date, String time, String key) {
        this.catogery = catogery;
        this.image = image;
        this.date = date;
        this.time = time;
        this.key = key;
    }

    public String getCatogery() {
        return catogery;
    }

    public void setCatogery(String title) {
        this.catogery = catogery;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
