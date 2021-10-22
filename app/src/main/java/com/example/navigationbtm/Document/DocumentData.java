package com.example.navigationbtm.Document;

public class DocumentData {
    String title,downloadurl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDownloadurl() {
        return downloadurl;
    }

    public void setDownloadurl(String downloadurl) {
        this.downloadurl = downloadurl;
    }

    public DocumentData(String title, String downloadurl) {
        this.title = title;
        this.downloadurl = downloadurl;
    }

    public DocumentData() {
    }
}
