package com.developer.headthapp.Report;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.headthapp.ApiMethods.JsonParser;
import com.developer.headthapp.ApiMethods.networkData;
import com.developer.headthapp.FragmentMains.DiseaseFragment;
import com.developer.headthapp.ImageRecylerAdapter;
import com.developer.headthapp.R;
import com.developer.headthapp.SpinnerAdapter2;
import com.developer.headthapp.SpinnerClass;
import com.developer.headthapp.imageRecyclerClass;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

import static androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL;

public class ReportAdd extends AppCompatActivity {
ImageButton back;
TextView date;
//ImageView pdf;
EditText title,observer,detail;
private static final int STORAGE_PERMISSION_CODE = 123;
private static final int PICK_IMAGE_REQUEST = 1;
Button choose,submit;
Calendar myCalendar;
Spinner type;
SpinnerAdapter2 adapter;
ArrayList<SpinnerClass> list;
//new multiple image adder code from here
RecyclerView recycler;
ArrayList<imageRecyclerClass> list2=new ArrayList<>();
ImageRecylerAdapter adapter2;

ProgressDialog progressDialog;
FirebaseAuth mauth=FirebaseAuth.getInstance();
String titleF,observerF,dateF,detailF,typeF,base65,reportType=null;
Context context;
    public static String imagePaths;
    public String nameF;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=ReportAdd.this;
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("Sending information");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        setContentView(R.layout.activity_report_add);
        back=(ImageButton)findViewById(R.id.back);
        choose=(Button)findViewById(R.id.choose);
        submit=(Button)findViewById(R.id.submit);
        title=(EditText)findViewById(R.id.title);
        type=(Spinner)findViewById(R.id.type);
        recycler=(RecyclerView)findViewById(R.id.recycler);
        //keep this much code to make it dynamic
        adapter2=new ImageRecylerAdapter(list2,context);
        recycler.setLayoutManager(new LinearLayoutManager(context, HORIZONTAL,false));
        recycler.setHasFixedSize(true);
        recycler.setAdapter(adapter2);
        //till here
        formList();
        adapter=new SpinnerAdapter2(context,list);
        type.setAdapter(adapter);
        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SpinnerClass item=adapter.getItem(i);
                reportType=item.getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        observer=(EditText)findViewById(R.id.observer);
        detail=(EditText)findViewById(R.id.detail);
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
                        String myFormat = "dd/MM/YYYY"; //In which you need put here
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                        date.setText(sdf.format(myCalendar.getTime()));
                    }
                };
                DatePickerDialog dialog=new DatePickerDialog(context,date2,myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH));
                dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                dialog.show();
            }
        });
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               dialogShower();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             titleF=title.getText().toString();
             observerF=observer.getText().toString();
             dateF=date.getText().toString();
             detailF=detail.getText().toString();
             if(titleF.isEmpty())
             {
                 Toast.makeText(context,"title cannot be empty",Toast.LENGTH_SHORT).show();
                 return;
             }
             else if(observerF.isEmpty())
             {
                 Toast.makeText(context,"Observer cannot be empty",Toast.LENGTH_SHORT).show();
                 return;
             }
             else if(detailF.isEmpty())
             {
                 Toast.makeText(context,"Detail cannot be empty",Toast.LENGTH_SHORT).show();
                 return;
             }
             else if(dateF.isEmpty()||dateF.contains("date"))
             {
                 Toast.makeText(context,"Date cannot be empty",Toast.LENGTH_SHORT).show();
                 return;
             }
             else if(list2.size()<=0)
             {
                 Toast.makeText(context,"Document cannot be empty",Toast.LENGTH_SHORT).show();
                 return;
             }
             else if(reportType.isEmpty())
             {
                 Toast.makeText(context,"Please Select your report type",Toast.LENGTH_SHORT).show();
                 return;
             }
             else
             {
                 imagePaths="";
                 for(int i=0;i<list2.size();i++)
                 {
                     imageRecyclerClass item=list2.get(i);
                     imagePaths+=item.getId()+";";
                 }
                 Toast.makeText(context,imagePaths,Toast.LENGTH_SHORT).show();
                 new submitReport().execute();
             }
            }
        });
    }
    public void formList()
    {
        list=new ArrayList<>();
        list.add(new SpinnerClass("others","Select Report Type"));
        list.add(new SpinnerClass("blood","Blood Report"));
        list.add(new SpinnerClass("eye","Eye Report"));
        list.add(new SpinnerClass("dental","Dental Report"));
        list.add(new SpinnerClass("ent","ENT Report"));
        list.add(new SpinnerClass("x_ray","X-Ray Report"));
        list.add(new SpinnerClass("others","Other"));
        reportType="others";
    }
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    private void openPdfChooser()
    {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);

        try {
            startActivityForResult(Intent.createChooser(intent, "Select Your .pdf File"), STORAGE_PERMISSION_CODE);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(ReportAdd.this, "Please Install a File Manager",Toast.LENGTH_SHORT).show();
        }
    }
    Dialog dialog;
    public void dialogShower()
    {
        dialog=new Dialog(context, 0);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_choice);
        ImageButton close_btn2=(ImageButton)dialog.findViewById(R.id.close_btn2);
        TextView image=(TextView)dialog.findViewById(R.id.image);
        TextView pdf=(TextView)dialog.findViewById(R.id.pdf);
        TextView camera=(TextView)dialog.findViewById(R.id.camera);
        close_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
                dialog.dismiss();
            }
        });
        pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             openPdfChooser();
             dialog.dismiss();
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             Intent cam=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
             startActivityForResult(cam,9);
             dialog.dismiss();
            }
        });
        dialog.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Toast.makeText(context, "here", Toast.LENGTH_SHORT).show();
        if(resultCode==RESULT_OK&&requestCode==STORAGE_PERMISSION_CODE&&data!=null&&data.getData()!=null)
        {
            Toast.makeText(context, "reached here", Toast.LENGTH_SHORT).show();
            Uri pdfUri = data.getData();
            base65=getStringPdf(pdfUri);
            base65="data:application/pdf;base64,"+base65;
//            pdf.setImageResource(R.drawable.ic_pdf);
            System.out.println("------------------"+base65);
            typeF=".pdf";
            String uploadId= UUID.randomUUID().toString();
            nameF=uploadId;
            new uploadData().execute();
        }
        if(requestCode==PICK_IMAGE_REQUEST&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null)
        {
           Uri imageuri=data.getData();
            //path=getPath(imageuri);
            try
            {
                Toast.makeText(context,"image choosed",Toast.LENGTH_SHORT).show();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageuri);
                //imageView_pic.setImageURI(imageuri);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,75,byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                base65= Base64.encodeToString(byteArray,Base64.NO_WRAP);
//                pdf.setImageBitmap(bitmap);
                base65="data:image/jpeg;base64,"+base65;
                typeF=".jpeg";
//                System.out.println("-------------------------------------"+base65);
                //                Log.d("image ", "doInBackground: "+convertImage);
                String uploadId= UUID.randomUUID().toString();
                nameF=uploadId;
                new uploadData().execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(requestCode==9&&resultCode==RESULT_OK)
        {
            Uri imageuri=data.getData();
            //path=getPath(imageuri);
            try
            {
                Toast.makeText(context,"image found",Toast.LENGTH_SHORT).show();
                Bitmap bitmap=(Bitmap)data.getExtras().get("data");
                //imageView_pic.setImageURI(imageuri);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,75,byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                base65= Base64.encodeToString(byteArray,Base64.NO_WRAP);
                base65="data:image/jpeg;base64,"+base65;
                typeF=".jpeg";
//                pdf.setImageBitmap(bitmap);
                System.out.println("-------------------------------------"+base65);
                //                Log.d("image ", "doInBackground: "+convertImage);
                String uploadId= UUID.randomUUID().toString();
                nameF=uploadId;
                new uploadData().execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class uploadData extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String url=new networkData().url+new networkData().addImage64;
            String data=new JsonParser().addImage(url,base65,nameF,typeF);
            return data;
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
                    if (status.equals("1"))
                    {
                        String fileName=String.valueOf(obj.get("name"));
                        String msg=String.valueOf(obj.get("msg"));
                        imagePaths+=fileName+";";
                        list2.add(new imageRecyclerClass(base65,fileName,typeF));
                        adapter2=new ImageRecylerAdapter(list2,context);
                        recycler.setLayoutManager(new LinearLayoutManager(context, HORIZONTAL,false));
                        recycler.setHasFixedSize(true);
                        recycler.setAdapter(adapter2);
                        Toast.makeText(context,"Image Inserted",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(context,"Image Not Inserted",Toast.LENGTH_SHORT).show();

                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
    public String getStringPdf (Uri filepath){
        InputStream inputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            inputStream =  getContentResolver().openInputStream(filepath);

            byte[] buffer = new byte[1024];
            byteArrayOutputStream = new ByteArrayOutputStream();

            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        byte[] pdfByteArray = byteArrayOutputStream.toByteArray();
        String base=Base64.encodeToString(pdfByteArray, Base64.NO_WRAP);
        System.out.println("---------------------------------"+base);
        return base;
    }
    public class submitReport extends AsyncTask<String,String,String>
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
            String method=networkData.addReport;
            String url=base+method;
            String number=mauth.getCurrentUser().getPhoneNumber();
            number=number.substring(3,number.length());
            String uploadId= UUID.randomUUID().toString();

            String json=new JsonParser().addReport(url,number,titleF,observerF,dateF,detailF,typeF,imagePaths,reportType);
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
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Update")
                                .setMessage(responce2)
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        finish();
                                    }
                                });
                        builder.create();
                        builder.show();
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