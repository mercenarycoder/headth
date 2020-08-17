package com.developer.headthapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Nominations extends AppCompatActivity {
Button verify;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nominations);
        verify=(Button)findViewById(R.id.verify);
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
          Intent intent=new Intent(Nominations.this,HealthCart.class);
          startActivity(intent);
          finish();
            }
        });
    }
}