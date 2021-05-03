package com.developer.headthapp.NotificationCode;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationManagerCompat;

public class NotificationReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"I am clicked",Toast.LENGTH_SHORT).show();

        NotificationManagerCompat notificationManager= NotificationManagerCompat.from(context);
        notificationManager.cancel(0);
    }
}
