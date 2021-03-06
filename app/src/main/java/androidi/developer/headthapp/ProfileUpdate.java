package androidi.developer.headthapp;

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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import androidi.developer.headthapp.ApiMethods.*;

//import com.developer.headthapp.R;
import androidi.developer.headthapp.R;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import androidi.developer.headthapp.ApiMethods.JsonParser;
import androidi.developer.headthapp.ApiMethods.networkData;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import static android.text.InputType.TYPE_NULL;

public class ProfileUpdate extends AppCompatActivity {
    Button verify;
    Context context;
    ProgressDialog dialog;
    TextView name,dob;
    Spinner height,weight,blood;
    SpinnerAdapter2 adapterH,adapterW,adapterB;
    ArrayList<SpinnerClass> listH,listW,listB;
    FirebaseAuth mauth;
    String nameS,dobS,heightS,weightS,bloodS,edit="false";
    SharedPreferences checker,testCheck;
    SharedPreferences.Editor editor,testMake;
Calendar myCalendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=ProfileUpdate.this;
        testCheck=getSharedPreferences("TestCheck",Context.MODE_PRIVATE);
        testMake=testCheck.edit();
        checker=getSharedPreferences("basicinfo",Context.MODE_PRIVATE);
        editor=checker.edit();
        mauth=FirebaseAuth.getInstance();
        Intent intent=getIntent();
        edit=intent.getStringExtra("edit");
        setContentView(R.layout.activity_profile_update);
        name=(TextView) findViewById(R.id.name);
        dob=(TextView) findViewById(R.id.dob);
        myCalendar=Calendar.getInstance();
        formHeight();
        formBlood();
        formWeight();
        height=(Spinner) findViewById(R.id.height);
        adapterH=new SpinnerAdapter2(context,listH);
        height.setAdapter(adapterH);
        height.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SpinnerClass item= (SpinnerClass) adapterView.getSelectedItem();
                heightS=item.getId();
//                Toast.makeText(context,heightS,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        weight=(Spinner) findViewById(R.id.weight);
        adapterW=new SpinnerAdapter2(context,listW);
        weight.setAdapter(adapterW);
        weight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SpinnerClass item= (SpinnerClass) adapterView.getSelectedItem();
                weightS=item.getId();
//                Toast.makeText(context,heightS,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        blood=(Spinner) findViewById(R.id.blood);
        adapterB=new SpinnerAdapter2(context,listB);
        blood.setAdapter(adapterB);
        blood.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SpinnerClass item= (SpinnerClass) adapterView.getSelectedItem();
                bloodS=item.getId();
//                Toast.makeText(context,heightS,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        verify=(Button)findViewById(R.id.verify);
        if(edit.equals("true"))
        {
            nameS=intent.getStringExtra("name");
            name.setText(nameS);
            dob.setText(intent.getStringExtra("dob"));
            String ss=intent.getStringExtra("height");
            String sw=intent.getStringExtra("weight");
            String sb=intent.getStringExtra("blood");
            int x=0,y=0,z=0;
            for(int i=0;i<listH.size();i++)
            {
                SpinnerClass item=listH.get(i);
                if(item.getId().equals(ss))
                {
                    x=i;
                }
            }
            for(int i=0;i<listW.size();i++)
            {
                SpinnerClass item=listW.get(i);
                if(item.getId().equals(sw))
                {
                    y=i;
                }
            }
            for(int i=0;i<listB.size();i++)
            {
                SpinnerClass item=listB.get(i);
                if(item.getId().equals(sb))
                {
                    z=i;
                }
            }
            height.setSelection(x);
            weight.setSelection(y);
            blood.setSelection(z);
        }
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nameS=name.getText().toString();
                dobS=dob.getText().toString();
//                heightS=height.getText().toString();
//                weightS=weight.getText().toString();
//                bloodS=blood.getText().toString();
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
                        String myFormat = "dd-MM-YYYY"; //In which you need put here
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
        listH=new ArrayList<>();
        for(int i=120;i<240;i++)
        {
            listH.add(new SpinnerClass(String.valueOf(i) ,i+" cms"));
        }
    }
    public void formWeight()
    {
        listW=new ArrayList<>();
        for(int i=30;i<120;i++)
        {
            listW.add(new SpinnerClass(String.valueOf(i),i+" kgs"));
        }
    }
    public void formBlood()
    {
        listB=new ArrayList<>();
        listB.add(new SpinnerClass("O+","O+"));
        listB.add(new SpinnerClass("A+","A+"));
        listB.add(new SpinnerClass("B+","B+"));
        listB.add(new SpinnerClass("O-","O-"));
        listB.add(new SpinnerClass("A-","A-"));
        listB.add(new SpinnerClass("AB+","AB+"));
        listB.add(new SpinnerClass("AB-","AB-"));
        listB.add(new SpinnerClass("B-","B-"));
    }
    ArrayList<String> options;
    Dialog dialog2;
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
            String logInfo=testCheck.getString("LogInfo","No");
            String number="";
            if(mauth.getCurrentUser()!=null) {
                number = mauth.getCurrentUser().getPhoneNumber();
                number=number.substring(3,number.length());
            }
            else if(logInfo.equals("Yes")) {
                number="1234567890";
            }
//            number=number.substring(3,number.length());
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
                        builder.setTitle("Error")
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
