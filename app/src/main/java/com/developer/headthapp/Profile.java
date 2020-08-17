package com.developer.headthapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;

public class Profile extends AppCompatActivity {
ImageButton back;
RecyclerView emergency;
ArrayList<emergencyClass> list;
emergencyAdapter adapter;
Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
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
}