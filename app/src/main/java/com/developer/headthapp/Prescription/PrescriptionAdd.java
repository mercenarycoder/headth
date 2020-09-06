package com.developer.headthapp.Prescription;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.headthapp.ApiMethods.JsonParser;
import com.developer.headthapp.ApiMethods.networkData;
import com.developer.headthapp.Nominations;
import com.developer.headthapp.ProfileUpdate;
import com.developer.headthapp.R;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class PrescriptionAdd extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    ImageButton back;
    ImageView image;
    EditText title,doc_name,observation;
    Button choose,upload;
    TextView date;
    Uri imageuri;
    Context context;
    Bitmap img=null;
    FirebaseAuth mauth;
    Calendar myCalendar;
    String titleF,docF,observationF,dateF,imageF="";
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_add);
        initiaLize();
        mauth=FirebaseAuth.getInstance();
        context=PrescriptionAdd.this;
        back=(ImageButton)findViewById(R.id.back);
        date=(TextView)findViewById(R.id.date);
        myCalendar=Calendar.getInstance();
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO Auto-generated method stub
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String myFormat = "MM/dd/yy"; //In which you need put here
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                        date.setText(sdf.format(myCalendar.getTime()));
                    }
                };
                new DatePickerDialog(PrescriptionAdd.this,date2,myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               titleF=title.getText().toString();
               dateF=date.getText().toString();
               docF=doc_name.getText().toString();
               observationF=observation.getText().toString();
               if(imageF.equals(""))
               {
                   Toast.makeText(context,"Choose a image and try again",Toast.LENGTH_SHORT).show();
                   return;
               }
               else
               {
                   if(titleF.isEmpty()||dateF.isEmpty()||docF.isEmpty()||observationF.isEmpty())
                   {
                       Toast.makeText(context,"Please fill all the fields",Toast.LENGTH_SHORT).show();
                       return;
                   }
                   else
                   {
                       new uploadPres().execute();
                   }
               }
            }
        });
    }
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_REQUEST&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null)
        {
            imageuri=data.getData();
            try
            {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageuri);
                image.setImageBitmap(bitmap);
                img=bitmap;
                //imageView_pic.setImageURI(imageuri);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                img.compress(Bitmap.CompressFormat.JPEG,75,byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                imageF= Base64.encodeToString(byteArray,Base64.DEFAULT);
               //                Log.d("image ", "doInBackground: "+convertImage);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void initiaLize()
    {
        title=(EditText)findViewById(R.id.title);
        observation=(EditText)findViewById(R.id.observation);
        doc_name=(EditText)findViewById(R.id.doc_name);
        choose=(Button)findViewById(R.id.choose);
        upload=(Button)findViewById(R.id.upload);
        image=(ImageView)findViewById(R.id.image);
    }
public class uploadPres extends AsyncTask<String,String,String>
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
        String method=networkData.precription;
        String url=base+method;
        String number=mauth.getCurrentUser().getPhoneNumber();
        number=number.substring(3,number.length()-1);
        String json=new JsonParser().saveCategory(url,number,titleF,dateF,imageuri,docF,observationF);
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
                JSONObject jsonObject = new JSONObject(result);
                final String responce = String.valueOf(jsonObject.get("status"));
                final String responce2=String.valueOf(jsonObject.get("msg"));
                if(responce.equals("1"))
                {
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
}
}