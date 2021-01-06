package com.developer.headthapp.NotificationCode;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

import com.developer.headthapp.ApiMethods.JsonParser;
import com.developer.headthapp.HealthCart;
import com.developer.headthapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.core.app.NotificationCompat;

public class NotificationMaker {

//    public class setIt extends AsyncTask<String, String, String>
//    {
//
//        @Override
//        protected String doInBackground(String... strings) {
//            String url="https://express.accountantlalaji.com/newapp/webapi/Orchidvendor/notification";
//            JsonParser jsonParser=new JsonParser();
//            String data=jsonParser.notifications(url,"23");
//            return data;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//            if(s!=null) {
//                try {
//                    boolean counter=true;
//                    JSONObject jsonObject = new JSONObject(s);
//                    JSONArray jsonArray = jsonObject.getJSONArray("data");
//                    ArrayList name = new ArrayList<String>();
//                    // list = new ArrayList<>();
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject object = jsonArray.getJSONObject(i);
//                        String cus_name = String.valueOf(object.get("customer_name"));
//                        String order_no = String.valueOf(object.get("order_no"));
//                        String book_date = String.valueOf(object.get("appointment_date"));
//                        String time = String.valueOf(object.get("start_time")) + " to " +
//                                String.valueOf(object.get("end_time"));
//                        String book_time = String.valueOf(object.get("day"));
//                        System.out.println(cus_name);
//                        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
//                        Intent action = new Intent(context, HealthCart.class);
//                        //action.putExtra("appointment_id", appointment_id);
//                        //action.putExtra("buttons", "allfine2");
//                        //Context context;
//                        PendingIntent pendingIntent = PendingIntent.getActivity(context,
//                                0, action, 0);
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                            Notification notification = new NotificationCompat.Builder(context,
//                                    order_no)
//                                    .setSmallIcon(R.drawable.ic_notification)
//                                    .setContentTitle(cus_name)
//                                    .addAction(R.drawable.dark_btn, "Order", pendingIntent)
//                                    .setContentInfo("Order Number:" + order_no)
//                                    .setContentText("Time:" + time)
//                                    .setAutoCancel(true)
//                                    .setPriority(NotificationCompat.PRIORITY_HIGH)
//                                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
//                                    .build();
//                            // notification.flags|=Notification.FLAG_AUTO_CANCEL;
//                            NotificationChannel channel1 = new NotificationChannel(
//                                    order_no,
//                                    cus_name,
//                                    NotificationManager.IMPORTANCE_HIGH
//                            );
//                            channel1.setDescription(time+"     Order Number"+order_no);
//                            channel1.enableLights(true);
//                            channel1.setShowBadge(true);
//                            channel1.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
//                            NotificationManager manager =context.getSystemService(NotificationManager.class);
//                            manager.createNotificationChannel(channel1);
//                            manager.notify(r,notification);
//                            r++;
//                            v.vibrate(VibrationEffect.createOneShot(1000,
//                                    VibrationEffect.DEFAULT_AMPLITUDE));
//                        }
//                        else {
//                            Notification notification = new NotificationCompat.Builder(context,
//                                    "new notification")
//                                    .setSmallIcon(R.drawable.ic_download)
//                                    .setContentTitle(cus_name)
//                                    .addAction(R.drawable.ic_close, "Order", pendingIntent)
//                                    .setContentInfo("Order Number:" + order_no)
//                                    .setContentText("Time:" + time)
//                                    .setAutoCancel(true)
//                                    .setPriority(NotificationCompat.PRIORITY_HIGH)
//                                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
//                                    .build();
//                            // a small code for making phone vibrate on this code when api is made
//                            notificationManager.notify(r, notification);
//                            r++;
//                            v.vibrate(1000);
//                        }
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }
//    }
}
