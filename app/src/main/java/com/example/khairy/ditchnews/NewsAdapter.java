package com.example.khairy.ditchnews;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.khairy.ditchnews.firebase.Operations;
import com.example.khairy.ditchnews.firebase.SavedArticle;
import com.example.khairy.ditchnews.model.Article;
import com.example.khairy.ditchnews.utils.DateUtils;
import com.example.khairy.ditchnews.widget.NewsWidget;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by khairy on 1/2/2018.
 */

// TODO(4) this class about represent the data in a RecyclerView

public class NewsAdapter<T> extends RecyclerView.Adapter<NewsAdapter.HomeNewsViewHolder> {

    private Context context;

    // TODO(5) I used a generic list to pithiness the code

    List<T> news_Articles = new ArrayList<T>();
    private boolean isSaved;
    private CurrentData currentData;


    public NewsAdapter(List<T> Articles, boolean check, Context con) {

        this.news_Articles = (List<T>) Articles;
        this.isSaved = check;
        this.context = con;
    }

    @Override
    public HomeNewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_news, parent, false);
        HomeNewsViewHolder hnv_holder = new HomeNewsViewHolder(view);
        return hnv_holder;
    }

    @Override
    public void onBindViewHolder(final HomeNewsViewHolder holder, final int position) {

// TODO(6) this method will represent data from 2 sources (from DB or from API)



        if (isSaved) {
            final SavedArticle savedArticle = (SavedArticle) news_Articles.get(position);

            textCheck(holder,
                    savedArticle.imageUrl,
                    savedArticle.title,
                    savedArticle.date,
                    savedArticle.description);

        } else {
            final Article newsarticle = (Article) news_Articles.get(position);

            dateCheck(holder, newsarticle.getPublishedAt(), position);

            textCheck(holder,
                    newsarticle.getUrlToImage(),
                    newsarticle.getTitle(),
                    newsarticle.getPublishedAt(),
                    newsarticle.getDescription());
        }


        savedCheck(holder, position);

        // TODO(7) on News clicked

        holder.itemView.setOnClickListener(view -> {
            /// Lon Analytics (FIREBASE)
            FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(view.getContext());
            Bundle bundle = new Bundle();
            bundle.putString("index", String.valueOf(position));
            firebaseAnalytics.logEvent("CardClicked", bundle);
            NewsDetailsActivity.launch(view.getContext(), position);
        });

        // TODO(8) to Save News    (using firebase)

        holder.SaveCheckbox.setOnClickListener(v -> {
            if (holder.SaveCheckbox.isChecked()) {
                Snackbar.make(holder.cardView, context.getString(R.string.saqved), Snackbar.LENGTH_LONG).show();
                Operations.saveNews(getCurrentData(position));
            } else {
                Snackbar.make(holder.cardView, context.getString(R.string.delete), Snackbar.LENGTH_LONG).show();
                Operations.DeleteNews(getCurrentData(position));

            }
        });

        holder.widgetBtn.setOnClickListener(v -> {
            Prefrance.saveData(context
                    , getCurrentData(position).getDescription()
                    , getCurrentData(position).getTitle());
            Snackbar.make(holder.cardView, context.getString(R.string.addwidget), Snackbar.LENGTH_LONG).show();
            updateWidget(context);

        });

        holder.imgShare.setOnClickListener(v -> {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "Here is the share content body";
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
            context.startActivity(Intent.createChooser(sharingIntent, "Share via"));
        });
    }

    // TODO(9) this method to put the News in groups depended on the date

    private void dateCheck(final HomeNewsViewHolder holder, String date , int position){
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy");
        Date dateToday = new Date();

        String today = formatter.format(dateToday);

        try {
            dateToday = formatter.parse(today);
            Date newsDate = formatter.parse(DateUtils.FormatNewsapiDate(date));

            if (position ==0){
                if (dateToday.equals(newsDate)){
                    holder.linearLayout.setVisibility(View.VISIBLE);
                    holder.dayText.setText("Today");
                }else {
                    holder.linearLayout.setVisibility(View.VISIBLE);
                    holder.dayText.setText("Before");
                }
            }
            else if (dateToday.after(newsDate)){
                String previous = ((Article)news_Articles.get(position-1)).getPublishedAt();
                Date previousDate = formatter.parse(DateUtils.FormatNewsapiDate(previous));
                if (dateToday.equals(previousDate)) {
                    holder.linearLayout.setVisibility(View.VISIBLE);
                    holder.dayText.setText("Before");
                }else {holder.linearLayout.setVisibility(View.GONE);}
            }
            else {
                holder.linearLayout.setVisibility(View.GONE); }
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }

    // TODO(10) this to remove view if it's empty

    private void textCheck(final HomeNewsViewHolder holder, String image , String title , String date , String description){
        Glide.with(holder.Card_imagev.getContext()).load(image).centerCrop().into(holder.Card_imagev);
        holder.Card_titlev.setText(title);
        holder.Card_timev.setText(DateUtils.FormatNewsapiDate(date));
        holder.Card_contentv.setText(description);
        if (image == null){
            holder.Card_imagev.setVisibility(View.GONE);
        }
        if (title == null){
            holder.Card_titlev.setVisibility(View.GONE);
        }
        if (date == null){
            holder.Card_timev.setVisibility(View.GONE);
        }
        if (description == null){
            holder.Card_contentv.setVisibility(View.GONE);
        }


//        if (image.equals("")){
//            holder.Card_imagev.setVisibility(View.GONE);
//        }
//        else { Glide.with(holder.Card_imagev.getContext()).load(image).centerCrop().into(holder.Card_imagev); }
//
//        if (title.isEmpty()){
//            holder.Card_titlev.setVisibility(View.GONE);
//        }
//        else { holder.Card_titlev.setText(title); }
//
//        if (date.isEmpty()){
//            holder.Card_timev.setVisibility(View.GONE);
//        }
//        else {holder.Card_timev.setText(DateUtils.FormatNewsapiDate(date)); }
//
//        if (description.isEmpty()){
//            holder.Card_contentv.setVisibility(View.GONE);
//        }
//        else {holder.Card_contentv.setText(description); }


    }


    private CurrentData getCurrentData(int position) {
        if (isSaved) {
            SavedArticle savedArticle = (SavedArticle) news_Articles.get(position);
            currentData = new CurrentData(savedArticle.title
                    , savedArticle.description
                    , savedArticle.url
                    , savedArticle.imageUrl
                    , savedArticle.date);

        } else {
            Article newsarticle = (Article) news_Articles.get(position);
            currentData = new CurrentData(newsarticle.getTitle()
                    , newsarticle.getDescription()
                    , newsarticle.getUrl()
                    , newsarticle.getUrlToImage()
                    , newsarticle.getPublishedAt());
        }

        return currentData;
    }

    private void savedCheck(final HomeNewsViewHolder holder, int position) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query applesQuery = ref.child("article").orderByChild("url").equalTo(getCurrentData(position).getUrl());

        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                    holder.SaveCheckbox.setChecked(true);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("MAIN", "onCancelled", databaseError.toException());
            }
        });
    }

    public static void updateWidget(Context context) {

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, NewsWidget.class));
        NewsWidget.updateDataWidgets(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public int getItemCount() {
        return news_Articles.size();
    }

    // TODO(11) this view holder class


    public static class HomeNewsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.card)
        CardView cardView;
        @BindView(R.id.news_image)
        ImageView Card_imagev;
        @BindView(R.id.news_title)
        TextView Card_titlev;
        @BindView(R.id.news_time)
        TextView Card_timev;
        @BindView(R.id.news_content)
        TextView Card_contentv;
        @BindView(R.id.save_CheckBox)
        CheckBox SaveCheckbox;
        @BindView(R.id.wid_brn)
        TextView widgetBtn;
        @BindView(R.id.share)
        ImageView imgShare;
        @BindView(R.id.day)
        LinearLayout linearLayout;
        @BindView(R.id.daytext)
        TextView dayText;


        public HomeNewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
