package com.developer.headthapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class Profile extends AppCompatActivity {
ImageButton back;
RecyclerView emergency;
ArrayList<emergencyClass> list;
emergencyAdapter adapter;
FirebaseAuth mauth;
ProgressDialog dialog;
Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mauth=FirebaseAuth.getInstance();
        context=Profile.this;
        formList();
        back=(ImageButton)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        emergency=(RecyclerView)findViewById(R.id.emergency);
        adapter=new emergencyAdapter(list,context);
        emergency.setHasFixedSize(true);
        emergency.setLayoutManager(new LinearLayoutManager(context));
        emergency.setAdapter(adapter);
    }
    public void formList()
    {
        list=new ArrayList<>();
        list.add(new emergencyClass("","",""));
        list.add(new emergencyClass("","",""));
        list.add(new emergencyClass("","",""));
        list.add(new emergencyClass("","",""));
        list.add(new emergencyClass("","",""));
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
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}