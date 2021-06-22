package androidi.developer.headthapp.DeletedCode;

public class delete {
public void healthCart1(){
    //        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            if(biometricManager.canAuthenticate()==BiometricManager.BIOMETRIC_SUCCESS)
//            {
//                Toast.makeText(context,"verification could be done",Toast.LENGTH_SHORT).show();
//            }
//        }
    //adding a new way to run a service which will persists even when phone restarts
//        ServiceNoDelay mSensorService=new ServiceNoDelay(getApplicationContext());
//        Intent mServiceIntent=new Intent(getApplicationContext(),mSensorService.getClass());
//        if(!isMyServiceRunning(mSensorService.getClass()))
//            startService(mServiceIntent);
    //these lines of code will enable notifications
//        ComponentName receiver = new ComponentName(context, FromNotificationclass.class);
//        PackageManager pm = context.getPackageManager();
//
//        pm.setComponentEnabledSetting(receiver,
//                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
//                PackageManager.DONT_KILL_APP);

    //this few lines are for running a notification service
//        AlarmManager manager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
//        Intent alarmIntent;
//        alarmIntent = new Intent(HealthCart.this,FromNotificationclass.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
//                0, alarmIntent, 0);
//        int interval = 60000;
//        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
//                interval, pendingIntent);

}
//ProfileUpdate.java commented code
    //    public void dialogShower(String head,ArrayList<String> chose)
//    {
//        dialog2= new Dialog(ProfileUpdate.this, 0);
//        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog2.setCancelable(false);
//        dialog2.setContentView(R.layout.dialog_chooser);
//        ImageButton close_btn2=(ImageButton)dialog2.findViewById(R.id.close_but);
//        final TextView name=(TextView)dialog2.findViewById(R.id.option);
//        name.setText(head);
//        close_btn2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog2.dismiss();
//            }
//        });
//        RecyclerView options=(RecyclerView)dialog2.findViewById(R.id.options);
//        dashmainadapter adapter=new dashmainadapter(chose,context) {
//            @NonNull
//            @Override
//            public viewholder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                return super.onCreateViewHolder(parent, viewType);
//            }
//
//            @Override
//            public void onBindViewHolder(@NonNull viewholder1 holder, int position) {
//                super.onBindViewHolder(holder, position);
//                holder.name.setText(chose.get(position));
//                holder.layout.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        if(head.contains("Weight"))
//                        {
//                            weight.setText(chose.get(position).substring(0,3));
//                        }
//                        if(head.contains("Height"))
//                        {
//                            height.setText(chose.get(position).substring(0,3));
//                        }
//                        if(head.contains("Blood"))
//                        {
//                            blood.setText(chose.get(position));
//                        }
//                        dialog2.dismiss();
//                    }
//                });
//            }
//
//            @Override
//            public int getItemCount() {
//                return super.getItemCount();
//            }
//        };
//        options.setLayoutManager(new LinearLayoutManager(context));
//        options.setHasFixedSize(true);
//        options.setAdapter(adapter);
//        dialog2.show();
//    }

}
