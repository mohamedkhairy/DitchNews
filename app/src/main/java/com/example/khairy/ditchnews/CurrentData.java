package com.example.khairy.ditchnews;

import java.util.ArrayList;
import java.util.List;

public class CurrentData {


    private String title;
    private String description;
    private String url;
    private String imageUrl;
    private String date;

    public CurrentData(String title, String description, String url, String imageUrl, String date) {

        this.title = title;
        this.description = description;
        this.url = url;
        this.imageUrl = imageUrl;
        this.date = date;
    }


    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDate() {
        return date;
    }
}
