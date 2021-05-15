package com.developer.headthapp.Covid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.developer.headthapp.ApiMethods.JsonParser;
import com.developer.headthapp.ApiMethods.networkData;
import com.developer.headthapp.HealthCart;
import com.developer.headthapp.NotificationCode.NotificationReciever;
import com.developer.headthapp.R;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.developer.headthapp.NotificationCode.App.CHANNEL_ID;

public class CovidCaller extends AppCompatActivity {
    TextView plasma;
    TextView request,no_res;
    FirebaseAuth mauth=FirebaseAuth.getInstance();
    Context context;
    RecyclerView results;
    ImageButton back;
    ArrayList<HelpClass> list;
    ArrayList<String> list2;
    Button raiser;
    CovidPlasmaAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(mauth.getCurrentUser()==null)
        {
            finish();
        }
        setContentView(R.layout.activity_covid_caller);
        plasma=(TextView)findViewById(R.id.plasma);
        context=CovidCaller.this;
        request=(TextView)findViewById(R.id.request);
        no_res=(TextView)findViewById(R.id.no_res);
        results=(RecyclerView)findViewById(R.id.results);
        back=(ImageButton)findViewById(R.id.back);
        Intent intent=new Intent(context, HealthCart.class);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
                finish();
            }
        });
        raiser=(Button)findViewById(R.id.raiser);
        raiser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Alert")
                        .setMessage("Do you really want to clear all requests")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
//                            new cancelling().execute();
                                startActivity(intent);
                                finish();
                            }
                        });
                builder.show();
            }
        });
        new setIt().execute();
    }
    public class cancelling extends AsyncTask<String,String,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String url=new networkData().url+new networkData().getAllRequests;
            JsonParser jsonParser=new JsonParser();
            String number=mauth.getCurrentUser().getPhoneNumber();
            number=number.substring(3,number.length());
            String data=null;
            for(int i=0;i<list.size();i++)
            {
                data=new JsonParser().getAllAlrams(url,number);
            }
                Log.d("cancelling going", "doInBackground: "+data);


            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s!=null){
                Intent intent=new Intent(context,HealthCart.class);
                startActivity(intent);
                finish();
            }
        }
    }
    public class setIt extends AsyncTask<String, String, String>
    {

        @Override
        protected String doInBackground(String... strings) {
            new networkData();
            String base= networkData.url;
            String method=networkData.getAllRequests;
            String url=base+method;
            String number=mauth.getCurrentUser().getPhoneNumber();
            number=number.substring(3,number.length());
            System.out.println("The number is :-"+number);
            String data=new JsonParser().getBulkCalled(url,number);
            Log.d("opened", "doInBackground: "+data);
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s!=null) {
                try {
                    boolean counter=true;
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    ArrayList name = new ArrayList<String>();
                     list = new ArrayList<>();
                     list2=new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object1 = jsonArray.getJSONObject(i);
                        String id=object1.getString("id");
                        String called=object1.getString("called");
                        String caller=object1.getString("caller");
                        String status=object1.getString("status");
                        if(list2.contains(caller))
                        {
                            continue;
                        }
                        else {
                            list2.add(caller);
                            list.add(new HelpClass(id,caller));
                        }
                    }
                    System.out.println(list);
                    adapter= new CovidPlasmaAdapter(list,context);
                    results.setHasFixedSize(true);
                    results.setLayoutManager(new LinearLayoutManager(context));
                    results.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}