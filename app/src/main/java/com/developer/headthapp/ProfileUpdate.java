package com.developer.headthapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;


import com.developer.headthapp.ApiMethods.*;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

import static android.text.InputType.TYPE_NULL;

public class ProfileUpdate extends AppCompatActivity {
    Button verify;
    Context context;
    ProgressDialog dialog;
    EditText name,dob,height,weight,blood;
    FirebaseAuth mauth;
    String nameS,dobS,heightS,weightS,bloodS,edit="false";
Calendar myCalendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=ProfileUpdate.this;
        mauth=FirebaseAuth.getInstance();
        Intent intent=getIntent();
        edit=intent.getStringExtra("edit");
        setContentView(R.layout.activity_profile_update);
        name=(EditText)findViewById(R.id.name);
        dob=(EditText)findViewById(R.id.dob);
        myCalendar=Calendar.getInstance();
        height=(EditText)findViewById(R.id.height);
        weight=(EditText)findViewById(R.id.weight);
        blood=(EditText)findViewById(R.id.blood);
        verify=(Button)findViewById(R.id.verify);
        if(edit.equals("true"))
        {
            nameS=intent.getStringExtra("name");
            name.setText(nameS);
            dob.setText(intent.getStringExtra("dob"));
            height.setText(intent.getStringExtra("height"));
            weight.setText(intent.getStringExtra("weight"));
            blood.setText(intent.getStringExtra("blood"));

        }
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nameS=name.getText().toString();
                dobS=dob.getText().toString();
                heightS=height.getText().toString();
                weightS=weight.getText().toString();
                bloodS=blood.getText().toString();
//                Intent intent=new Intent(ProfileUpdate.this, Nominations.class);
//                startActivity(intent);
//                finish();
                if(nameS.isEmpty()||dobS.isEmpty()||heightS.isEmpty()||weightS.isEmpty()||bloodS.isEmpty())
                {
                    Toast.makeText(context,"Please fill all the above details",Toast.LENGTH_SHORT).show();
                }
                else {
                    new AdminLogin().execute();
                }
            }
        });
        dob.setInputType(TYPE_NULL);
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO Auto-generated method stub
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String myFormat = "MM/dd/yy"; //In which you need put here
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                        dob.setText(sdf.format(myCalendar.getTime()));
                    }
                };
                new DatePickerDialog(context,date,myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }
    private class AdminLogin extends AsyncTask<String,String,String>
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
            JsonParser jsonParser=new JsonParser();
            String url="";
            if(edit.equals("false")) {
                url = "http://192.168.1.3:5000/app1/newUser";
            }
            else
            {
                url=new networkData().url+new networkData().profileUpdate;
            }
            String number=mauth.getCurrentUser().getPhoneNumber();
            number=number.substring(3,number.length());
            String json=jsonParser.signingIn(url,nameS,number,heightS,weightS,bloodS,dobS);
            //        Log.d("return",json);
            return json;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dialog.dismiss();
            //  Log.d("some",result);

            if(null!=result)
            {
                try
                {
                    JSONObject jsonObject = new JSONObject(result);
                    final String responce = String.valueOf(jsonObject.get("status"));
                    final String responce2=String.valueOf(jsonObject.get("msg"));
                    if(responce.equals("1"))
                    {
                        Intent intent=new Intent(ProfileUpdate.this, Nominations.class);
                        if(edit.equals("true"))
                        {
                            intent.putExtra("edit","true");
                        }
                        else
                        {
                            intent.putExtra("edit","false");
                        }
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
            //Toast.makeText(signup_Activity.this, "something missing", Toast.LENGTH_SHORT).show();
            else
            {
                Toast.makeText(context,"please check details and try again",Toast.LENGTH_SHORT).show();
            }
        }
//        public void toDetails(JSONObject jsonObject1) throws JSONException {
//            SharedPreferences login_pref=getApplicationContext().getSharedPreferences("login_details",
//                    getApplicationContext().MODE_PRIVATE);
//            SharedPreferences.Editor editor=login_pref.edit();
//            //Toast.makeText(signup_Activity.this,jsonObject1.getString("user_name"), Toast.LENGTH_SHORT).show();
//            //editor.putString("user_pass",password_getter);
//            editor.putString("user_id",email_get);
//            editor.putString("user_id",email.getText().toString());
//            //editor.putString("user_pass",password.getText().toString());
//            editor.putString("auth_key", String.valueOf(jsonObject1.get("auth_key")));
//            editor.putString("user_name", String.valueOf(jsonObject1.get("user_name")));
//            editor.putString("user_mobile",String.valueOf(jsonObject1.get("user_mobile")));
//            editor.putString("user_state", String.valueOf(jsonObject1.get("user_state")));
//            editor.putString("user_address", String.valueOf(jsonObject1.get("user_address")));
//            editor.putString("user_role", String.valueOf(jsonObject1.get("user_role")));
//            editor.putString("user_city", String.valueOf(jsonObject1.get("user_city")));
//            editor.putString("user_id2", String.valueOf(jsonObject1.get("id")));
//            editor.putString("user_lang", String.valueOf(jsonObject1.get("user_lang")));
//            editor.apply();
//        }
    }
}
