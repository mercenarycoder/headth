package com.developer.headthapp.Covid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.headthapp.ApiMethods.JsonParser;
import com.developer.headthapp.ApiMethods.networkData;
import com.developer.headthapp.R;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.UUID;

public class SearchResults extends AppCompatActivity {
    String city,pin,blood,age;
    FirebaseAuth mauth=FirebaseAuth.getInstance();
    Context context;
    TextView request;
    RecyclerView results;
    ImageButton back;
    ArrayList<CovidBox> list;
    TextView no_res;
    CovidAdapter adapter;
    ProgressDialog progressDialog;
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
        request=(TextView)findViewById(R.id.request);
        results=(RecyclerView) findViewById(R.id.results);
        request.setText("Search Results for blood group: "+blood+" city:"+city+" age:"+age+" pin:");
        no_res=(TextView)findViewById(R.id.no_res);
        no_res.setVisibility(View.INVISIBLE);
        new getContacts().execute();
    }
    private class getContacts extends AsyncTask<String,String,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String url=new networkData().url+new networkData().getVolunteer;
            String number=mauth.getCurrentUser().getPhoneNumber();
            number=number.substring(3,number.length());
            String uploadId= UUID.randomUUID().toString();
            String json=new JsonParser().getHelp(url,age,blood,number,pin,city);
            System.out.println(json);
            return json;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            if(s!=null){
                try {
                    Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
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