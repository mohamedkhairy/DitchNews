package com.example.khairy.ditchnews.firebase;

public class SavedArticle {

    public String articleID;
    public String title;
    public String description;
    public String url;
    public String imageUrl;
    public String date;

    public SavedArticle() {
    }

    public SavedArticle(String ID, String title, String description, String url, String imageUrl, String date) {
        this.articleID = ID;
        this.title = title;
        this.description = description;
        this.url = url;
        this.imageUrl = imageUrl;
        this.date = date;
    }

}
