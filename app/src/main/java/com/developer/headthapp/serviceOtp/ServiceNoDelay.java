package com.developer.headthapp.serviceOtp;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class ServiceNoDelay extends Service {
    public int counter=0;
    Context context;
    public ServiceNoDelay(Context applicationContent) {
    super();
    context=applicationContent;
    System.out.println("Service"+" I am a service created over here");
    }
    public ServiceNoDelay(){

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent,flags,startId);
        startTimer();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("Service"+" I on Destroyed");

        Intent broadcastIntent = new Intent("ac.in.ActivityRecognition.RestartSensor");
        sendBroadcast(broadcastIntent);
        stoptimerTask();
    }

    private Timer timer;
    private TimerTask timerTask;
    public void startTimer(){
        timer=new Timer();
//        initialize the timer job
        intializeTimerTask();

//        initialize the timer with the duration of wakeup
        timer.schedule(timerTask,2000,10000);
    }
    public void intializeTimerTask(){
        timerTask=new TimerTask() {
            @Override
            public void run() {
//          Toast.makeText(context,"HI i am getting executed",Toast.LENGTH_SHORT).show();
                System.out.println("Service"+" Executing here");
            }
        };
    }
    public void stoptimerTask(){
//        stop the timer if its not already null
        if(timer!=null){
            timer.cancel();
            timer=null;
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

}