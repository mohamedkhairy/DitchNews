package com.example.khairy.ditchnews.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import com.example.khairy.ditchnews.R;


import static com.example.khairy.ditchnews.Prefrance.PREFS_NAME;


public class NewsWidget extends AppWidgetProvider {

    private static SharedPreferences prefs;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String description = prefs.getString("description", "");
        String title = prefs.getString("title", "");

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.news_widget);

        views.setTextViewText(R.id.widgetTitle, title);
        views.setTextViewText(R.id.appwidget_text, description);

        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.linear_widgwt);
        appWidgetManager.updateAppWidget(appWidgetId, views);

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
//         There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);

        }
    }

    public static void updateDataWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

