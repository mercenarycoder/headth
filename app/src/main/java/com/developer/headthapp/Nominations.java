package com.developer.headthapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.developer.headthapp.ApiMethods.JsonParser;
import com.developer.headthapp.ApiMethods.networkData;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

public class Nominations extends AppCompatActivity {
Button verify;
EditText name1,name2,name3,name4,name5;
EditText phone1,phone2,phone3,phone4,phone5;
String name[],nameF;
String phone[],phoneF;
ProgressDialog dialog;
FirebaseAuth mauth;
Context context;
boolean hare=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=Nominations.this;
        name=new String[5];
        phone=new String[5];
        mauth=FirebaseAuth.getInstance();
        setContentView(R.layout.activity_nominations);
        name1=(EditText)findViewById(R.id.name1);
        name2=(EditText)findViewById(R.id.name21);
        name3=(EditText)findViewById(R.id.name3);
        name4=(EditText)findViewById(R.id.name4);
        name5=(EditText)findViewById(R.id.name5);
        phone1=(EditText)findViewById(R.id.number1);
        phone2=(EditText)findViewById(R.id.number21);
        phone3=(EditText)findViewById(R.id.number3);
        phone4=(EditText)findViewById(R.id.number4);
        phone5=(EditText)findViewById(R.id.number5);

        verify=(Button)findViewById(R.id.verify);
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
         name[0]=name1.getText().toString();
         name[1]=name2.getText().toString();
         name[2]=name3.getText().toString();
         name[3]=name4.getText().toString();
         name[4]=name5.getText().toString();
         phone[0]=phone1.getText().toString();
         phone[1]=phone2.getText().toString();
         phone[2]=phone3.getText().toString();
         phone[3]=phone4.getText().toString();
         phone[4]=phone5.getText().toString();
         nameF="";
         phoneF="";
         for(int i=0;i<5;i++)
         {
             if(checkName(name[i])&&checkPhone(phone[i]))
             {
                 hare=true;
                 phoneF+=phone[i]+",";
                 nameF+=name[i]+",";
             }
         }
         if(nameF.endsWith(","))
         {
             nameF=nameF.substring(0,nameF.length()-1);
         }
         if(phoneF.endsWith(","))
         {
             phoneF=phoneF.substring(0,phoneF.length()-1);
         }
         if(hare) {
             Toast.makeText(context,nameF,Toast.LENGTH_SHORT).show();
             Toast.makeText(context,phoneF,Toast.LENGTH_SHORT).show();
             new submitEmergency().execute();
             hare=false;
         }
         else
         {
             Toast.makeText(context,"Please fill atleast one emergency contact",Toast.LENGTH_SHORT).show();
         }
            }
        });
    }
    public boolean checkName(String str)
    {
        if(str.length()>1)
        {
            return true;
        }
        return false;
    }
    public boolean checkPhone(String s1)
    {
        if(s1.length()==10)
        {
            return true;
        }
        return false;
    }
    public class submitEmergency extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog=new ProgressDialog(context);
            dialog.setMessage("Please wait");
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }
        @Override
        protected String doInBackground(String... strings) {
            String baseUrl=new networkData().url;
            String emergency=new networkData().emergency;
            String url=baseUrl+emergency;
            String number=mauth.getCurrentUser().getPhoneNumber();
            number=number.substring(3,number.length());
            String json=new  JsonParser().setEmergency(url,number,nameF,phoneF);
            return json;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dialog.dismiss();
            if(result!=null)
            {
                try
                {
                    JSONObject jsonObject = new JSONObject(result);
                    final String responce = String.valueOf(jsonObject.get("status"));
                    final String responce2=String.valueOf(jsonObject.get("msg"));
                    if(responce.equals("1"))
                    {
                        Intent intent=new Intent(Nominations.this,HealthCart.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
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
            else
            {
                Toast.makeText(context,"Please Check the details and try again",Toast.LENGTH_SHORT).show();
            }
        }
    }
}