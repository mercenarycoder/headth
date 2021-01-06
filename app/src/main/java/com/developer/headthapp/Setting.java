package com.developer.headthapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.vision.text.Line;
import com.google.firebase.auth.FirebaseAuth;

public class Setting extends AppCompatActivity {
LinearLayout logout,privacy,doctor,notification;
ImageButton back;
Context context=Setting.this;
SharedPreferences preferences;
SharedPreferences.Editor editor;
FirebaseAuth mauth=FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
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
                                Intent intent=new Intent(Setting.this,LoginUser.class);
                                mauth.signOut();
                                startActivity(intent);
                                finish();
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
}