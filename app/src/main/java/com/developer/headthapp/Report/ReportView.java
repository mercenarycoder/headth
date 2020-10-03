package com.developer.headthapp.Report;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.headthapp.R;

public class ReportView extends AppCompatActivity {
ImageButton back;
TextView title,dr_name;
WebView webview;
Button share,download;
Context context;
ProgressBar progress;
String drF,titF,urlF,idF;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=ReportView.this;
        setContentView(R.layout.activity_report_view);
        Intent intent=getIntent();
        drF=intent.getStringExtra("doctor");
        titF=intent.getStringExtra("title");
        urlF=intent.getStringExtra("url");
        idF=intent.getStringExtra("id");
        title=(TextView)findViewById(R.id.title);
        back=(ImageButton)findViewById(R.id.back);
        dr_name=(TextView)findViewById(R.id.dr_name);
        webview=(WebView)findViewById(R.id.webview);
        progress=(ProgressBar)findViewById(R.id.progress);
        share=(Button)findViewById(R.id.share);
        download=(Button)findViewById(R.id.download);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        String webviewurl = urlF;
        webview.getSettings().setJavaScriptEnabled(true);
        System.out.println("----------------"+webviewurl);
        webview.setWebViewClient(new WebViewClient() {
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
        if(webviewurl.contains(".pdf")){
            webviewurl = "https://docs.google.com/gview?embedded=true&url=" + webviewurl;
            Toast.makeText(context,"Its a Pdf file",Toast.LENGTH_SHORT).show();
        }

        webview.loadUrl(webviewurl);

        dr_name.setText(drF);
        title.setText(titF);
    }
}