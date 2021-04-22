package com.developer.headthapp.NotificationCode;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

import com.developer.headthapp.ApiMethods.JsonParser;
import com.developer.headthapp.ApiMethods.networkData;
import com.developer.headthapp.HealthCart;
import com.developer.headthapp.R;
import com.developer.headthapp.serviceOtp.MyService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


public class FromNotificationclass extends BroadcastReceiver
{
    Context context;
    NotificationManagerCompat notificationManager;
    int r=0;
    public void formality()
    {
        notificationManager= NotificationManagerCompat.from(context);
//        context.startService(new Intent(context.getApplicationContext(), MyService.class));
        new setIt().execute();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context=context;
        formality();
    }

    public class setIt extends AsyncTask<String, String, String>
    {

        @Override
        protected String doInBackground(String... strings) {
            String url=new networkData().url+new networkData().notification;
            JsonParser jsonParser=new JsonParser();
            SharedPreferences preferences=context.getApplicationContext().
                    getSharedPreferences("login_details",context.getApplicationContext().MODE_PRIVATE);

            String data=new JsonParser().viewOffer(url,"7389438159");;

            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        if(s!=null) {
            try {
                boolean counter=true;
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("obj");
                ArrayList name = new ArrayList<String>();
                // list = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object1 = jsonArray.getJSONObject(i);
                    String id=object1.getString("id");
                    String content=object1.getString("content");
                    String date=object1.getString("date");
                    String title=object1.getString("title");

                    System.out.println(id);
                    Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                    Intent action = new Intent(context, HealthCart.class);
                    //action.putExtra("appointment_id", appointment_id);
                    //action.putExtra("buttons", "allfine2");
                    //Context context;
                    PendingIntent pendingIntent = PendingIntent.getActivity(context,
                            0, action, 0);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        Notification notification = new NotificationCompat.Builder(context,
                                id)
                                .setSmallIcon(R.drawable.ic_notification)
                                .setContentTitle(title)
                                .addAction(R.drawable.dark_btn, "Order", pendingIntent)
                                .setContentInfo("Order Number:" + date)
                                .setContentText("Time:" + date)
                                .setAutoCancel(true)
                                .setPriority(NotificationCompat.PRIORITY_HIGH)
                                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                .build();
                         // notification.flags|=Notification.FLAG_AUTO_CANCEL;
                        NotificationChannel channel1 = new NotificationChannel(
                                id,
                                title,
                                NotificationManager.IMPORTANCE_HIGH
                        );
                        channel1.setDescription(date+"     Order Number"+content);
                        channel1.enableLights(true);
                        channel1.setShowBadge(true);
                        channel1.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
                        NotificationManager manager =context.getSystemService(NotificationManager.class);
                        manager.createNotificationChannel(channel1);
                        manager.notify(r,notification);
                        r++;
                        v.vibrate(VibrationEffect.createOneShot(5000,
                                VibrationEffect.DEFAULT_AMPLITUDE));
                    }
                    else {
                        Notification notification = new NotificationCompat.Builder(context,
                                "new notification")
                                .setSmallIcon(R.drawable.ic_download)
                                .setContentTitle(title)
                                .addAction(R.drawable.ic_close, "Order", pendingIntent)
                                .setContentInfo("Order Number:" + id)
                                .setContentText("Time:" + date)
                                .setAutoCancel(true)
                                .setPriority(NotificationCompat.PRIORITY_HIGH)
                                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                .build();
                        // a small code for making phone vibrate on this code when api is made
                        notificationManager.notify(r, notification);
                        r++;
                        v.vibrate(5000);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
            }
        }
    }

