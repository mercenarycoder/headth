package com.developer.headthapp.serviceOtp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class SensorRestarterBroadcastReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        System.out.println("BroadcastReciever"+" Service stops oops");
        context.startService(new Intent(context,ServiceNoDelay.class));
    }
}