package com.developer.headthapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.headthapp.ApiMethods.JsonParser;
import com.developer.headthapp.ApiMethods.networkData;
import com.developer.headthapp.Prescription.dashboard2;
import com.developer.headthapp.Prescription.presClass;
import com.developer.headthapp.Qr.QRone;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class Profile extends AppCompatActivity {
ImageButton back,prof,setting;
RecyclerView emergency;
ArrayList<emergencyClass> list;
emergencyAdapter adapter;
FirebaseAuth mauth;
Button edit_profile;
TextView name_main,name,phone,height,weight,age,dob,blood;
ProgressDialog dialog;
Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mauth=FirebaseAuth.getInstance();
        context=Profile.this;
       // formList();
        back=(ImageButton)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        prof=(ImageButton)findViewById(R.id.prof);
        prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Profile.this, QRone.class);
                startActivity(intent);
                finish();
            }
        });
        name=(TextView)findViewById(R.id.name);
        name_main=(TextView)findViewById(R.id.name_main);
        dob=(TextView)findViewById(R.id.dob);
        height=(TextView)findViewById(R.id.height);
        weight=(TextView)findViewById(R.id.weight);
        blood=(TextView)findViewById(R.id.blood);
        setting=(ImageButton)findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
         Intent intent=new Intent(Profile.this,Setting.class);
         startActivity(intent);
         overridePendingTransition(R.anim.in_from_bottom,R.anim.out_to_top);
            }
        });
        age=(TextView)findViewById(R.id.age);
        edit_profile=(Button)findViewById(R.id.edit_profile);
        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Profile.this,ProfileUpdate.class);
                String hh=height.getText().toString();
                hh=hh.substring(0,hh.length()-4);
                String ww=weight.getText().toString();
                ww=ww.substring(0,ww.length()-3);
                intent.putExtra("edit","true");
                intent.putExtra("name",name.getText().toString());
                intent.putExtra("dob",dob.getText().toString());
                intent.putExtra("height",hh);
                intent.putExtra("weight",ww);
                intent.putExtra("blood",blood.getText().toString());
                startActivity(intent);
                check=true;
            }
        });
        phone=(TextView)findViewById(R.id.phone);
        emergency=(RecyclerView)findViewById(R.id.emergency);
        new getProfile().execute();
    //    new getEmergency().execute();
    }
boolean check=false;
    @Override
    protected void onResume() {
        super.onResume();
        if(check) {
            new getProfile().execute();
        check=false;
        }
        if(mauth.getCurrentUser()==null)
        {
            finish();
        }
    }

    private class getProfile extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog=new ProgressDialog(Profile.this);
            dialog.setMessage("Please wait");
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
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
            dialog.dismiss();
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
                        String arr[]=nameF.split(" ");
                        String mobileF = obj.getString("mobile");
                        String heightF = obj.getString("height");
                        String weightF = obj.getString("weight");
                        String dobF = obj.getString("dob");

                        dobF=dobF.replace("/","-");
                        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                        Date date = formatter.parse(dobF);
                        //Converting obtained Date object to LocalDate object
                        Instant instant = null;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            instant = date.toInstant();
                            ZonedDateTime zone = instant.atZone(ZoneId.systemDefault());
                            LocalDate givenDate = zone.toLocalDate();
                            //Calculating the difference between given date to current date.
                            Period period = Period.between(givenDate, LocalDate.now());
                            age.setText(String.valueOf(period.getYears()));
                        }else{
                            age.setText("23");
                        }
                        String bloodF = obj.getString("blood");
                        if(arr.length>1)
                        {
                            nameF=arr[0].substring(0,1).toUpperCase()+arr[0].substring(1)+" "+arr[1];
                        }
                        else
                        {
                            nameF=arr[0].substring(0,1).toUpperCase()+arr[0].substring(1);
                        }
                        name_main.setText(nameF);
                        name.setText(nameF);
                        phone.setText("+91-"+mobileF);
                        height.setText(heightF+" cms");
                        weight.setText(weightF+" kg");
                        dob.setText(dobF);
                        blood.setText(bloodF);
                        new getEmergency().execute();
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
                catch (JSONException | ParseException e) {
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
    public static Date StringToDate(String dob) throws ParseException {
        //Instantiating the SimpleDateFormat class
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        //Parsing the given String to Date object
        Date date = formatter.parse(dob);
//        System.out.println("Date object value: "+date);
        return date;
    }
    public class getEmergency extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... strings) {
            String url=new networkData().url+new networkData().getemergency;
            String number=mauth.getCurrentUser().getPhoneNumber();
            number=number.substring(3,number.length());
            String uploadId= UUID.randomUUID().toString();
            String json=new JsonParser().viewOffer(url,number);
            return json;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(result!=null)
            {
                try{
                    list=new ArrayList<>();
                    JSONObject jsonObject = new JSONObject(result);
                    final String responce = String.valueOf(jsonObject.get("status"));
                    // final String responce2=String.valueOf(jsonObject.get("msg"));
                    if(responce.equals("1"))
                    {
                        //Toast.makeText(context,"Reching here",Toast.LENGTH_SHORT).show();
                        JSONArray data=jsonObject.getJSONArray("data");
                        for(int i=0;i<data.length();i++)
                        {
                            JSONObject obj=data.getJSONObject(i);
                            String rec_id = obj.getString("rec_id");
                            String name = obj.getString("name");
                            String phone = obj.getString("phone");
                            list.add(new emergencyClass(rec_id,name,phone));
                        }
                        adapter=new emergencyAdapter(list,context);
                        emergency.setHasFixedSize(true);
                        emergency.setLayoutManager(new LinearLayoutManager(context));
                        emergency.setAdapter(adapter);
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
}