package com.developer.headthapp.Covid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.developer.headthapp.R;

public class Volunteer extends AppCompatActivity {
ImageButton back;
Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer);
        context=Volunteer.this;
        initialize();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    public void initialize()
    {
        back=(ImageButton)findViewById(R.id.back);
    }
}