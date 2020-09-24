package com.developer.headthapp.Prescription;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.developer.headthapp.R;
import com.squareup.picasso.Picasso;

public class PrescriptionsView extends AppCompatActivity {
ImageButton back;
String titleF,doctorF,observationF,imageF,idF,dateF;
TextView title,doctor;
ImageView report;
Button share,download;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescriptions_view);
        back=(ImageButton)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Intent intent=getIntent();
        titleF=intent.getStringExtra("title");
        doctorF=intent.getStringExtra("doctor");
        imageF=intent.getStringExtra("image");
        idF=intent.getStringExtra("id");
        report=(ImageView)findViewById(R.id.report);
        share=(Button)findViewById(R.id.share);
        download=(Button)findViewById(R.id.download);
        title=(TextView)findViewById(R.id.title);
        doctor=(TextView)findViewById(R.id.doctor);
        Picasso.with(PrescriptionsView.this).load(imageF).placeholder(R.drawable.ic_pdf).into(report);
        title.setText(titleF);
        doctor.setText(doctorF);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}