package androidi.developer.headthapp;

import androidi.developer.headthapp.ApiMethods.JsonParser;
import androidi.developer.headthapp.ApiMethods.Verifier;
import androidi.developer.headthapp.ApiMethods.networkData;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
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
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.developer.headthapp.R;
import androidi.developer.headthapp.R;
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
    Context context=LoginUser.this;
    String codeSent;
    String phonenum;
    boolean account=false;
    SharedPreferences preferences,checker,testCheck;
    ProgressDialog progressDialog;
   // DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    SharedPreferences.Editor editor,editor2,testMake;

    LinearLayout otp_layout,main_layout;
    Dialog dialog;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth=FirebaseAuth.getInstance();
        checker=getSharedPreferences("basicinfo",Context.MODE_PRIVATE);
        testCheck=getSharedPreferences("TestCheck",Context.MODE_PRIVATE);
        testMake=testCheck.edit();
        editor2=checker.edit();
//        editor2.putString("personal","done");
//        editor2.putString("emergency","done");
//        editor2.apply();
//        editor2.commit();
        String testLog=testCheck.getString("LogInfo","No");
        if((mAuth.getCurrentUser()!=null||testLog.equals("Yes"))&&checker.getString("personal","no").equals("done")
                &&checker.getString("emergency","no").equals("done"))
        {
         Intent intent=new Intent(LoginUser.this, Verifier.class);
         startActivity(intent);
         finish();
        }
        else if((mAuth.getCurrentUser()!=null||testLog.equals("Yes"))&&checker.getString("personal","no").equals("done")&&
                checker.getString("emergency","no").equals("no"))
        {

//            Toast.makeText(context,"emergency finder",Toast.LENGTH_SHORT).show();
         Intent intent=new Intent(LoginUser.this,EmergencyContacts.class);
            intent.putExtra("edit","false");
         startActivity(intent);
         finish();
        }
        else if((mAuth.getCurrentUser()!=null||testLog.equals("Yes"))&&checker.getString("personal","no").equals("done")&&
                checker.getString("emergency","no").equals("done")&&
                checker.getString("otp","not done").equals("not done"))
        {

//            Toast.makeText(context,"opt verfier finder",Toast.LENGTH_SHORT).show();
         Intent intent=new Intent(LoginUser.this,Verifier.class);
//            intent.putExtra("edit","false");
         startActivity(intent);
         finish();
        }
        else if((mAuth.getCurrentUser()!=null||testLog.equals("Yes"))&&checker.getString("personal","no").equals("no")
                &&checker.getString("emergency","no").equals("no"))
        {
//            Toast.makeText(context,"profile finder",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(LoginUser.this,ProfileUpdate.class);
            intent.putExtra("edit","false");
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
                    dialog=new Dialog(context, 0);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.dialog_phone);
                    Button accept=dialog.findViewById(R.id.accept);
                    TextView content=(TextView)dialog.findViewById(R.id.content);
                    accept.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            content.setText("Are you sure you wish to proceed with "+numberP);
                            accept.setText("Proceed");
                            accept.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.dismiss();
                                    new checkAccount().execute();
                                }
                            });
                            dialog.show();
                        }
                    });
                    ImageButton close_btn2=dialog.findViewById(R.id.close_btn2);
                    close_btn2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
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
                120,                 // Timeout duration
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
            edit1.setText("*");
            edit2.setText("*");
            edit3.setText("*");
            edit4.setText("*");
            edit5.setText("*");
            edit6.setText("*");
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
                                Intent intent=new Intent(LoginUser.this, Verifier.class);
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
            progressDialog=new ProgressDialog(context);
            progressDialog.setMessage("Sending information");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
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
            progressDialog.dismiss();
            if(numberP.equals("1234567890")){
                testMake.putString("LogInfo","Yes");
                testMake.apply();
                testMake.commit();
                account=true;
                editor2.putString("personal","done");
                editor2.putString("account","done");
                editor2.putString("emergency","done");
                editor2.apply();
                editor2.commit();
                Intent intent=new Intent(LoginUser.this, Verifier.class);
                intent.putExtra("edit","false");
                startActivity(intent);
                finish();
            }
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

                        Toast.makeText(context,"Account Found",Toast.LENGTH_SHORT).show();
                        account=true;
                        editor2.putString("personal","done");
                        editor2.putString("account","done");
                        editor2.putString("emergency","done");
                        editor2.apply();
                        editor2.commit();
                        sendVerificationCode();
                    }
                    else
                    {
//                        Intent intent=new Intent(LoginUser.this, ProfileUpdate.class);
//                        intent.putExtra("edit","false");
//                        startActivity(intent);
//                        finish();

                        Toast.makeText(context,"Account Not found",Toast.LENGTH_SHORT).show();
                        sendVerificationCode();
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