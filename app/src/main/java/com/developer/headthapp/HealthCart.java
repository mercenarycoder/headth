package com.developer.headthapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.biometrics.BiometricManager;
import android.hardware.biometrics.BiometricPrompt;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.headthapp.ApiMethods.JsonParser;
import com.developer.headthapp.ApiMethods.networkData;
import com.developer.headthapp.FragmentMains.DiseaseFragment;
import com.developer.headthapp.FragmentMains.FragmentAllergies;
import com.developer.headthapp.FragmentMains.FragmentHistiry;
import com.developer.headthapp.FragmentMains.MedicineFragment;
import com.developer.headthapp.NotificationCode.FromNotificationclass;
import com.developer.headthapp.NotificationsDir.Notifications;
import com.developer.headthapp.Prescription.Prescriptions;
import com.developer.headthapp.Prescription.dashboard2;
import com.developer.headthapp.Prescription.presClass;
import com.developer.headthapp.Qr.QRone;
import com.developer.headthapp.Report.ReportActivity;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.UUID;

public class HealthCart extends AppCompatActivity {
RecyclerView recycler_report,recycler_pres;
dashmainadapter adapter;
Button view_pres,view_report,help;
dashboard2 adapter2;
ArrayList<reportClass> list;
ArrayList<presClass>  list2;
ArrayList<typeClass> dis,med,all,his;
Context context;
Button open_profile;
FirebaseAuth mauth;
ProgressDialog progressDialog;
static RelativeLayout layout_changer;

ImageButton open_notification,qr_act;
LinearLayout disease,medicine,allergies,history,help_layout,disease_help,medicine_hep,allergies_help,history_help,qr_help;
LinearLayout d_help,m_help,a_help,h_help,noti_help,profile_help,report_help2,pres_help2;
Button next_d,next_m,next_a,next_h,next_qr,next_n,next_p,next_r,next_pres,report_help,pres_help,profile_2;
RelativeLayout help_layout2;
ProgressBar progress,progress2;
TextView no_report,no_pres;
ImageButton disease_2,medicine_2,allergies_2,history_2,qr,noti;
SwipeRefreshLayout refresh,refresh2;
public static Boolean check=false;
    public BiometricManager biometricManager;
public static void changeVisiblity()
{
    layout_changer.setVisibility(View.INVISIBLE);
}


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=HealthCart.this;

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            if(biometricManager.canAuthenticate()==BiometricManager.BIOMETRIC_SUCCESS)
//            {
//                Toast.makeText(context,"verification could be done",Toast.LENGTH_SHORT).show();
//            }
//        }
        mauth=FirebaseAuth.getInstance();
        setContentView(R.layout.activity_health_cart);
        new GlobalVariables("fdfkd");
        formList();
       // formList2();
        initiaLize();
        //these lines of code will enable notifications
        ComponentName receiver = new ComponentName(context, FromNotificationclass.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
        //this few lines are for running a notification service
//        AlarmManager manager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
//        Intent alarmIntent;
//        alarmIntent = new Intent(HealthCart.this,FromNotificationclass.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
//                0, alarmIntent, 0);
//        int interval = 60000;
//        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
//                interval, pendingIntent);

        //till here
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           help_layout2.setVisibility(View.VISIBLE);
           qr.setVisibility(View.VISIBLE);
           qr_help.setVisibility(View.VISIBLE);
           //disease_2,medicine_2,allergies_2,history_2,qr,noti
                disease_2.setEnabled(false);
                medicine_2.setEnabled(false);
                allergies_2.setEnabled(false);
                history_2.setEnabled(false);
                qr.setEnabled(false);
                noti.setEnabled(false);
                medicine.setEnabled(false);
                disease.setEnabled(false);
                allergies.setEnabled(false);
                history.setEnabled(false);
                view_pres.setEnabled(false);
                view_report.setEnabled(false);
                help.setEnabled(false);
                open_profile.setEnabled(false);
                open_notification.setEnabled(false);
                new GlobalVariables();
                GlobalVariables.Helper =false;
            }
        });
        next_qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qr.setVisibility(View.INVISIBLE);
                qr_help.setVisibility(View.INVISIBLE);
                noti.setVisibility(View.VISIBLE);
                noti_help.setVisibility(View.VISIBLE);
            }
        });
        next_n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noti.setVisibility(View.INVISIBLE);
                noti_help.setVisibility(View.INVISIBLE);
                profile_2.setVisibility(View.VISIBLE);
                profile_help.setVisibility(View.VISIBLE);
            }
        });
        next_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profile_2.setVisibility(View.INVISIBLE);
                profile_help.setVisibility(View.INVISIBLE);
                help_layout2.setVisibility(View.INVISIBLE);
                help_layout.setVisibility(View.VISIBLE);
                disease_help.setVisibility(View.VISIBLE);
                d_help.setVisibility(View.VISIBLE);
            }
        });
        next_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                medicine_hep.setVisibility(View.VISIBLE);
                disease_help.setVisibility(View.INVISIBLE);
                m_help.setVisibility(View.VISIBLE);
                d_help.setVisibility(View.INVISIBLE);
            }
        });
        next_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                medicine_hep.setVisibility(View.INVISIBLE);
                allergies_help.setVisibility(View.VISIBLE);
                m_help.setVisibility(View.INVISIBLE);
                a_help.setVisibility(View.VISIBLE);
            }
        });
        next_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allergies_help.setVisibility(View.INVISIBLE);
                history_help.setVisibility(View.VISIBLE);
                a_help.setVisibility(View.INVISIBLE);
                h_help.setVisibility(View.VISIBLE);
            }
        });
        next_h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                history_help.setVisibility(View.INVISIBLE);
                h_help.setVisibility(View.INVISIBLE);
                help_layout.setVisibility(View.INVISIBLE);
                help_layout2.setVisibility(View.VISIBLE);
                report_help.setVisibility(View.VISIBLE);
                report_help2.setVisibility(View.VISIBLE);
            }
        });
        next_r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                report_help.setVisibility(View.INVISIBLE);
                report_help2.setVisibility(View.INVISIBLE);
               pres_help.setVisibility(View.VISIBLE);
               pres_help2.setVisibility(View.VISIBLE);
            }
        });
        next_pres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pres_help.setVisibility(View.INVISIBLE);
                pres_help2.setVisibility(View.INVISIBLE);
                help_layout2.setVisibility(View.INVISIBLE);
                disease_2.setEnabled(true);
                medicine_2.setEnabled(true);
                allergies_2.setEnabled(true);
                history_2.setEnabled(true);
                qr.setEnabled(true);
                noti.setEnabled(true);
                medicine.setEnabled(true);
                disease.setEnabled(true);
                allergies.setEnabled(true);
                history.setEnabled(true);
                view_pres.setEnabled(true);
                view_report.setEnabled(true);
                help.setEnabled(true);
                open_notification.setEnabled(true);
                open_profile.setEnabled(true);
                new GlobalVariables();
                GlobalVariables.Helper=true;
            }
        });
        context=HealthCart.this;


        qr_act.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HealthCart.this, QRone.class);
                startActivity(intent);
            }
        });
        disease_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.layout_changer,new DiseaseFragment()).commit();
                layout_changer.setVisibility(View.VISIBLE);
            }
        });
        disease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
          getSupportFragmentManager().beginTransaction().replace(R.id.layout_changer,new DiseaseFragment()).commit();
          layout_changer.setVisibility(View.VISIBLE);
            }
        });
        medicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.layout_changer,new MedicineFragment()).commit();
                layout_changer.setVisibility(View.VISIBLE);
            }
        });
        medicine_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.layout_changer,new MedicineFragment()).commit();
                layout_changer.setVisibility(View.VISIBLE);
            }
        });
        allergies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.layout_changer,new FragmentAllergies()).commit();
                layout_changer.setVisibility(View.VISIBLE);
            }
        });
        allergies_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.layout_changer,new FragmentAllergies()).commit();
                layout_changer.setVisibility(View.VISIBLE);
            }
        });
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.layout_changer,new FragmentHistiry()).commit();
                layout_changer.setVisibility(View.VISIBLE);
            }
        });
        history_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.layout_changer,new FragmentHistiry()).commit();
                layout_changer.setVisibility(View.VISIBLE);
            }
        });
        //
        recycler_pres=(RecyclerView)findViewById(R.id.recycler_pres);

        open_profile=(Button)findViewById(R.id.open_profile);
        open_notification=(ImageButton)findViewById(R.id.open_notification);

        open_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,Profile.class);
                startActivity(intent);
            }
        });
        open_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, Notifications.class);
                startActivity(intent);
            }
        });
        view_pres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check=true;
                Intent intent=new Intent(context, Prescriptions.class);
                startActivity(intent);
            }
        });
        view_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check=true;
                Intent intent=new Intent(context, ReportActivity.class);
                startActivity(intent);
            }
        });
        refresh=(SwipeRefreshLayout)findViewById(R.id.refresh);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh.setRefreshing(false);
                new getReports().execute();
            }
        });
        refresh2=(SwipeRefreshLayout)findViewById(R.id.refresh2);
        refresh2.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh2.setRefreshing(false);
                new getallPres().execute();
            }
        });
        new getallPres().execute();
        new getProfile().execute();
        new getReports().execute();
    }
    
    public void initiaLize()
    {
        help=(Button)findViewById(R.id.help);
        view_pres=(Button)findViewById(R.id.view_pres);
        view_report=(Button)findViewById(R.id.view_report);
        recycler_report=(RecyclerView)findViewById(R.id.recycler_report);
        layout_changer=(RelativeLayout) findViewById(R.id.layout_changer);
        //main buttons
        disease=(LinearLayout)findViewById(R.id.disease);
        medicine=(LinearLayout)findViewById(R.id.medicine);
        allergies=(LinearLayout)findViewById(R.id.allergies);
        history=(LinearLayout)findViewById(R.id.history);
        disease_2=(ImageButton)findViewById(R.id.disease_2);
        medicine_2=(ImageButton)findViewById(R.id.medicine_2);
        allergies_2=(ImageButton)findViewById(R.id.allergies_2);
        history_2=(ImageButton)findViewById(R.id.history_2);
        qr_act=(ImageButton)findViewById(R.id.qr_act);
        //helper layouts from here
        help_layout=(LinearLayout)findViewById(R.id.help_layout);
        disease_help=(LinearLayout)findViewById(R.id.disease_help);
        medicine_hep=(LinearLayout)findViewById(R.id.medicine_help);
        allergies_help=(LinearLayout)findViewById(R.id.allergies_help);
        history_help=(LinearLayout)findViewById(R.id.history_help);
        next_d=(Button)findViewById(R.id.next_d);
        next_h=(Button)findViewById(R.id.next_h);
        next_a=(Button)findViewById(R.id.next_a);
        next_m=(Button)findViewById(R.id.next_m);
        d_help=(LinearLayout)findViewById(R.id.d_help);
        a_help=(LinearLayout)findViewById(R.id.a_help);
        h_help=(LinearLayout)findViewById(R.id.h_help);
        m_help=(LinearLayout)findViewById(R.id.m_help);
        help_layout2=(RelativeLayout)findViewById(R.id.help_layout2);
        qr=(ImageButton)findViewById(R.id.qr);
        noti=(ImageButton)findViewById(R.id.noti);
        profile_2=(Button)findViewById(R.id.profile_2);
        next_qr=(Button)findViewById(R.id.next_qr);
        next_n=(Button)findViewById(R.id.next_n);
        next_p=(Button)findViewById(R.id.next_p);
        next_r=(Button)findViewById(R.id.next_r);
        next_pres=(Button)findViewById(R.id.next_pres);
        qr_help=(LinearLayout)findViewById(R.id.qr_help);
        noti_help=(LinearLayout)findViewById(R.id.noti_help);
        profile_help=(LinearLayout)findViewById(R.id.profile_help);
        report_help=(Button)findViewById(R.id.report_help);
        report_help2=(LinearLayout)findViewById(R.id.report_help2);
        pres_help=(Button)findViewById(R.id.pres_help);
        pres_help2=(LinearLayout)findViewById(R.id.pres_help2);
        progress=(ProgressBar)findViewById(R.id.progress);
        progress2=(ProgressBar)findViewById(R.id.progress2);
        no_report=(TextView)findViewById(R.id.no_report);
        no_pres=(TextView)findViewById(R.id.no_pres);
    }
    public void formList()
    {

    }

    @Override
    protected void onResume() {
        super.onResume();
    if(mauth.getCurrentUser()==null)
    {
        finish();
    }
    if(check)
    {
        new getReports().execute();
        new getallPres().execute();
        new getProfile().execute();
        check=false;
    }
    }
    private class getProfile extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... strings) {
            String url=new networkData().url+new networkData().getprofile;
            String number=mauth.getCurrentUser().getPhoneNumber();
            number=number.substring(3,number.length());
            String uploadId= UUID.randomUUID().toString();
            String json=new JsonParser().viewOffer(url,number);
            return json;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
//            dialog.dismiss();
            if(result!=null)
            {
                try{
                    JSONObject jsonObject = new JSONObject(result);
                    final String responce = String.valueOf(jsonObject.get("status"));
                    // final String responce2=String.valueOf(jsonObject.get("msg"));
                    if(responce.equals("1"))
                    {
                        JSONArray data=jsonObject.getJSONArray("data");
                        JSONObject obj=data.getJSONObject(0);
                        String nameF=obj.getString("name");
                        String mobileF = obj.getString("mobile");
                        String heightF = obj.getString("height");
                        String weightF = obj.getString("weight");
                        String dobF = obj.getString("dob");
                        String bloodF = obj.getString("blood");
                        open_profile.setText(nameF.substring(0,1).toUpperCase());
                    }

                }
                catch (JSONException e) {
                    e.printStackTrace();
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        final String responce2=String.valueOf(jsonObject.get("msg"));
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Update")
                                .setMessage(responce2)
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                        builder.create();
                        builder.show();
                    }
                    catch (Exception e2)
                    {

                    }
                }
            }
            else
            {
                Toast.makeText(context,"Some Networking error try again later",Toast.LENGTH_SHORT).show();
            }
        }
    }
    public class getReports extends AsyncTask<String,String,String>
{
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
      progress.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(String... strings) {
        new networkData();
        String base= networkData.url;
        String method=networkData.gettopreport;
        String url=base+method;
        String number=mauth.getCurrentUser().getPhoneNumber();
        number=number.substring(3,number.length());
        String uploadId= UUID.randomUUID().toString();

        String json=new JsonParser().viewOffer(url,number);
        return json;
    }
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
         //progressDialog.dismiss();
        progress.setVisibility(View.INVISIBLE);
        if(null!=result)
        {
            try
            {
                list=new ArrayList<>();
                JSONObject jsonObject = new JSONObject(result);
                final String responce = String.valueOf(jsonObject.get("status"));
                // final String responce2=String.valueOf(jsonObject.get("msg"));
                if(responce.equals("1"))
                {
                    JSONArray data=jsonObject.getJSONArray("data");
                    if(data.length()>0) {
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject object = data.getJSONObject(i);
                            String title = object.getString("title");
                            String doctor = object.getString("observer");
                            String observation = object.getString("details");
                            String date[] = object.getString("date").split("T");
                            String id = object.getString("id");
                            String type = object.getString("type");
                            String image = object.getString("link");
                            String category=object.getString("category");
                            list.add(new reportClass(doctor, title, date[0], id, image, type,category,observation));
                        }
                        adapter = new dashmainadapter(list, context);
                        recycler_report.setHasFixedSize(true);
                        recycler_report.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                        recycler_report.setAdapter(adapter);
                        recycler_report.setVisibility(View.VISIBLE);
                        no_report.setVisibility(View.INVISIBLE);
                    }
                else{
                    no_report.setVisibility(View.VISIBLE);
                    recycler_report.setVisibility(View.INVISIBLE);
                    }
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Update")
                            .setMessage("Some Error in fetching info please swipe to refresh")
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                    builder.create();
                    builder.show();
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    final String responce2=String.valueOf(jsonObject.get("msg"));
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Update")
                            .setMessage(responce2)
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                    builder.create();
                    builder.show();
                }
                catch (Exception r)
                {

                }
            }
        }
        //Toast.makeText(signup_Activity.this, "something missing", Toast.LENGTH_SHORT).show();
        else
        {
            Toast.makeText(context,"please check details and try again",Toast.LENGTH_SHORT).show();
        }

    }
}
    public class getallPres extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress2.setVisibility(View.VISIBLE);
//            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            new networkData();
            String base= networkData.url;
            String method=networkData.gettoppres;
            String url=base+method;
            String number=mauth.getCurrentUser().getPhoneNumber();
            number=number.substring(3,number.length());
            String uploadId= UUID.randomUUID().toString();

            String json=new JsonParser().viewOffer(url,number);
            return json;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
           // progressDialog.dismiss();
           progress2.setVisibility(View.INVISIBLE);
            if(null!=result)
            {
                try
                {
                    list2=new ArrayList<>();
                    JSONObject jsonObject = new JSONObject(result);
                    final String responce = String.valueOf(jsonObject.get("status"));
                    // final String responce2=String.valueOf(jsonObject.get("msg"));
                    if(responce.equals("1"))
                    {
                        JSONArray data=jsonObject.getJSONArray("data");
                        if(data.length()>0) {
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject object = data.getJSONObject(i);
                                String title = object.getString("title");
                                String doctor = object.getString("doctor");
                                String observation = object.getString("observation");
                                String date[] = object.getString("date").split("T");
                                String id = object.getString("id");
                                String image = object.getString("image");
                                list2.add(new presClass(title, date[0], doctor, image, id, observation));
                            }
                            adapter2 = new dashboard2(list2, context);
                            recycler_pres.setHasFixedSize(true);
                            recycler_pres.setLayoutManager(new LinearLayoutManager(context));
                            recycler_pres.setAdapter(adapter2);
                            recycler_pres.setVisibility(View.VISIBLE);
                            no_pres.setVisibility(View.INVISIBLE);
                        }
                        else
                        {
                            recycler_pres.setVisibility(View.INVISIBLE);
                            no_pres.setVisibility(View.VISIBLE);
                        }
                    }
                    else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Update")
                                .setMessage("Some Error in fetching info please swipe to refresh")
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                        builder.create();
                        builder.show();
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        final String responce2=String.valueOf(jsonObject.get("msg"));
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Update")
                                .setMessage(responce2)
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                        builder.create();
                        builder.show();
                    }
                    catch (Exception r)
                    {

                    }
                }
            }
            //Toast.makeText(signup_Activity.this, "something missing", Toast.LENGTH_SHORT).show();
            else
            {
                Toast.makeText(context,"please check details and try again",Toast.LENGTH_SHORT).show();
            }

        }
    }
}