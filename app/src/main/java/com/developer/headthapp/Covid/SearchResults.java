package com.developer.headthapp.Covid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.headthapp.ApiMethods.JsonParser;
import com.developer.headthapp.ApiMethods.networkData;
import com.developer.headthapp.LoggerService.NotificationServiceCovid;
import com.developer.headthapp.R;
import com.developer.headthapp.Schedulers.BlukRaiser;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.UUID;

public class SearchResults extends AppCompatActivity {
    public static final String TAG="BulkRaiser Scheduler";
    String city,pin,blood,age;
    FirebaseAuth mauth=FirebaseAuth.getInstance();
    Context context;
    TextView request;
    RecyclerView results;
    ImageButton back;
    ArrayList<CovidBox> list;
    Button raiser;
    TextView no_res;
    CovidAdapter adapter;
    ProgressDialog progressDialog;
    private boolean checkRaise=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        setContentView(R.layout.activity_search_results);
        context=SearchResults.this;
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("Sending information");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        city=intent.getStringExtra("city");
        pin=intent.getStringExtra("pin");
        blood=intent.getStringExtra("blood");
        age=intent.getStringExtra("age");
        back=(ImageButton)findViewById(R.id.back);
        raiser=(Button)findViewById(R.id.raiser);
        raiser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(checkRaise)
            {
                raiser.setEnabled(false);
                Toast.makeText(context,"bluk alarm is raised have paitence",Toast.LENGTH_SHORT).show();
                return;
            }
            else{
                Dialog dialog=new Dialog(context, 0);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.dialog_phone);
                Button accept=dialog.findViewById(R.id.accept);
                TextView title=dialog.findViewById(R.id.title);
                TextView content=(TextView)dialog.findViewById(R.id.content);
                content.setText("A notification will be sent to all the volunteers along with your number. Are you sure you want to raise a Bulk Request?");
                title.setText("Bulk Request Raiser");
                ImageButton close_btn2=dialog.findViewById(R.id.close_btn2);
                close_btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        scheduleJob2();
                        checkRaise=true;
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        request=(TextView)findViewById(R.id.request);
        results=(RecyclerView) findViewById(R.id.results);
        request.setText("Search Results for blood group: "+blood+" city:"+city+" age:"+age+" pin:"+pin);
        no_res=(TextView)findViewById(R.id.no_res);
        no_res.setVisibility(View.INVISIBLE);
        new getContacts().execute();
    }
    private String callers[];
    public void scheduleJob2(){
        ComponentName componentName=new ComponentName(this, BlukRaiser.class);
        PersistableBundle bundle=new PersistableBundle();
        String caller="";
        for(int i=0;i<list.size();i++)
        {
            CovidBox obj=list.get(i);
            caller+=obj.getMobile()+";";
        }
        if(caller.endsWith(";"))
        {
            caller=caller.substring(0,caller.length()-1);
        }
        callers=caller.split(";");
        new raiseBulk().execute();
        bundle.putString("caller",caller);
//        JobInfo info=new JobInfo.Builder(445,componentName)
////                .setPersisted(true)
//                .setExtras(bundle)
//                .setPeriodic(365*24*60*60*1000)
//                .build();
//        JobScheduler scheduler=(JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE);
//        int resultCode = scheduler.schedule(info);
//        if(resultCode==JobScheduler.RESULT_SUCCESS){
//            Log.d(TAG, "scheduleJob2: Job scheduled");
//        }else{
//            Log.d(TAG, "scheduleJob2: Job scheduling failed");
//        }
    }
    private class raiseBulk extends AsyncTask<String,String,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(String... strings) {
            String url=new networkData().url+new networkData().raiseAlarm;
            JsonParser jsonParser=new JsonParser();
            String number=mauth.getCurrentUser().getPhoneNumber();
            number=number.substring(3,number.length());
            String data=null;
            for(int i=0;i<callers.length;i++) {
                data=new JsonParser().raiseAlarm(url,number,callers[i]);
            }
            Log.d(TAG, "doInBackground: "+data);
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s!=null){
//                jobFinished(params,false);
                Toast.makeText(context,"Bulk request raised",Toast.LENGTH_SHORT).show();
            }
            else{
//                jobFinished(params,false);
                Toast.makeText(context,"Bulk request failed",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class getContacts extends AsyncTask<String,String,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String number=mauth.getCurrentUser().getPhoneNumber();
            number=number.substring(3,number.length());
            String uploadId= UUID.randomUUID().toString();
            String json=null;
            if(pin.length()==0)
            {
                String url=new networkData().url+new networkData().getVolunteerCity;
                json=new JsonParser().getHelpCity(url,age,blood,number,city);
            }
            else{
                String url=new networkData().url+new networkData().getVolunteerPin;
                json=new JsonParser().getHelpPin(url,age,blood,number,pin);
            }
            System.out.println(json);
            return json;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            if(s!=null){
                try {
//                    Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
                    JSONObject obj=new JSONObject(s);
                    String status=String.valueOf(obj.get("status"));
                    if(status.equals("1")){
                        JSONArray arr=obj.getJSONArray("data");
                        list=new ArrayList<>();
                        for(int i=0;i<arr.length();i++){
                            JSONObject object=arr.getJSONObject(i);
                            String name=String.valueOf(object.get("name"));
                            String blood=String.valueOf(object.get("blood"));
                            String age=String.valueOf(object.get("age"));
                            String pin=String.valueOf(object.get("pincode"));
                            String address=String.valueOf(object.get("address"));
                            String mobile=String.valueOf(object.get("mobile"));
                            String city=String.valueOf(object.get("city"));
                            list.add(new CovidBox(name,blood,age,mobile,city,pin));
                        }
                        if(list.size()>0)
                        {
                            adapter=new CovidAdapter(list,context);
                            results.setLayoutManager(new LinearLayoutManager(context));
                            results.setHasFixedSize(true);
                            results.setAdapter(adapter);
                            results.setVisibility(View.VISIBLE);
                            no_res.setVisibility(View.INVISIBLE);
                        }
                        else{

                            results.setVisibility(View.INVISIBLE);
                            no_res.setVisibility(View.VISIBLE);
                        }
                    }
                    else{
                        results.setVisibility(View.INVISIBLE);
                        no_res.setVisibility(View.VISIBLE);
                        final String responce2=String.valueOf(obj.get("msg"));
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Update")
                                .setMessage(responce2)
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                        builder.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}