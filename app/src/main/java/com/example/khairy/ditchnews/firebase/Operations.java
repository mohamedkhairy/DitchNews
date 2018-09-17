package com.example.khairy.ditchnews.firebase;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.khairy.ditchnews.CurrentData;
import com.example.khairy.ditchnews.NewsAdapter;
import com.example.khairy.ditchnews.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Operations {


    private static DatabaseReference databaseReference;


    public static void saveNews(CurrentData currentData) {

        databaseReference = FirebaseDatabase.getInstance().getReference("article");
        String articleID = databaseReference.push().getKey();
        SavedArticle savedArticle = new SavedArticle(articleID
                , currentData.getTitle()
                , currentData.getDescription()
                , currentData.getUrl()
                , currentData.getImageUrl()
                , currentData.getDate());
        databaseReference.child(articleID).setValue(savedArticle);
    }

    public static void DeleteNews(CurrentData currentData) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query applesQuery = ref.child("article").orderByChild("url").equalTo(currentData.getUrl());

        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                    appleSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("MAIN", "onCancelled", databaseError.toException());
            }
        });
    }

    public static void getSaved(final Activity activity) {
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm.getActiveNetworkInfo() != null) {
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference("article");


            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    final List<SavedArticle> SavedArticleList = new ArrayList<>();

                    for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                        SavedArticle savedArticle = appleSnapshot.getValue(SavedArticle.class);
                        SavedArticleList.add(savedArticle);
                    }

                    if (SavedArticleList.isEmpty()) {
                        ImageView imageView = activity.findViewById(R.id.noconnection);
                        imageView.setVisibility(View.VISIBLE);
                        imageView.setImageResource(R.drawable.nosave);
                    }
                    RecyclerView news_Recyclerview = activity.findViewById(R.id.main_recyclerView);
                    news_Recyclerview.setLayoutManager(new LinearLayoutManager(activity));
                    NewsAdapter newsAdapter = new NewsAdapter(SavedArticleList, true, activity);
                    news_Recyclerview.setAdapter(newsAdapter);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("MAIN", "onCancelled", databaseError.toException());

                }
            });
        } else {
            ImageView imageView = activity.findViewById(R.id.noconnection);
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageResource(R.drawable.nowifi);
        }

    }


}
