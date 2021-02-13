package com.developer.headthapp.Qr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.developer.headthapp.AdapterEmergency;
import com.developer.headthapp.ApiMethods.JsonParser;
import com.developer.headthapp.ApiMethods.networkData;
import com.developer.headthapp.R;
import com.developer.headthapp.emergencyClass;
import com.google.android.gms.location.FusedLocationProviderClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

public class QRprofile extends AppCompatActivity {
Context context;
TextView blood,age;
RecyclerView diesease_recycler,medicine_recycler,allergy_recycler;
Button ambulance,police,family;
String mobile,access;
ArrayList<qrBase> list_d,list_a,list_m;
ArrayList<emergencyClass>        list_e;
QrAdapter adapter_d,adapter_m,adapter_a;
ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=QRprofile.this;
        setContentView(R.layout.activity_q_rprofile);
        Intent intent=getIntent();
        mobile=intent.getStringExtra("mobile");
        access=intent.getStringExtra("access");
        ambulance=(Button)findViewById(R.id.ambulance);
        police=(Button)findViewById(R.id.police);
        family=(Button)findViewById(R.id.family);
        blood=(TextView)findViewById(R.id.blood);
        age=(TextView)findViewById(R.id.age);
        diesease_recycler=(RecyclerView)findViewById(R.id.diesease_recycler);
        medicine_recycler=(RecyclerView)findViewById(R.id.medicine_recycler);
        allergy_recycler=(RecyclerView)findViewById(R.id.allergy_recycler);
        family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog=new Dialog(context, 0);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.dialog_emergency);
                ImageButton close_btn2=(ImageButton)dialog.findViewById(R.id.close_btn2);
                close_btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                RecyclerView recycler=(RecyclerView)dialog.findViewById(R.id.recycler);
                recycler.setLayoutManager(new LinearLayoutManager(context));
                recycler.setHasFixedSize(true);
                AdapterEmergency adapter=new AdapterEmergency(list_e,context);
                recycler.setAdapter(adapter);
                dialog.show();
            }
        });
        new getEmergencyProfile1().execute();
    }
    public class  getEmergencyProfile1 extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(context);
            progressDialog.setMessage("Sending information");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            new networkData();
            String base= networkData.url;
            String method=networkData.getQR;
            String url=base+method;
            String uploadId= UUID.randomUUID().toString();

            String json=new JsonParser().getCompleteHealth(url,mobile,access);
            return json;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            if(s!=null)
            {
                try{
                    JSONObject object = new JSONObject(s);
                    String status = object.getString("status");
                    if(status.equals("2")) {
                        JSONArray medicines = object.getJSONArray("medicines");
                        JSONArray allergy = object.getJSONArray("allergy");
                        JSONArray dieseas = object.getJSONArray("dieseas");
                        JSONObject object1 = object.getJSONObject("profile");
                        String blood2=object1.getString("blood");
                        String dob=object1.getString("dob");
                        String arr[]=dob.split("/");
                        String age2=getAge(Integer.parseInt("20"+arr[2]),Integer.parseInt(arr[0]),Integer.parseInt(arr[1]));
                        age.setText(age2);
                        blood.setText(blood2);
                        list_m=new ArrayList<>();
                        for(int i=0;i<medicines.length();i++)
                        {
                            JSONObject object2 = medicines.getJSONObject(i);
                            String name=object2.getString("name");
                            String id=object2.getString("id");
                            String details = object2.getString("purpose");
                            list_m.add(new qrBase(name,details,id));
                        }
                        list_a=new ArrayList<>();
                        for(int i=0;i<allergy.length();i++)
                        {
                            JSONObject object2 = allergy.getJSONObject(i);
                            String name=object2.getString("allergy");
                            String id=object2.getString("id");
                            String details = object2.getString("triggers");
                            list_a.add(new qrBase(name,details,id));
                        }
                        list_d=new ArrayList<>();
                        for(int i=0;i<dieseas.length();i++)
                        {
                            JSONObject object2 = dieseas.getJSONObject(i);
                            String name=object2.getString("name");
                            String id=object2.getString("id");
                            String details = object2.getString("details");
                            list_d.add(new qrBase(name,details,id));
                        }
                        adapter_m=new QrAdapter(list_m,context);
                        adapter_a=new QrAdapter(list_a,context);
                        adapter_d=new QrAdapter(list_d,context);
                        diesease_recycler.setLayoutManager(new LinearLayoutManager(context));
                        diesease_recycler.setHasFixedSize(true);
                        diesease_recycler.setAdapter(adapter_d);
                        medicine_recycler.setLayoutManager(new LinearLayoutManager(context));
                        medicine_recycler.setHasFixedSize(true);
                        medicine_recycler.setAdapter(adapter_m);
                        allergy_recycler.setLayoutManager(new LinearLayoutManager(context));
                        allergy_recycler.setHasFixedSize(true);
                        allergy_recycler.setAdapter(adapter_a);
                        JSONArray emergency=object.getJSONArray("emergency");
                        list_e=new ArrayList<>();
                        for(int i=0;i<emergency.length();i++)
                        {
                            JSONObject object2 = emergency.getJSONObject(i);
                            String phone = object2.getString("phone");
                            String name=object2.getString("name");
                            String id=object2.getString("rec_id");
                            list_e.add(new emergencyClass(id,name,phone));
                        }

                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
    private String getAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }
}