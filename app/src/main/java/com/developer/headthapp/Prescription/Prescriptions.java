package com.developer.headthapp.Prescription;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.headthapp.ApiMethods.JsonParser;
import com.developer.headthapp.ApiMethods.networkData;
import com.developer.headthapp.DeleteClass;
import com.developer.headthapp.FragmentMains.FragmentHistiry;
import com.developer.headthapp.R;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class Prescriptions extends AppCompatActivity {
RecyclerView previous_pres;
ArrayList<presClass> list2;
ArrayList<String> data_cont;
ArrayList<Integer> counter;
Context context;
ImageButton back,filter;
dashboard2 adapter;
FirebaseAuth mauth;
SwipeRefreshLayout refresh;
static boolean adding=false;
boolean searched=false;
DeleteClass dd=new DeleteClass("fdfd");
ProgressDialog progressDialog;
TextView nop;
Button add_prescription,remove_prescription,previous;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mauth=FirebaseAuth.getInstance();
        setContentView(R.layout.activity_prescriptions);
        context=Prescriptions.this;
        refresh=(SwipeRefreshLayout)findViewById(R.id.refresh);
        previous=(Button)findViewById(R.id.previous);
        previous.setVisibility(View.INVISIBLE);
        previous.setEnabled(false);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new getallPres().execute();
                previous.setVisibility(View.INVISIBLE);
                previous.setEnabled(false);
            }
        });
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh.setRefreshing(false);
                new getallPres().execute();
            }
        });
        filter=(ImageButton)findViewById(R.id.filter);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(list2.size()>0)
                dialogShower();
                else
                    Toast.makeText(context,"No Items to run this operation",Toast.LENGTH_LONG).show();
            }

        });
        back=(ImageButton)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        add_prescription=(Button)findViewById(R.id.add);
        remove_prescription=(Button)findViewById(R.id.remove);
        remove_prescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              new deleteItems().execute();
            }
        });
        add_prescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Prescriptions.this, PrescriptionAdd.class);
                startActivity(intent);
                adding=true;
            }
        });
        previous_pres=(RecyclerView)findViewById(R.id.previous_pres);
        nop=(TextView)findViewById(R.id.nop);
        new getallPres().execute();
    }
    public void dialogShower()
    {
        final Dialog dialog=new Dialog(context, 0);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.prescription_search);
        ImageButton close_btn2=(ImageButton)dialog.findViewById(R.id.close_dialog);
        close_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        AutoCompleteTextView search_key=(AutoCompleteTextView)dialog.findViewById(R.id.search_key);
        ImageButton now_search=(ImageButton) dialog.findViewById(R.id.now_search);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (context, android.R.layout.select_dialog_item, data_cont);
        search_key.setThreshold(1);
        search_key.setAdapter(adapter);
        now_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                String str=search_key.getText().toString();
                filterRecycler(str);
            }
        });
        dialog.show();
    }
    public void filterRecycler(String str)
    {
        searched=true;
        ArrayList<presClass> listx = new ArrayList<>();
        for(int i=0;i<list2.size();i++)
        {
            presClass item=list2.get(i);
            if(item.getDiesease().contains(str)||item.getDoctor().contains(str)||item.getObservation().contains(str))
            {
                listx.add(item);
            }
            else
            {
                continue;
            }
        }
        adapter=new dashboard2(listx,context);
        previous_pres.setHasFixedSize(true);
        previous_pres.setLayoutManager(new LinearLayoutManager(context));
        previous_pres.setAdapter(adapter);
        if(listx.size()>0)
        {
            nop.setVisibility(View.INVISIBLE);
            previous.setVisibility(View.INVISIBLE);
            previous.setEnabled(false);
        }
        else
        {
            nop.setVisibility(View.VISIBLE);
            nop.setText("Your search results for '"+str+"'");
            previous.setVisibility(View.VISIBLE);
            previous.setEnabled(true);

        }
    }

    @Override
    protected void onResume() {
       if(adding)
       {
           adding=false;
           new getallPres().execute();
       }
           super.onResume();
    }

    @Override
    public void onBackPressed() {
        if(searched)
        {
            searched=false;
            new getallPres().execute();
        }
        else
        super.onBackPressed();
    }

    public class deleteItems extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
          progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String url=new networkData().url+new networkData().deletePrescription;
            ArrayList<String> arr= new ArrayList<>();
            ArrayList<String> arr2= new ArrayList<>();
            HashMap mapper=dd.listD;
            Iterator<Map.Entry<String,String>> it=mapper.entrySet().iterator();
            while(it.hasNext())
            {
                Map.Entry<String, String> pair = (Map.Entry<String, String>) it.next();
                System.out.println(pair.getKey() + " = " + pair.getValue());
                arr.add(pair.getKey());
                arr2.add(pair.getValue());
            }
            String number=mauth.getCurrentUser().getPhoneNumber();
            number=number.substring(3,number.length());
            String json=new JsonParser().deleteBigItems(url,arr,arr2,number);
            return json;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            if(s!=null)
            {
                try{
                    JSONObject jsonObject = new JSONObject(s);
                    String status = jsonObject.getString("status");
                    final String responce2=String.valueOf(jsonObject.get("msg"));
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Update")
                            .setMessage(responce2)
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dd=new DeleteClass("fdd");
                                    new getallPres().execute();
                                }
                            });
                    builder.create();
                    builder.show();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
    public class getallPres extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(context);
            progressDialog.setMessage("Sending information");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            new networkData();
            String base= networkData.url;
            String method=networkData.getprescriptions;
            String url=base+method;
            String number=mauth.getCurrentUser().getPhoneNumber();
            number=number.substring(3,number.length());
            String uploadId= UUID.randomUUID().toString();

            String json=new JsonParser().viewOffer(url,number);
            return json;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if(null!=result)
            {
                try
                {
                    list2=new ArrayList<>();
                    data_cont=new ArrayList<>();
                    counter=new ArrayList<>();
                    JSONObject jsonObject = new JSONObject(result);
                    final String responce = String.valueOf(jsonObject.get("status"));
                   // final String responce2=String.valueOf(jsonObject.get("msg"));
                    if(responce.equals("1"))
                    {
                    JSONArray data=jsonObject.getJSONArray("data");
                    for(int i=0;i<data.length();i++)
                    {
                        JSONObject object = data.getJSONObject(i);
                        String title=object.getString("title");
                        data_cont.add(title);
                        String doctor=object.getString("doctor");
                        data_cont.add(doctor);
                        String observation=object.getString("observation");
                        data_cont.add(observation);
                        String date[]=object.getString("date").split("T");
                        data_cont.add(date[0]);
                        String id=object.getString("id");
                        String image=object.getString("image");
                        counter.add(i);
                        list2.add(new presClass(title,date[0],doctor,image,id,observation));
                    }
                        adapter=new dashboard2(list2,context);
                        previous_pres.setHasFixedSize(true);
                        previous_pres.setLayoutManager(new LinearLayoutManager(context));
                        previous_pres.setAdapter(adapter);
                        if(list2.size()>0)
                        {
                            nop.setVisibility(View.INVISIBLE);
                        }
                        else
                        {
                            nop.setVisibility(View.VISIBLE);
                            nop.setText("No Items found");
                        }
                     }
                    else {
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
                catch (JSONException e) {
                    e.printStackTrace();
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        final String responce2=String.valueOf(jsonObject.get("msg"));
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Update")
                                .setMessage(responce2)
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                        builder.create();
                        builder.show();
                    }
                    catch (Exception r)
                    {

                    }
                }
            }
            //Toast.makeText(signup_Activity.this, "something missing", Toast.LENGTH_SHORT).show();
            else
            {
                Toast.makeText(context,"please check details and try again",Toast.LENGTH_SHORT).show();
            }

        }
    }
}