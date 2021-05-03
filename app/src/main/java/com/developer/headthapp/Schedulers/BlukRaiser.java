package com.developer.headthapp.Schedulers;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.AsyncTask;

import com.google.firebase.auth.FirebaseAuth;

public class BlukRaiser  extends JobService {
    public static final String TAG="BulkRaiser";
    private boolean jobCancelled=false;
    private String callers="";
    FirebaseAuth mauth=FirebaseAuth.getInstance();
    private int status=0;
    @Override
    public boolean onStartJob(JobParameters jobParameters) {

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
    private class raiseBulk extends AsyncTask<String,String,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(String... strings) {

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}
