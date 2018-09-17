package com.example.khairy.ditchnews;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.khairy.ditchnews.firebase.Operations;
import com.example.khairy.ditchnews.model.GetArticlesResponse;

import com.example.khairy.ditchnews.networking.NewsAPI;
import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_recyclerView)
    RecyclerView news_Recyclerview;
    @BindView(R.id.activity_main_progressBar)
    ProgressBar progressBar;
    @BindView(R.id.activity_main)
    DrawerLayout drawerLayout;
    @BindView(R.id.gridnews)
    GridLayout gridLayout;
    @BindView(R.id.coordinator)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.noconnection)
    ImageView imageView;

    private ActionBarDrawerToggle toggle;
    private LinearLayout linearLayout;
    private int positionIndex;
    private String source;
    private String language;
    private boolean isSaved;
    private static final String JOB_TAG = "MyJobService";
    private FirebaseJobDispatcher jDispatcher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        jDispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));

        news_Recyclerview.setLayoutManager(new LinearLayoutManager(this));
        imageView.setVisibility(View.GONE);
        setToolbar();

        final int childCount = gridLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {

            linearLayout = (LinearLayout) gridLayout.getChildAt(i);
            final int position = i;
            linearLayout.setOnClickListener(v -> newsSelection(position));
        }

        if (isNetworkConnected()) {
            progressBar.setVisibility(View.VISIBLE);
            if (savedInstanceState != null) {
                source = savedInstanceState.getString("source");
                language = savedInstanceState.getString("language");
                positionIndex = savedInstanceState.getInt("position");
                isSaved = savedInstanceState.getBoolean("save", false);
                if (isSaved) {
                    Operations.getSaved(this);
                } else {
                    getNewsData(source , language);
                }

            } else {
                source = "google-news";
                language = "en";
                isSaved = false;
                getNewsData(source , language);
            }
        } else {
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageResource(R.drawable.nowifi);
        }

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null) {
            jDispatcher.cancelAll();

            return true;
        } else {
            StartJob();
            return false;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        int Index = ((LinearLayoutManager) news_Recyclerview.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
        outState.putInt("position", Index);
        outState.putString("source", source);
        outState.putString("language", language);
        outState.putBoolean("save", isSaved);

    }

    public void setToolbar() {
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.Open, R.string.Close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    // TODO(1) this method for calling api and parsing the json using Retrofit(use your API key)

    private void getNewsData(String source  , String sourceLanguage ) {
        retrofit2.Call<GetArticlesResponse> call = NewsAPI.getAPI().getarticles(source, sourceLanguage , "YOUR API KEY");
        call.enqueue(new Callback<GetArticlesResponse>() {
            @Override
            public void onResponse(retrofit2.Call<GetArticlesResponse> call, Response<GetArticlesResponse> response) {
                Log.d("URL : ", "response.raw().request().url();" + response.raw().request().url());

                progressBar.setVisibility(View.GONE);
                imageView.setVisibility(View.GONE);
                showNewsApiSnackbar();
                GetArticlesResponse getArticlesResponse = response.body();
                NewsStore.setNews_articles(getArticlesResponse.getArticles());
                NewsAdapter newsAdapter = new NewsAdapter(getArticlesResponse.getArticles(), false, MainActivity.this);
                news_Recyclerview.setAdapter(newsAdapter);
                if (positionIndex >= 0) {
                    news_Recyclerview.getLayoutManager().scrollToPosition(positionIndex);
                    newsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<GetArticlesResponse> call, Throwable t) {
                StartJob();
                t.printStackTrace();
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, getString(R.string.error), Toast.LENGTH_LONG).show();
            }
        });
    }



    private void newsSelection(int position) {
        switch (position) {
            case 0:
                source = "abc-news";
                language = "en";
                getNewsData(source , language);
                break;
            case 1:
                source = "argaam";
                language = "ar";
                getNewsData(source , language);
                break;
            case 2:
                source = "buzzfeed";
                language = "en";
                getNewsData(source , language);
                break;
            case 3:
                source = "cnn";
                language = "en";
                getNewsData(source , language);
                break;
            case 4:
                source = "daily-mail";
                language = "en";
                getNewsData(source , language);
                break;
            case 5:
                source = "google-news";
                language = "en";
                getNewsData(source , language);
                break;

        }

    }

// TODO(13) when save clicked  from menu

    @OnClick(R.id.saved)
    public void onClickSaved() {
        isSaved = true;
        Operations.getSaved(this);
    }


    @OnClick(R.id.refresh)
    public void onClickRefresh() {

        if (isNetworkConnected()){
            Snackbar.make(drawerLayout, "already Connected", Snackbar.LENGTH_LONG).show();
        }else {
            Snackbar.make(drawerLayout, "trying to connect...", Snackbar.LENGTH_LONG).show();
        }
    }

    ///////////Show snackbar in coordinate layout /////////////
    public void showNewsApiSnackbar() {
        Snackbar.make(drawerLayout, getString(R.string.powered), Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.visit), view -> loadNewsApiWebsite()).show();
    }

    private void loadNewsApiWebsite() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://newsapi.org"));
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (toggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    private void StartJob() {
        Job myJob = jDispatcher.newJobBuilder()
                .setService(JobDispatcher.class)
                .setTag(JOB_TAG)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(5, 15))
                .setLifetime(Lifetime.UNTIL_NEXT_BOOT)
                .setReplaceCurrent(false)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                .build();
        jDispatcher.mustSchedule(myJob);
    }
}
