package com.developer.headthapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.developer.headthapp.ApiMethods.JsonParser;
import com.developer.headthapp.ApiMethods.networkData;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.UUID;

public class Nominations extends AppCompatActivity {
Button verify;
EditText name1,name2,name3,name4,name5;
EditText phone1,phone2,phone3,phone4,phone5;
String name[],nameF,nameU,rec_id,phoneU;
String phone[],phoneF;
ArrayList<emergencyClass> list;
String edit="false";
ProgressDialog dialog;
FirebaseAuth mauth;
Context context;
int z=0;
boolean hare=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=Nominations.this;
        dialog=new ProgressDialog(context);
        dialog.setMessage("Please wait");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCanceledOnTouchOutside(false);
        name=new String[5];
        phone=new String[5];
        Intent intent=getIntent();
        edit=intent.getStringExtra("edit");
        if(edit.equals("true"))
        {
          new getEmergency().execute();
        }
        else
        {

        }
        mauth=FirebaseAuth.getInstance();
        setContentView(R.layout.activity_nominations);
        name1=(EditText)findViewById(R.id.name1);
        name2=(EditText)findViewById(R.id.name21);
        name3=(EditText)findViewById(R.id.name3);
        name4=(EditText)findViewById(R.id.name4);
        name5=(EditText)findViewById(R.id.name5);
        phone1=(EditText)findViewById(R.id.number1);
        phone2=(EditText)findViewById(R.id.number21);
        phone3=(EditText)findViewById(R.id.number3);
        phone4=(EditText)findViewById(R.id.number4);
        phone5=(EditText)findViewById(R.id.number5);

        verify=(Button)findViewById(R.id.verify);
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edit.equals("true"))
                {
                    boolean done=false;
                    submitUpdate(0);
                    done=true;

                }
                else {
         name[0]=name1.getText().toString();
         name[1]=name2.getText().toString();
         name[2]=name3.getText().toString();
         name[3]=name4.getText().toString();
         name[4]=name5.getText().toString();
         phone[0]=phone1.getText().toString();
         phone[1]=phone2.getText().toString();
         phone[2]=phone3.getText().toString();
         phone[3]=phone4.getText().toString();
         phone[4]=phone5.getText().toString();
         nameF="";
         phoneF="";
         for(int i=0;i<5;i++)
         {
             if(checkName(name[i])&&checkPhone(phone[i]))
             {
                 hare=true;
                 phoneF+=phone[i]+",";
                 nameF+=name[i]+",";
             }
         }
         if(nameF.endsWith(","))
         {
             nameF=nameF.substring(0,nameF.length()-1);
         }
         if(phoneF.endsWith(","))
         {
             phoneF=phoneF.substring(0,phoneF.length()-1);
         }
         if(hare) {
             Toast.makeText(context,nameF,Toast.LENGTH_SHORT).show();
             Toast.makeText(context,phoneF,Toast.LENGTH_SHORT).show();
             new submitEmergency().execute();
             hare=false;
         }
         else
         {
             Toast.makeText(context,"Please fill atleast one emergency contact",Toast.LENGTH_SHORT).show();
         }
            }
        }
    });
    }
    public boolean checkName(String str)
    {
        if(str.length()>1)
        {
            return true;
        }
        return false;
    }
    public boolean checkPhone(String s1)
    {
        if(s1.length()==10)
        {
            return true;
        }
        return false;
    }
    public void setTextPhone(int i)
    {
     switch (i)
     {
         case 0:
         {
             emergencyClass obj=list.get(i);
             name1.setText(obj.getName());
             phone1.setText(obj.getPhone());
             break;
         }
         case 1:
         {
             emergencyClass obj=list.get(i);
             name2.setText(obj.getName());
             phone2.setText(obj.getPhone());
             break;
         }
         case 2:
         {
             emergencyClass obj=list.get(i);
             name3.setText(obj.getName());
             phone3.setText(obj.getPhone());
             break;
         }
         case 3:
         {
             emergencyClass obj=list.get(i);
             name4.setText(obj.getName());
             phone4.setText(obj.getPhone());
             break;
         }
         case 5:
         {
             emergencyClass obj=list.get(i);
             name5.setText(obj.getName());
             phone5.setText(obj.getPhone());
             break;
         }
         default:
         {
           Toast.makeText(context,"Not here",Toast.LENGTH_SHORT).show();
         }
     }
    }
    public void submitUpdate(int i)
    {
        switch (i)
        {
            case 0:
            {

                nameU=name1.getText().toString();
                phoneU= phone1.getText().toString();
                if(!(i>=z)) {
                    emergencyClass obj = list.get(i);
                    rec_id = obj.getId();

                    if (!nameU.isEmpty() && phoneU.length()==10) {
                        hare=true;
                        new updateEmergency().execute();
                    }
                }
                else
                {
                    if(!nameU.isEmpty() && phoneU.length() == 10) {
                        hare=true;
                        new addEmergency().execute();
                    }
                }
                break;
            }
            case 1:
            {
                nameU=name2.getText().toString();
                phoneU= phone2.getText().toString();
                if(!(i>=z)) {
                    emergencyClass obj = list.get(i);
                    rec_id = obj.getId();
                    if (!nameU.isEmpty() && phoneU.length()==10) {
                        hare=true;
                        new updateEmergency().execute();
                    }
                }
                else
                {
                    if(!nameU.isEmpty() && phoneU.length() == 10) {
                        hare=true;
                        new addEmergency().execute();
                    }
                }
                break;
            }
            case 2:
            {
                nameU=name3.getText().toString();
                phoneU= phone3.getText().toString();
                if(!(i>=z)) {
                    emergencyClass obj = list.get(i);
                    rec_id = obj.getId();
                    if (!nameU.isEmpty() && phoneU.length()==10) {
                        hare=true;
                        new updateEmergency().execute();
                    }
                }
                else
                {
                    if(!nameU.isEmpty() && phoneU.length() == 10) {
                        hare=true;
                        new addEmergency().execute();
                    }
                }
                break;
            }
            case 3:
            {
                nameU=name4.getText().toString();
                phoneU= phone4.getText().toString();
                if(!(i>=z)) {
                    emergencyClass obj = list.get(i);
                    rec_id = obj.getId();
                    if (!nameU.isEmpty() && phoneU.length()==10) {
                        hare=true;
                        new updateEmergency().execute();
                    }
                }
                else
                {
                    if(!nameU.isEmpty() && phoneU.length() == 10) {
                        hare=true;
                        new addEmergency().execute();
                    }
                }
                break;
            }
            case 4:
            {
                nameU=name5.getText().toString();
                phoneU= phone5.getText().toString();
                if(!(i>=z)) {
                    emergencyClass obj = list.get(i);
                    rec_id = obj.getId();
                    if (!nameU.isEmpty() && phoneU.length()==10) {
                        hare=true;
                        new updateEmergency().execute();
                    }
                }
                else
                {
                    if(!nameU.isEmpty() && phoneU.length() == 10) {
                       hare=true;
                        new addEmergency().execute();
                    }
                }
                break;
            }
            default:
            {
              //  Toast.makeText(context,"Not here",Toast.LENGTH_SHORT).show();
            }
        }
    }
    public class addEmergency extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String url=new networkData().url+new networkData().singleemergency;
            String number=mauth.getCurrentUser().getPhoneNumber();
            number=number.substring(3,number.length());
            String uploadId= UUID.randomUUID().toString();
            String json=new JsonParser().addEmergency(url,number,nameU,phoneU);
            return json;
           // return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s!=null)
            {
                try {
                    JSONObject obj=new JSONObject(s);
                    String status = obj.getString("status");
                    if(status.equals("1"))
                    {
                        Toast.makeText(context,"Record Inserted",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(context,"Not Inserted",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public class getEmergency extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String url=new networkData().url+new networkData().getemergency;
            String number=mauth.getCurrentUser().getPhoneNumber();
            number=number.substring(3,number.length());
            String uploadId= UUID.randomUUID().toString();
            String json=new JsonParser().viewOffer(url,number);
            return json;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dialog.dismiss();
            if(result!=null)
            {
                try{
                    list=new ArrayList<>();
                    JSONObject jsonObject = new JSONObject(result);
                    final String responce = String.valueOf(jsonObject.get("status"));
                    // final String responce2=String.valueOf(jsonObject.get("msg"));
                    if(responce.equals("1"))
                    {
                        Toast.makeText(context,"Reching here",Toast.LENGTH_SHORT).show();
                        JSONArray data=jsonObject.getJSONArray("data");
                        for(int i=0;i<data.length();i++)
                        {
                            JSONObject obj=data.getJSONObject(i);
                            String rec_id = obj.getString("rec_id");
                            String name = obj.getString("name");
                            String phone = obj.getString("phone");
                            list.add(new emergencyClass(rec_id,name,phone));
                            z++;
                            setTextPhone(i);
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
                    catch (Exception e2)
                    {

                    }
                }
            }
            else
            {
                Toast.makeText(context,"Some Networking error try again later",Toast.LENGTH_SHORT).show();
            }
        }
        }

    public class updateEmergency extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           // dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String url=new networkData().url+new networkData().updateemergency;
            String number=mauth.getCurrentUser().getPhoneNumber();
            number=number.substring(3,number.length());
            String json=new JsonParser().updateHsnItem(url,rec_id,nameU,phoneU,number);
            return json;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //dialog.dismiss();
            if(s!=null)
            {
            try{
                JSONObject object=new JSONObject(s);
                String status = object.getString("status");
                String msg=object.getString("msg");
                if(status.equals("1"))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Update")
                            .setMessage(msg)
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                    builder.create();
                    builder.show();
                }
            }
            catch(Exception e)
            {

            }
            }
        }
    }
    public class submitEmergency extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog=new ProgressDialog(context);
            dialog.setMessage("Please wait");
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }
        @Override
        protected String doInBackground(String... strings) {
            String baseUrl=new networkData().url;
            String emergency=new networkData().emergency;
            String url=baseUrl+emergency;
            String number=mauth.getCurrentUser().getPhoneNumber();
            number=number.substring(3,number.length());
            String json=new  JsonParser().setEmergency(url,number,nameF,phoneF);
            return json;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dialog.dismiss();
            if(result!=null)
            {
                try
                {
                    JSONObject jsonObject = new JSONObject(result);
                    final String responce = String.valueOf(jsonObject.get("status"));
                    final String responce2=String.valueOf(jsonObject.get("msg"));
                    if(responce.equals("1"))
                    {
                        Intent intent=new Intent(Nominations.this,HealthCart.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
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
            else
            {
                Toast.makeText(context,"Please Check the details and try again",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
