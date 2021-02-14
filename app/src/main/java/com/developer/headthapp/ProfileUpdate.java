package com.developer.headthapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.developer.headthapp.ApiMethods.*;
import com.developer.headthapp.FragmentMains.DiseaseFragment;
import com.developer.headthapp.Report.ReportView;
import com.google.android.gms.vision.text.Line;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.text.InputType.TYPE_NULL;
import static java.sql.Types.NULL;

public class ProfileUpdate extends AppCompatActivity {
    Button verify;
    Context context;
    ProgressDialog dialog;
    TextView name,dob,height,weight,blood;
    FirebaseAuth mauth;
    String nameS,dobS,heightS,weightS,bloodS,edit="false";
    SharedPreferences checker;
    SharedPreferences.Editor editor;
Calendar myCalendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=ProfileUpdate.this;
        checker=getSharedPreferences("basicinfo",Context.MODE_PRIVATE);
        editor=checker.edit();
        mauth=FirebaseAuth.getInstance();
        Intent intent=getIntent();
        edit=intent.getStringExtra("edit");
        setContentView(R.layout.activity_profile_update);
        name=(TextView) findViewById(R.id.name);
        dob=(TextView) findViewById(R.id.dob);
        myCalendar=Calendar.getInstance();
        height=(TextView) findViewById(R.id.height);
        height.setInputType(TYPE_NULL);
        height.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                formHeight();
                dialogShower("Choose Height",options);
            }
        });
        weight=(TextView) findViewById(R.id.weight);
//        weight.setInputType(NULL);
        weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                formWeight();
                dialogShower("Your Weight Approx",options);
            }
        });

        blood=(TextView) findViewById(R.id.blood);
//        blood.setInputType(NULL);
        blood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                formBlood();
                dialogShower("Your Blood Group",options);
            }
        });
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
                        String myFormat = "dd/MM/YYYY"; //In which you need put here
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                        dob.setText(sdf.format(myCalendar.getTime()));
                    }
                };
                new DatePickerDialog(context,date,myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }
    public void formHeight()
    {
        options=new ArrayList<>();
        for(int i=120;i<240;i++)
        {
            options.add(String.valueOf(i)+" cms");
        }
    }
    public void formWeight()
    {
        options=new ArrayList<>();
        for(int i=30;i<120;i++)
        {
            options.add(String.valueOf(i)+" kgs");
        }
    }
    public void formBlood()
    {
        options=new ArrayList<>();
        options.add("O+");
        options.add("A+");
        options.add("B+");
        options.add("O-");
        options.add("A-");
        options.add("AB+");
        options.add("AB-");
        options.add("B-");
    }
    ArrayList<String> options;
    Dialog dialog2;
    public void dialogShower(String head,ArrayList<String> chose)
    {
        dialog2= new Dialog(ProfileUpdate.this, 0);
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog2.setCancelable(false);
        dialog2.setContentView(R.layout.dialog_chooser);
        ImageButton close_btn2=(ImageButton)dialog2.findViewById(R.id.close_but);
        final TextView name=(TextView)dialog2.findViewById(R.id.option);
        name.setText(head);
        close_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog2.dismiss();
            }
        });
        RecyclerView options=(RecyclerView)dialog2.findViewById(R.id.options);
        dashmainadapter adapter=new dashmainadapter(chose,context) {
            @NonNull
            @Override
            public viewholder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return super.onCreateViewHolder(parent, viewType);
            }

            @Override
            public void onBindViewHolder(@NonNull viewholder1 holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.name.setText(chose.get(position));
                holder.layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(head.contains("Weight"))
                        {
                            weight.setText(chose.get(position).substring(0,3));
                        }
                        if(head.contains("Height"))
                        {
                            height.setText(chose.get(position).substring(0,3));
                        }
                        if(head.contains("Blood"))
                        {
                            blood.setText(chose.get(position));
                        }
                        dialog2.dismiss();
                    }
                });
            }

            @Override
            public int getItemCount() {
                return super.getItemCount();
            }
        };
        options.setLayoutManager(new LinearLayoutManager(context));
        options.setHasFixedSize(true);
        options.setAdapter(adapter);
        dialog2.show();
    }
    public abstract class dashmainadapter extends RecyclerView.Adapter<dashmainadapter.viewholder1>{
        ArrayList<String> list;
        Context context;
        int cats[];
        int anchor[];
        int i=4;
        public dashmainadapter(ArrayList<String> list, Context context)
        {
            this.list=list;
            this.context=context;

        }
        @NonNull
        @Override
        public viewholder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context=parent.getContext();
            View inflator= LayoutInflater.from(context).inflate(R.layout.item_options, parent,
                    false);
            viewholder1 viewhold=new viewholder1(inflator);
            return viewhold;
        }

        @Override
        public void onBindViewHolder(@NonNull final viewholder1 holder, final int position) {
            final String adapter=list.get(position);

        }
        @Override
        public int getItemCount() {
            return list.size();
        }
        public  class viewholder1 extends RecyclerView.ViewHolder
        {
            TextView name;
            LinearLayout layout;
            public viewholder1(@NonNull View itemView) {
                super(itemView);
               layout=(LinearLayout)itemView.findViewById(R.id.layout);
               name=(TextView)itemView.findViewById(R.id.name);
            }
        }
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
                url = new networkData().url+new networkData().update;
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
                        Intent intent=new Intent(ProfileUpdate.this, EmergencyContacts.class);
                        if(edit.equals("true"))
                        {
                            intent.putExtra("edit","true");
                        }
                        else
                        {
                            intent.putExtra("edit","false");
                        }
                        editor.putString("personal","done");
                        editor.apply();
                        editor.commit();
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
