package com.developer.headthapp.NotificationsDir;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.biometrics.BiometricManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.headthapp.ApiMethods.JsonParser;
import com.developer.headthapp.ApiMethods.networkData;
import com.developer.headthapp.R;

import org.json.JSONObject;
import org.w3c.dom.Text;

public class NotificationLayout extends AppCompatActivity {
String id,titleN,dateN,contentN,statusN;
TextView title,date,content;
ImageButton back;
ProgressBar progress;
Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_layout);
        context=NotificationLayout.this;
        title=(TextView)findViewById(R.id.title);
        date=(TextView)findViewById(R.id.date);
        content=(TextView)findViewById(R.id.content);
        progress=(ProgressBar)findViewById(R.id.progress);
        back=(ImageButton)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Intent intent=getIntent();
        id=intent.getStringExtra("id");
        titleN=intent.getStringExtra("title");
        dateN=intent.getStringExtra("date");
        contentN=intent.getStringExtra("content");
        statusN=intent.getStringExtra("status");
        title.setText(titleN);
        date.setText(dateN);
        content.setText(contentN);
        new informBackend().execute();
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q) {
//            BiometricManager biometricManager = BiometricManager.from(NotificationLayout.this);
//            switch ()
        }
    }
    public class informBackend extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            String url=new networkData().url+new networkData().updateNotification;
            String data=new JsonParser().deleteNotification(url,id);
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progress.setVisibility(View.INVISIBLE);
            if(s!=null)
            {
                try{
                    JSONObject object = new JSONObject(s);
                    String status = object.getString("status");
                    if(status.equals("1"))
                    {
                        Toast.makeText(context,"Notification updated",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(context,"Not Updated",Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}