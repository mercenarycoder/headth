package androidi.developer.headthapp;

import androidi.developer.headthapp.ApiMethods.JsonParser;
import androidi.developer.headthapp.ApiMethods.Verifier;
import androidi.developer.headthapp.ApiMethods.networkData;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.job.JobScheduler;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.developer.headthapp.R;
import androidi.developer.headthapp.R;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

public class Setting extends AppCompatActivity {
LinearLayout logout,privacy,doctor,notification,change_otp,delete_account;
ImageButton back;
Context context=Setting.this;
SharedPreferences preferences;
SharedPreferences.Editor editor;
FirebaseAuth mauth=FirebaseAuth.getInstance();
    SharedPreferences testCheck;
    SharedPreferences.Editor testMake;
ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        progressDialog=new ProgressDialog(context);
        testCheck=getSharedPreferences("TestCheck",Context.MODE_PRIVATE);
        testMake=testCheck.edit();
        progressDialog.setMessage("Logging off");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        preferences=getSharedPreferences("basicinfo",Context.MODE_PRIVATE);
        editor=preferences.edit();
        back=(ImageButton)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        logout=(LinearLayout)findViewById(R.id.logout);
        privacy=(LinearLayout)findViewById(R.id.privacy);
        doctor=(LinearLayout)findViewById(R.id.upgrade);
        notification=(LinearLayout)findViewById(R.id.notification);
        change_otp=(LinearLayout)findViewById(R.id.change_otp);
        delete_account=(LinearLayout)findViewById(R.id.delete_account);
        delete_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog=new Dialog(context, 0);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.dialog_phone);
                Button accept=dialog.findViewById(R.id.accept);
                TextView title=dialog.findViewById(R.id.title);
                TextView content=(TextView)dialog.findViewById(R.id.content);
                content.setText("Your account and all your stored details will be removed. Within 30 days. Are you sure you want to proceed?");
                title.setText("Confirm Deletion");
                accept.setText("Proceed");
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
                        new deleteOtp().execute();
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        change_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Alert")
                        .setMessage("Do you really want to Change your Pin ?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
//                                new deleteOtp().execute();
                            Intent intent=new Intent(Setting.this, Verifier.class);
                                preferences=getSharedPreferences("basicinfo",Context.MODE_PRIVATE);
                                editor=preferences.edit();
                                editor.putString("otp","not done");
                                editor.apply();
                                editor.commit();
                                startActivity(intent);
                                finish();
                            }

                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                builder.create();
                builder.show();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Update")
                        .setMessage("Do you really want to Logout ?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                new deleteOtp().execute();
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                builder.create();
                builder.show();
            }
        });
        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,WiproId.class);
                startActivity(intent);
            }
        });

    }
    //to cancel bulk plasma notification service
    public void cancelJob2(){
        JobScheduler scheduler=(JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.cancel(444);
        Log.d("BlukNotification:-", "cancelJob2: Job scheduling failed");
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
            String logInfo=testCheck.getString("LogInfo","No");
            String number="";
            if(mauth.getCurrentUser()!=null) {
                number = mauth.getCurrentUser().getPhoneNumber();
                number=number.substring(3,number.length());
            }
            else if(logInfo.equals("Yes")) {
                number="1234567890";
            }
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
                     cancelJob2();
                     Intent intent=new Intent(Setting.this,LoginUser.class);
                     intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                     mauth.signOut();
                     editor.clear();
                     editor.apply();
                     editor.commit();
                     testMake.clear();
                     testMake.apply();
                     testMake.commit();
                     startActivity(intent);
                     finish();
                 }
                 else
                 {
                    Toast.makeText(context,"Failed to logout please try again",Toast.LENGTH_SHORT).show();
                 }
             } catch (JSONException e) {
                 e.printStackTrace();
             }
            }
        }
    }
}