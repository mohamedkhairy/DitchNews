package com.example.khairy.ditchnews;

import android.content.Context;
import android.content.SharedPreferences;

public class Prefrance {

    public static final String PREFS_NAME = "prefs";

    public static void saveData(Context context, String description, String title) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();

        prefs.remove("description");
        prefs.putString("description", description);

        prefs.remove("title");
        prefs.putString("title", title);

        prefs.apply();
    }
}
