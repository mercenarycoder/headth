package com.developer.headthapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.developer.headthapp.ApiMethods.JsonParser;
import com.developer.headthapp.ApiMethods.networkData;
import com.google.android.gms.vision.text.Line;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

public class Setting extends AppCompatActivity {
LinearLayout logout,privacy,doctor,notification;
ImageButton back;
Context context=Setting.this;
SharedPreferences preferences;
SharedPreferences.Editor editor;
FirebaseAuth mauth=FirebaseAuth.getInstance();
ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("Logging off");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        preferences=getSharedPreferences("basicinfo",Context.MODE_PRIVATE);
        editor=preferences.edit();
        back=(ImageButton)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        logout=(LinearLayout)findViewById(R.id.logout);
        privacy=(LinearLayout)findViewById(R.id.privacy);
        doctor=(LinearLayout)findViewById(R.id.upgrade);
        notification=(LinearLayout)findViewById(R.id.notification);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Update")
                        .setMessage("Do you really want to Logout ?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                new deleteOtp().execute();
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                builder.create();
                builder.show();
            }
        });
        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"Privacy policies here",Toast.LENGTH_SHORT).show();
            }
        });

    }
    private class deleteOtp extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String base= networkData.url;
            String method=networkData.deleteOtp;
            String url=base+method;
            String number=mauth.getCurrentUser().getPhoneNumber();
            number=number.substring(3,number.length());
            String uploadId= UUID.randomUUID().toString();
            String json=new JsonParser().deleteOtp(url,number);
            return json;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            if(s!=null)
            {
             try{
                 JSONObject obj=new JSONObject(s);
                 String status=String.valueOf(obj.get("status"));
                 if(status.equals("1"))
                 {
                     Intent intent=new Intent(Setting.this,LoginUser.class);
                     mauth.signOut();
                     editor.clear();
                     editor.apply();
                     editor.commit();
                     startActivity(intent);
                     finish();
                 }
                 else
                 {
                    Toast.makeText(context,"Failed to logout please try again",Toast.LENGTH_SHORT).show();
                 }
             } catch (JSONException e) {
                 e.printStackTrace();
             }
            }
        }
    }
}