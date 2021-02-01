package com.developer.headthapp.Prescription;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.headthapp.ApiMethods.JsonParser;
import com.developer.headthapp.ApiMethods.networkData;
import com.developer.headthapp.DeleteClass;
import com.developer.headthapp.Qr.QRone;
import com.developer.headthapp.R;
import com.google.android.gms.vision.text.Line;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PrescriptionsView extends AppCompatActivity {
ImageButton back;
String titleF,doctorF,observationF,imageF,idF,dateF,urlF;
TextView title,doctor,edit,delete;
LinearLayout options;
ImageView menu;
RecyclerView multiRecycler;
ArrayList<presImageClass> list;
prescriptionAdapterView adapter;
WebView web;
ProgressBar progress;
FirebaseAuth mauth=FirebaseAuth.getInstance();
Context context=PrescriptionsView.this;
Button share,download;
ProgressDialog progressDialog;
String optionF="edit";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_prescriptions_view);
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("Sending information");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progressDialog.show();
        back=(ImageButton)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Prescriptions();
                Prescriptions.adding =true;
                finish();
            }
        });
        Intent intent=getIntent();
        titleF=intent.getStringExtra("title");
        doctorF=intent.getStringExtra("doctor");
        imageF=intent.getStringExtra("image");
        idF=intent.getStringExtra("id");
        urlF=intent.getStringExtra("url");
        //layout code for the edit and delete dialog in the activity
        delete=(TextView)findViewById(R.id.delete);
        edit=(TextView)findViewById(R.id.edit);
        options=(LinearLayout)findViewById(R.id.options);
        menu=(ImageButton)findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(options.getVisibility()==View.VISIBLE)
                {
                    options.setVisibility(View.INVISIBLE);
                }
                else
                {
                    options.setVisibility(View.VISIBLE);
                }
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              optionF="edit";
              options.setVisibility(View.INVISIBLE);
              dialogShower();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              optionF="delete";
              dialogShower();
              options.setVisibility(View.INVISIBLE);
            }
        });
        //the dialog code was till here
//        report=(ImageView)findViewById(R.id.report);
        share=(Button)findViewById(R.id.share);
        download=(Button)findViewById(R.id.download);
        title=(TextView)findViewById(R.id.title);
        web=(WebView)findViewById(R.id.web);
        progress=(ProgressBar)findViewById(R.id.progress);
        doctor=(TextView)findViewById(R.id.doctor);
        //from here multiple images code is starting
        multiRecycler=(RecyclerView)findViewById(R.id.multirecyler);
        list=new ArrayList<>();
        multiRecycler.setVisibility(View.INVISIBLE);
        if(Build.VERSION.SDK_INT>=23) {
            if (checkPermission()) {

            } else {
                requestPermission();
            }
        }
        if(imageF.contains(".jpeg")) {
             String images[]=imageF.split(";");
             for(int i=0;i<images.length;i++)
             {
                 list.add(new presImageClass(i,new networkData().url_image+images[i]));
             }
             adapter=new prescriptionAdapterView(list,context);
             multiRecycler.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
             multiRecycler.setAdapter(adapter);
             multiRecycler.setHasFixedSize(true);
             multiRecycler.setVisibility(View.VISIBLE);
        }
        else
        {
            //Toast.makeText(PrescriptionsView.this,"Pdf file",Toast.LENGTH_LONG).show();
            multiRecycler.setVisibility(View.INVISIBLE);
            web.setVisibility(View.VISIBLE);
            web.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    progress.setVisibility(View.VISIBLE);
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    progress.setVisibility(View.INVISIBLE);
                }
            });
            web.getSettings().setJavaScriptEnabled(true);
            web.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url="+ new networkData().url.substring(0
                    , new networkData().url.length() - 4) +imageF);
        }
        title.setText(titleF);
        doctor.setText(doctorF);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number=mauth.getCurrentUser().getPhoneNumber();
                number=number.substring(3);
                String base=new networkData().url+new networkData().checkShare+"?url="+urlF+"&mobile="+number;
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, "Sharing URL");
                i.putExtra(Intent.EXTRA_TEXT, base);
                startActivity(Intent.createChooser(i, "Share URL"));
            }
        });
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(isStoragePermissionGranted()) {
                        if(imageF.contains(".jpeg")) {

                            new getBitmapClass().execute();
                            Toast.makeText(context, "Getting bitmap", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                    Toast.makeText(context,"Pdf downloader soon here",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(context, "Permission Required", Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                    Toast.makeText(context,"Error in permission", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void dialogShower()
    {
        final Dialog dialog=new Dialog(context, 0);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_delete);
        ImageButton close_btn2=(ImageButton)dialog.findViewById(R.id.close_btn2);
        close_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        Button no=(Button)dialog.findViewById(R.id.no);
        Button yes=(Button)dialog.findViewById(R.id.yes);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Prescriptions();
                Prescriptions.adding =true;
                if(optionF.equals("delete"))
                {
                    new deleteItems().execute();
                }
                else
                {
                    Toast.makeText(context,"From here edit option will gt enavled",Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public class deleteItems extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String url=new networkData().url+new networkData().deletePrescription;
            ArrayList<String> arr= new ArrayList<>();
            ArrayList<String> arr2= new ArrayList<>();
            arr.add(idF);
            arr2.add(imageF);
            String number=mauth.getCurrentUser().getPhoneNumber();
            number=number.substring(3,number.length());
            String json=new JsonParser().deleteBigItems(url,arr,arr2,number);
            return json;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            if(s!=null)
            {
                try{
                    JSONObject jsonObject = new JSONObject(s);
                    String status = jsonObject.getString("status");
                    final String responce2=String.valueOf(jsonObject.get("msg"));
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Update")
                            .setMessage(responce2)
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    dd=new DeleteClass("fdd");
                                    finish();
                                }
                            });
                    builder.create();
                    builder.show();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context,"Permission granted",Toast.LENGTH_SHORT).show();
                return true;
            } else {

                Toast.makeText(context,"Permission not granted",Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(PrescriptionsView.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        22);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Toast.makeText(context,"Permission granted",Toast.LENGTH_SHORT).show();
            return true;
        }
    }
    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }
    private void requestPermission()
    {

        Toast.makeText(context,"Initial reach",Toast.LENGTH_SHORT).show();
        if (ActivityCompat.shouldShowRequestPermissionRationale(PrescriptionsView.this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(PrescriptionsView.this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(PrescriptionsView.this,
                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 22);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 22:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(context,"Click again to save the file",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context,"Prescriptions cannot be saved because permission denied",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    public class getBitmapClass extends AsyncTask<String, String, Bitmap>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            try{
                URL url1=new URL(imageF);
                // HttpURLConnection conn=(HttpURLConnection)url1.openConnection();
                InputStream input=url1.openStream();
                Bitmap bitmap= BitmapFactory.decodeStream(input);
                System.out.println("Normal IMAGe ------------------------------------------------------");
                return bitmap;
            }catch (Exception e)
            {
                e.printStackTrace();
                System.out.println("Exception IMAGe ------------------------------------------------------");
                return null;
            }
        }

        @SuppressLint("WrongThread")
        @Override
        protected void onPostExecute(Bitmap s) {
            super.onPostExecute(s);
            if(s!=null)
            {
              //  Toast.makeText(context,"Bitmap recieved",Toast.LENGTH_SHORT).show();
                Bitmap bitmap=s;
                try{
                    if(Build.VERSION.SDK_INT>=23) {
                        if(checkPermission()) {
                            saveImage(bitmap,titleF);
                        }

                    }
                    else
                    {
                        saveImage(bitmap,titleF);
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    Toast.makeText(context,"Exception Occourred",Toast.LENGTH_SHORT).show();
                }
                finally {

                }
            }
        }
    }
    private void saveImage(Bitmap finalBitmap, String image_name) {
MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(),finalBitmap,image_name,"first save");
Toast.makeText(context,"Image Saved",Toast.LENGTH_SHORT).show();
    }
}