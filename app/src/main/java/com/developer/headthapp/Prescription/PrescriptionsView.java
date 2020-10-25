package com.developer.headthapp.Prescription;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.headthapp.R;
import com.squareup.picasso.Picasso;

public class PrescriptionsView extends AppCompatActivity {
ImageButton back;
String titleF,doctorF,observationF,imageF,idF,dateF;
TextView title,doctor;
ImageView report;
WebView web;
ProgressBar progress;
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
        web=(WebView)findViewById(R.id.web);
        progress=(ProgressBar)findViewById(R.id.progress);
        doctor=(TextView)findViewById(R.id.doctor);
        if(imageF.contains(".jpeg")) {
            Picasso.with(PrescriptionsView.this).load(imageF).placeholder(R.drawable.ic_pdf).into(report);
        }
        else
        {
            //Toast.makeText(PrescriptionsView.this,"Pdf file",Toast.LENGTH_LONG).show();
            report.setVisibility(View.INVISIBLE);
            web.setVisibility(View.VISIBLE);
            web.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    progress.setVisibility(View.VISIBLE);
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    progress.setVisibility(View.INVISIBLE);
                }
            });
            web.getSettings().setJavaScriptEnabled(true);
            web.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url="+imageF);
        }
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