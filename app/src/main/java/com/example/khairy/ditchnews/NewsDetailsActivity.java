package com.example.khairy.ditchnews;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsDetailsActivity extends AppCompatActivity {
    private static final String Key_Index = "news_index";
    @BindView(R.id.news_details_webview)
    WebView web_view;
    @BindView(R.id.news_details_progressbar)
    ProgressBar progress_bar;
    @BindView(R.id.details_toolbar)
    Toolbar toolbar;

    // TODO(12) this class for display the news content


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        ButterKnife.bind(this);

        toolbarView();
        int index = getIntent().getIntExtra(Key_Index, -1);
        if (index != -1) {
            udate_newsDetails(index);
        } else {
            Toast.makeText(NewsDetailsActivity.this, getString(R.string.incorrect), Toast.LENGTH_LONG).show();
        }

    }

    public void toolbarView() {

        this.setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    public void udate_newsDetails(int index) {

        web_view.getSettings().setJavaScriptEnabled(true);
        web_view.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progress_bar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progress_bar.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                progress_bar.setVisibility(View.GONE);
                Toast.makeText(NewsDetailsActivity.this, getString(R.string.error_loading), Toast.LENGTH_LONG).show();
            }
        });

        web_view.loadUrl(NewsStore.getNews_articles().get(index).getUrl());
        getSupportActionBar().setTitle(NewsStore.getNews_articles().get(index).getTitle());

    }

    public static void launch(Context context, int index) {
        Intent intent = new Intent(context, NewsDetailsActivity.class);
        intent.putExtra(Key_Index, index);
        context.startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
