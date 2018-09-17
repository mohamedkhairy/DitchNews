package com.example.khairy.ditchnews;

import com.example.khairy.ditchnews.model.Article;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by khairy on 12/31/2017.
 */

public class NewsStore {

    private static List<Article> news_articles = new ArrayList<>();

    public static List<Article> getNews_articles() {
        return news_articles;
    }

    public static void setNews_articles(List<Article> news_articles) {
        NewsStore.news_articles = news_articles;
    }
}
