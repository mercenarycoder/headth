package com.developer.headthapp.LoggerService;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.app.job.JobWorkItem;
import android.util.Log;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.google.android.gms.vision.L.TAG;

public class newService  extends JobService {
    public static final String TAG="JobServiceEx";
    private boolean jobCancelled=false;
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d(TAG, "onStartJob: ");
        doInBackground(jobParameters);
        return true;
    }
    public void doInBackground(final JobParameters params){
        new Thread(new Runnable() {
            @Override
            public void run() {
                int i=0;
                while(true){
//                    Log.d(TAG, "run: "+i);
                    if(jobCancelled)
                        break;
                    i++;
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Log.d(TAG, "run: Job finished");
                jobFinished(params,false);
            }
        }).start();
    }
    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        //here if we a running a asynctask we have to cancel that task
        Log.d(TAG, "onStopJob: Job cancelled before completion");
        jobCancelled=true;
        return true;
    }
}
