package com.developer.headthapp.ApiMethods;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.headthapp.HealthCart;
import com.developer.headthapp.LoginUser;
import com.developer.headthapp.Nominations;
import com.developer.headthapp.R;
import com.developer.headthapp.Setting;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

public class Verifier extends AppCompatActivity {
EditText edit1,edit2,edit3,edit4;
TextView btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn0,title;
String otp="",confirm="";
String first="no";
SharedPreferences preferences;
SharedPreferences.Editor editor;
Button delete;
Context context;
String account;
FirebaseAuth mauth=FirebaseAuth.getInstance();
ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences=getSharedPreferences("basicinfo",Context.MODE_PRIVATE);
        editor=preferences.edit();
        account=preferences.getString("account","not done");
        setContentView(R.layout.activity_verifier);
        context=Verifier.this;
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("Sending information");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        title=(TextView)findViewById(R.id.title);
        if(preferences.getString("otp","not done").equals("done"))
        {
            first="no";
        }
        else if(account.equals("done"))
        {
            new deleteOtp().execute();
            title.setText("Please Choose Otp");
            first="yes";
        }
        else
        {
            title.setText("Please Choose Otp");
            first="yes";
        }
        edit1=(EditText)findViewById(R.id.edit11);
        edit2=(EditText)findViewById(R.id.edit2);
        edit3=(EditText)findViewById(R.id.edit3);
        edit4=(EditText)findViewById(R.id.edit4);
        delete=(Button)findViewById(R.id.delete);
        btn1=(TextView)findViewById(R.id.btn1);
        btn2=(TextView)findViewById(R.id.btn2);
        btn3=(TextView)findViewById(R.id.btn3);
        btn4=(TextView)findViewById(R.id.btn4);
        btn5=(TextView)findViewById(R.id.btn5);
        btn6=(TextView)findViewById(R.id.btn6);
        btn7=(TextView)findViewById(R.id.btn7);
        btn8=(TextView)findViewById(R.id.btn8);
        btn9=(TextView)findViewById(R.id.btn9);
        btn0=(TextView)findViewById(R.id.btn0);
//        edit1.addTextChangedListener(new watcher(edit1));
//        edit2.addTextChangedListener(new watcher(edit2));
//        edit3.addTextChangedListener(new watcher(edit3));
//        edit4.addTextChangedListener(new watcher(edit4));
        edit1.setKeyListener(null);
        edit2.setKeyListener(null);
        edit3.setKeyListener(null);
        edit4.setKeyListener(null);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch(otp.length())
                {
                    case 1:
                        edit1.setText("");
                        otp="";
                        break;
                    case 2:
                        edit2.setText("");
                        otp=otp.substring(0,1);
//                        Toast.makeText(context,otp,Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        edit3.setText("");
                        otp=otp.substring(0,2);
//                        Toast.makeText(context,otp,Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        edit4.setText("");
                        otp=otp.substring(0,3);
//                        Toast.makeText(context,otp,Toast.LENGTH_SHORT).show();
                        break;
                    default:
//                        Toast.makeText(context,"Nothing to delete",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           otpSetter(0);
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           otpSetter(1);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            otpSetter(2);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            otpSetter(3);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            otpSetter(4);
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            otpSetter(5);
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            otpSetter(6);
            }
        });
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            otpSetter(7);
            }
        });
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            otpSetter(8);
            }
        });
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            otpSetter(9);
            }
        });
    }
    public void otpSetter(int i)
    {
        String num="";
        switch(i)
        {
            case 0:
                num="0";
                break;
            case 1:
                num="1";
                break;
            case 2:
                num="2";
                break;
            case 3:
                num="3";
                break;
            case 4:
                num="4";
                break;
            case 5:
                num="5";
                break;
            case 6:
                num="6";
                break;
            case 7:
                num="7";
                break;
            case 8:
                num="8";
                break;
            case 9:
                num="9";
                break;
        }
        if(otp.length()<4)
        {
            otp+=num;
            switch (otp.length())
            {
                case 1:
                    edit1.setText("*");
                    break;
                case 2:
                    edit2.setText("*");
                    break;
                case 3:
                    edit3.setText("*");
                    break;
                case 4:
                    edit4.setText("*");
                    if(first.equals("yes"))
                    {
                        if(confirm.equals(otp)) {
                            new newOtpAdder().execute();
                        }
                        else if(confirm.length()<1)
                        {
                            confirm=otp;
                            otp="";
                            Toast.makeText(context,"Enter Otp again to confirm",Toast.LENGTH_SHORT).show();
                            edit1.setText("");
                            edit2.setText("");
                            edit3.setText("");
                            edit4.setText("");
                        }
                        else if(!confirm.equals(otp))
                        {
                            Toast.makeText(context,"Given otp does not match the previous one",Toast.LENGTH_SHORT).show();
                            otp="";
                        }
                    }
                    else
                    {
                        new checkOtp().execute();
                    }
                    break;
            }
        }
        else if (otp.length()==4)
        {

        }
        else
        {
            Toast.makeText(context,"Otp can be of 4 digit only",Toast.LENGTH_SHORT).show();
        }
    }
    private class checkOtp extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String base= networkData.url;
            String method=networkData.checkotp;
            String url=base+method;
            String number=mauth.getCurrentUser().getPhoneNumber();
            number=number.substring(3,number.length());
            String uploadId= UUID.randomUUID().toString();
            String json=new JsonParser().checkOtpUser(url,number,otp);
            return json;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            if(s!=null)
            {
                try{
                    JSONObject object = new JSONObject(s);
                    String status = String.valueOf(object.get("status"));
                    String msg=String.valueOf(object.get("msg"));
                    if(status.equals("1"))
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Update")
                                .setMessage(msg)
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
//                                        editor.putString("otp","done");
//                                        editor.apply();
//                                        editor.commit();
                                        Intent intent=new Intent(Verifier.this, HealthCart.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                        builder.create();
                        builder.show();
                    }
                    else
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Update")
                                .setMessage(msg)
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                          Toast.makeText(context,"Otp not identified",Toast.LENGTH_SHORT).show();
                                    }
                                });
                        builder.create();
                        builder.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private class newOtpAdder extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String base= networkData.url;
            String method=networkData.newOtp;
            String url=base+method;
            String number=mauth.getCurrentUser().getPhoneNumber();
            number=number.substring(3,number.length());
            String uploadId= UUID.randomUUID().toString();
            String json=new JsonParser().addOtpUser(url,number,otp);
            return json;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            if(s!=null)
            {
                try{
                    JSONObject object = new JSONObject(s);
                    String status = String.valueOf(object.get("status"));
                    String msg=String.valueOf(object.get("msg"));
                    if(status.equals("1"))
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Update")
                                .setMessage(msg)
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        editor.putString("otp","done");
                                        editor.apply();
                                        editor.commit();
                                        Intent intent=new Intent(Verifier.this, HealthCart.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                        builder.create();
                        builder.show();
                    }
                    else
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Update")
                                .setMessage(msg)
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(context,"Otp not inserted try again",Toast.LENGTH_SHORT).show();
                                    }
                                });
                        builder.create();
                        builder.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }
    private class  watcher implements TextWatcher {
        View view;
        public watcher(View view)
        {
            this.view=view;
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String text = s.toString();
            switch (view.getId()) {
                case R.id.edit1: {
                    if (text.length() == 1) {
                        edit2.requestFocus();
                    }
                    break;
                }
                case R.id.edit2: {
                    if (text.length() == 1) {
                        edit3.requestFocus();
                    } else if (text.length() == 0) {
                        edit1.requestFocus();
                    }
                    break;
                }
                case R.id.edit3: {
                    if (text.length() == 1) {
                        edit4.requestFocus();
                    } else if (text.length() == 0) {
                        edit3.requestFocus();
                    }
                    break;
                }
                case R.id.edit4: {
                    if (text.length() == 0) {
                        edit4.requestFocus();
                    }
                    break;
                }
            }
        }
    }
    private class deleteOtp extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String base= networkData.url;
            String method=networkData.deleteOtp;
            String url=base+method;
            String number=mauth.getCurrentUser().getPhoneNumber();
            number=number.substring(3,number.length());
            String uploadId= UUID.randomUUID().toString();
            String json=new JsonParser().deleteOtp(url,number);
            return json;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            if(s!=null)
            {
                try{
                    JSONObject obj=new JSONObject(s);
                    String status=String.valueOf(obj.get("status"));
                    if(status.equals("1"))
                    {
                     Toast.makeText(context,"Previous otp cleared",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(context,"Please check internet connection",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}