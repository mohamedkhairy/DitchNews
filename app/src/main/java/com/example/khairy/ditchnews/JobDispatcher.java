package com.example.khairy.ditchnews;

import android.content.Intent;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

public class JobDispatcher extends JobService {

    @Override
    public boolean onStartJob(JobParameters job) {

        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(myIntent);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters job) {

        return false;
    }


}
