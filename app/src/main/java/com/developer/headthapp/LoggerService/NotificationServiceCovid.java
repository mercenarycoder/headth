package com.developer.headthapp.LoggerService;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.developer.headthapp.ApiMethods.JsonParser;
import com.developer.headthapp.ApiMethods.networkData;
import com.developer.headthapp.Covid.HelpClass;
import com.developer.headthapp.HealthCart;
import com.developer.headthapp.NotificationCode.NotificationReciever;
import com.developer.headthapp.R;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static com.developer.headthapp.NotificationCode.App.CHANNEL_ID;

public class NotificationServiceCovid extends JobService {
    public static final String TAG="NotificationService";
    private boolean jobCancelled=false;
    ArrayList<HelpClass> list;
    int r=0;
    Context context;
    NotificationManagerCompat notificationManager;
    FirebaseAuth mauth=FirebaseAuth.getInstance();
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d(TAG, "onStartJob: job is started");
        context=getApplicationContext();
        notificationManager=NotificationManagerCompat.from(context);
        doinBackground(jobParameters);
        return true;
    }

    private void doinBackground(JobParameters params) {

            new setIt().execute();
    }

    @Override

    public boolean onStopJob(JobParameters jobParameters) {
        Log.d(TAG, "onStopJob: Job is cancelled before completion");
        jobCancelled=true;
        new setIt().cancel(true);
        return true;
    }
    public class cancelling extends AsyncTask<String,String,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String url=new networkData().url+new networkData().changeBulkStatus;
            JsonParser jsonParser=new JsonParser();
            String number=mauth.getCurrentUser().getPhoneNumber();
            number=number.substring(3,number.length());
            String data=null;
            for(int i=0;i<list.size();i++)
            {
                data=new JsonParser().cancelPlasmaRequest(url,number,list.get(i).getCaller());
            }
            Log.d("cancelling going", "doInBackground: "+data);

            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s!=null){
                Toast.makeText(context,"Notification updated status",Toast.LENGTH_SHORT).show();
            }
        }
    }
    public class setIt extends AsyncTask<String, String, String>
    {

        @Override
        protected String doInBackground(String... strings) {
            String url=new networkData().url+new networkData().getBulks;
            JsonParser jsonParser=new JsonParser();
            String number=mauth.getCurrentUser().getPhoneNumber();
            number=number.substring(3,number.length());
            String data=new JsonParser().getBulkCalled(url,number);
            Log.d(TAG, "doInBackground: "+data);
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s!=null) {
                try {
                    boolean counter=true;
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    ArrayList name = new ArrayList<String>();
                     list = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object1 = jsonArray.getJSONObject(i);
                        String id=object1.getString("id");
                        String called=object1.getString("called");
                        String caller=object1.getString("caller");
                        String status=object1.getString("status");
                        list.add(new HelpClass(id,caller));
                        System.out.println(id);
                        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

                        //code for custom notification
                            RemoteViews collapseView = new RemoteViews(context.getPackageName(),R.layout.notification_collapsed );
                            RemoteViews expandedView = new RemoteViews(context.getPackageName(), R.layout.notification_expanded);

                            Intent clickIntent=new Intent(context, NotificationReciever.class);
                            PendingIntent clickPendingIntent = PendingIntent.getBroadcast(context, 0, clickIntent, 0);

                            collapseView.setTextViewText(R.id.content,"Plasma donation is requested please help by " +caller);
                            collapseView.setOnClickPendingIntent(R.id.call,clickPendingIntent);
                            collapseView.setOnClickPendingIntent(R.id.cancel,clickPendingIntent);
                            expandedView.setOnClickPendingIntent(R.id.call,clickPendingIntent);
                            expandedView.setOnClickPendingIntent(R.id.cancel,clickPendingIntent);
                            expandedView.setTextViewText(R.id.content,"Plasma donation is requested please help by " +caller);
//                            expandedView.setOnClickPendingIntent(R.id.call,clickPendingIntent);
                            Notification notification=new NotificationCompat.Builder(context,CHANNEL_ID)
                                    .setSmallIcon(R.drawable.logom)
                                    .setCustomContentView(collapseView)
                                    .setCustomBigContentView(expandedView)
                                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                                    .build();
                            v.vibrate(3000);
                            notificationManager.notify(r,notification);
                            r++;
                    }
                    new cancelling().execute();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
