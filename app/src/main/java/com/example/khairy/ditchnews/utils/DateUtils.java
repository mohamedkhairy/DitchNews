package com.example.khairy.ditchnews.utils;

import android.util.Log;

import com.google.firebase.crash.FirebaseCrash;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by khairy on 1/8/2018.
 */

// TODO(14) this class to reformat the date

public class DateUtils {
    public static String FormatNewsapiDate(String inputDate) {
        if (inputDate == null) {
            return null;
        }

        try {
            String inputDateformat = "yyyy-MM-dd'T'HH:mm:ss'Z'";
            String outputDateformat = "EEE, d MMM yyyy";

            SimpleDateFormat input = new SimpleDateFormat(inputDateformat);
            SimpleDateFormat output = new SimpleDateFormat(outputDateformat);

            Date date = input.parse(inputDate);
            return output.format(date);



        } catch (ParseException e) {

            e.printStackTrace();
            FirebaseCrash.report(e);

        }

        return inputDate;
    }
}
