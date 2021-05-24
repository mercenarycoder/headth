package com.developer.headthapp.NotificationsDir;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.headthapp.ApiMethods.JsonParser;
import com.developer.headthapp.ApiMethods.networkData;
import com.developer.headthapp.R;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

public class Notifications extends AppCompatActivity {
ArrayList<notiClass> list;
adapterNotification adapter;
Context context;
ImageButton back;
RecyclerView get_notification;
SwipeRefreshLayout refresh;
ProgressBar progress;
TextView nop;
FirebaseAuth mauth=FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        context=Notifications.this;
        //formList();
        back=(ImageButton)findViewById(R.id.back);
        nop=(TextView)findViewById(R.id.nop);
        progress=(ProgressBar)findViewById(R.id.progress);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        get_notification=(RecyclerView)findViewById(R.id.get_notification);
        refresh=(SwipeRefreshLayout)findViewById(R.id.refresh);


        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh.setRefreshing(false);
                new getNotification().execute();
            }
        });
        new getNotification().execute();
    }

 public class getNotification extends AsyncTask<String,String,String>
 {
     @Override
     protected void onPreExecute() {
         super.onPreExecute();
         progress.setVisibility(View.VISIBLE);
     }

     @Override
     protected String doInBackground(String... strings) {
         new networkData();
         String base= networkData.url;
         String method=networkData.notification;
         String url=base+method;
         String number=mauth.getCurrentUser().getPhoneNumber();
         number=number.substring(3,number.length());
         String uploadId= UUID.randomUUID().toString();

         String json=new JsonParser().viewOffer(url,number);
         return json;
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
//                 Toast.makeText(context,"Reaching here",Toast.LENGTH_SHORT).show();
                 if(status.equals("1"))
                 {
                     JSONArray array=object.getJSONArray("obj");
                     list=new ArrayList<>();
                     for(int i=0;i<array.length();i++)
                     {
                         JSONObject object1 = array.getJSONObject(i);
                         String id=object1.getString("id");
                         String content=object1.getString("content");
                         String date=object1.getString("date");
                         String dd[]=date.split("T");
                         String title=object1.getString("title");
                         String statusN=object1.getString("status");
                         list.add(new notiClass(id,title,dd[0],content,statusN));
                     }
//                     Toast.makeText(context,"also here",Toast.LENGTH_SHORT).show();
                     if(list.size()>0)
                     {
                         Collections.reverse(list);
                         get_notification.setHasFixedSize(true);
                         get_notification.setLayoutManager(new LinearLayoutManager(context));
                         adapter=new adapterNotification(list,context);
                         get_notification.setAdapter(adapter);
                     }
                     else
                     {
                         nop.setVisibility(View.VISIBLE);
                         get_notification.setHasFixedSize(true);
                         get_notification.setLayoutManager(new LinearLayoutManager(context));
                         adapter=new adapterNotification(list,context);
                         get_notification.setAdapter(adapter);
                     }
                 }
                 else{
                     nop.setVisibility(View.VISIBLE);
                     AlertDialog.Builder builder = new AlertDialog.Builder(context);
                     builder.setTitle("Update")
                             .setMessage("Some Error in fetching info please swipe to refresh")
                             .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                 @Override
                                 public void onClick(DialogInterface dialogInterface, int i) {

                                 }
                             });
                     builder.create();
                     builder.show();
                 }
             }
             catch (Exception e)
             {
                 AlertDialog.Builder builder = new AlertDialog.Builder(context);
                 builder.setTitle("Update")
                         .setMessage("\n Please check your Internet connection")
                         .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialogInterface, int i) {

                             }
                         });
                 builder.create();
                 builder.show();
             }
         }
     }
 }
}