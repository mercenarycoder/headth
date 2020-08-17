package com.developer.headthapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class LoginUser extends AppCompatActivity {
ImageButton start;
Button verify;
LinearLayout main_layout,otp_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);
        start=(ImageButton)findViewById(R.id.start);
        main_layout=(LinearLayout)findViewById(R.id.main_layout);
        otp_layout=(LinearLayout)findViewById(R.id.otp_layout);
        verify=(Button)findViewById(R.id.verify);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main_layout.setVisibility(View.INVISIBLE);
                otp_layout.setVisibility(View.VISIBLE);
            }
        });
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginUser.this, dashmainadapter.ProfileUpdate.class);
                startActivity(intent);
                finish();
            }
        });
    }
}