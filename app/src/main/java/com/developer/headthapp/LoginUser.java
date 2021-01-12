package com.developer.headthapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.developer.headthapp.ApiMethods.JsonParser;
import com.developer.headthapp.ApiMethods.networkData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

public class LoginUser extends AppCompatActivity {
    EditText number,edit1,edit2,edit3,edit4,edit5,edit6;
    Button verify;
    ImageButton start;
    String numberP;
    FirebaseAuth mAuth;
    Context context;
    String codeSent;
    String phonenum;
    boolean account=false;
    SharedPreferences preferences;
    ProgressDialog progressDialog;
   // DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    SharedPreferences.Editor editor;
    LinearLayout otp_layout,main_layout;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth=FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()!=null)
        {
         Intent intent=new Intent(LoginUser.this,HealthCart.class);
         startActivity(intent);
         finish();
        }
        setContentView(R.layout.activity_login_user);
        context=LoginUser.this;
        preferences=context.getSharedPreferences("virgin", Context.MODE_PRIVATE);
        if(preferences.getString("first","yes").equals("yes")) {
            editor = preferences.edit();
            editor.putString("first", "yes");
            editor.apply();
            editor.commit();
        }
        start=(ImageButton)findViewById(R.id.start);
        verify=(Button)findViewById(R.id.verify);
        number=(EditText)findViewById(R.id.number);
        number.setFocusable(true);
        number.requestFocus();

        edit1=(EditText)findViewById(R.id.edit1);
        edit2=(EditText)findViewById(R.id.edit2);
        edit3=(EditText)findViewById(R.id.edit3);
        edit4=(EditText)findViewById(R.id.edit4);
        edit5=(EditText)findViewById(R.id.edit5);
        edit6=(EditText)findViewById(R.id.edit6);
        edit1.addTextChangedListener(new watcher(edit1));
        edit2.addTextChangedListener(new watcher(edit2));
        edit3.addTextChangedListener(new watcher(edit3));
        edit4.addTextChangedListener(new watcher(edit4));
        edit5.addTextChangedListener(new watcher(edit5));
        edit6.addTextChangedListener(new watcher(edit6));
        otp_layout=(LinearLayout)findViewById(R.id.otp_layout);
        main_layout=(LinearLayout)findViewById(R.id.main_layout);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str=number.getText().toString();
                if(str.length()==10)
                {
                    numberP=str;
                    sendVerificationCode();
                    new checkAccount().execute();
                }
                else
                {
                    Toast.makeText(context,"Invalid Phone Number",Toast.LENGTH_SHORT).show();
                }
            }
        });
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifySignInCode();
            }
        });
    }
    private void sendVerificationCode(){

        String phone = number.getText().toString();
        String pho = "+";
        Integer cod= 91;
        String total= Integer.toString(cod);
        phonenum = pho.concat(total).concat(phone);

        if(phone.isEmpty()){
            number.setError("Phone number is required");
            number.requestFocus();
            return;
        }

        if(phone.length() < 10 ){
            number.setError("Please enter a valid phone");
            number.requestFocus();
            return;
        }

        // editTextCode.setVisibility(View.VISIBLE);
//        editTextCode.setEnabled(true);
        main_layout.setVisibility(View.INVISIBLE);
        otp_layout.setVisibility(View.VISIBLE);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phonenum,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    private void verifySignInCode(){

        String code ="";
        int i=0;
        switch(i)
        {
            case 0:
                code+=edit1.getText().toString();
            case 1:
                code+=edit2.getText().toString();
            case 2:
                code+=edit3.getText().toString();
            case 3:
                code+=edit4.getText().toString();
            case 4:
                code+=edit5.getText().toString();
            case 5:
                code+=edit6.getText().toString();
                break;
        }
        if(code.length()<6)
        {
            Toast.makeText(LoginUser.this,"Code should be of length 6", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(code)){
            Toast.makeText(LoginUser.this, "Please enter code", Toast.LENGTH_SHORT).show();
            //waitingDialog.dismiss();
        }
        else {
            edit1.setText(code.charAt(0));
            edit2.setText(code.charAt(1));
            edit3.setText(code.charAt(2));
            edit4.setText(code.charAt(3));
            edit5.setText(code.charAt(4));
            edit6.setText(code.charAt(5));
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, code);
            signInWithPhoneAuthCredential(credential);
        }
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(LoginUser.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // FirebaseUser user = task.getResult().getUser();
                            // waitingDialog.dismiss();
//                          new checkAccount().execute();
                            if(account)
                            {
                                Intent intent=new Intent(LoginUser.this, HealthCart.class);
                                startActivity(intent);
                                finish();
                            }
                            else{
                                Intent intent=new Intent(LoginUser.this, ProfileUpdate.class);
                                intent.putExtra("edit","false");
                                startActivity(intent);
                                finish();
                            }
                        }
                    }
                });
    }
    public class checkAccount extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
//            progressDialog=new ProgressDialog(context);
//            progressDialog.setMessage("Sending information");
//            progressDialog.setCanceledOnTouchOutside(false);
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String url=new networkData().url+new networkData().getprofile;
            String json=new JsonParser().viewOffer(url,numberP);
            return json;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//            progressDialog.dismiss();
            if(s!=null)
            {
                try{
                    JSONObject on = new JSONObject(s);
                    JSONArray array=on.getJSONArray("data");
                    if(array.length()>0)
                    {
//                        Intent intent=new Intent(LoginUser.this, HealthCart.class);
//                        startActivity(intent);
//                        finish();
                        account=true;
                    }
                    else
                    {
//                        Intent intent=new Intent(LoginUser.this, ProfileUpdate.class);
//                        intent.putExtra("edit","false");
//                        startActivity(intent);
//                        finish();
                        account=false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new
            PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                @Override
                public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                    Toast.makeText(LoginUser.this, "Phone number verified instantly",
                            Toast.LENGTH_LONG).show();
                    signInWithPhoneAuthCredential(phoneAuthCredential);
                }

                @Override
                public void onVerificationFailed(FirebaseException e) {

                    Toast.makeText(LoginUser.this,"Verification Failed",Toast.LENGTH_SHORT).show();
                    Toast.makeText(LoginUser.this,String.valueOf(e),Toast.LENGTH_SHORT).show();
                    System.out.println("------------------------------"+e);
                }

                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    //  super.onCodeSent(s, forceResendingToken);
                    codeSent = s;
                    Toast.makeText(LoginUser.this,"Code is being sent",Toast.LENGTH_SHORT).show();
                }
            };
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
                    if (text.length() == 1) {
                        edit5.requestFocus();
                    } else if (text.length() == 0) {
                        edit4.requestFocus();
                    }
                    break;
                }
                case R.id.edit5: {
                    if (text.length() == 1) {
                        edit6.requestFocus();
                    } else if (text.length() == 0) {
                        edit4.requestFocus();
                    }
                    break;
                }
                case R.id.edit6: {
                    if (text.length() == 0) {
                        edit5.requestFocus();
                    }
                    break;
                }
            }
        }
    }

}