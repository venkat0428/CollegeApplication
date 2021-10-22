package com.example.navigationbtm.videoLectures;

public class VideoData {
    String title, url, date, time;

    public VideoData() {
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public VideoData(String title, String url, String date, String time) {
        this.title = title;
        this.url = url;
        this.date = date;
        this.time = time;
    }
}
