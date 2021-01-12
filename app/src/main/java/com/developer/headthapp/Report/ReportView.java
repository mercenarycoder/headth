package com.developer.headthapp.Report;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.headthapp.ApiMethods.networkData;
import com.developer.headthapp.Prescription.PrescriptionsView;
import com.developer.headthapp.Report.ReportView;
import com.developer.headthapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import android.app.ProgressDialog;

public class ReportView extends AppCompatActivity {
ImageButton back;
TextView title,dr_name;
WebView webview;
Button share,download;
ImageView if_image;
Context context;
FirebaseAuth mauth=FirebaseAuth.getInstance();
ProgressBar progress;
String drF,titF,urlF,idF,typeF,shareF;
    private static final String TAG = "Download Task";

    private String downloadUrl = "", downloadFileName = "";
    private ProgressDialog progressDialog;
    public class CheckForSDCard{
//        if sd card is present or not method
        public boolean isSDCardPresent()
        {
            if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
            {
                return true;
            }
            return false;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=ReportView.this;
        setContentView(R.layout.activity_report_view);
        Intent intent=getIntent();
        drF=intent.getStringExtra("doctor");
        titF=intent.getStringExtra("title");
        urlF=intent.getStringExtra("url");
        idF=intent.getStringExtra("id");
        typeF=intent.getStringExtra("type");
        shareF=intent.getStringExtra("share");
        title=(TextView)findViewById(R.id.title);
        back=(ImageButton)findViewById(R.id.back);
        dr_name=(TextView)findViewById(R.id.dr_name);
        webview=(WebView)findViewById(R.id.webview);
        if_image=(ImageView)findViewById(R.id.if_image);
        if_image.setVisibility(View.INVISIBLE);
        progress=(ProgressBar)findViewById(R.id.progress);
        share=(Button)findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number=mauth.getCurrentUser().getPhoneNumber();
                number=number.substring(3);
                String base=new networkData().url+new networkData().checkShare+"?url="+shareF+"&mobile="+number;
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, "Sharing URL");
                i.putExtra(Intent.EXTRA_TEXT, base);
                startActivity(Intent.createChooser(i, "Share URL"));
            }
        });
        download=(Button)findViewById(R.id.download);
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(isStoragePermissionGranted()) {
                        if(urlF.contains(".jpeg")) {
                            new getBitmapClass().execute();
                            Toast.makeText(context, "Getting bitmap", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
//                            new DownloadFile().execute(urlF,"manjeet_bestrong.pdf");
                            downloadUrl=urlF;
                            downloadFileName="mummyMiss.pdf";
                            new DownloadingTask().execute();
                            Toast.makeText(context,"Pdf downloader testing",Toast.LENGTH_SHORT).show();
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
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        String webviewurl = urlF;
        System.out.println("----------------"+webviewurl);
        webview.setWebViewClient(new WebViewClient() {
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
        if(typeF.contains(".pdf")){
            webview.getSettings().setJavaScriptEnabled(true);
            webviewurl = "https://docs.google.com/gview?embedded=true&url=" + webviewurl;
           // Toast.makeText(context,"Its a Pdf file",Toast.LENGTH_SHORT).show();
            webview.loadUrl(webviewurl);
        }
        else
        {
            webview.setVisibility(View.INVISIBLE);
            //Toast.makeText(context,"Its a Jpeg file",Toast.LENGTH_SHORT).show();
            if_image.setVisibility(View.VISIBLE);
            Picasso.with(context).load(webviewurl).into(if_image);
        }

        dr_name.setText(drF);
        title.setText(titF);
    }
    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context,"Permission granted",Toast.LENGTH_SHORT).show();
                return true;
            } else {

                Toast.makeText(context,"Permission not granted",Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(ReportView.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
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
        if (ActivityCompat.shouldShowRequestPermissionRationale(ReportView.this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(ReportView.this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(ReportView.this,
                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 22);
        }
    }
    private class DownloadingTask extends AsyncTask<Void, Void, Void> {

        File apkStorage = null;
        File outputFile = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Downloading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Void result) {
            try {
                if (outputFile != null) {
                    progressDialog.dismiss();
                    ContextThemeWrapper ctw = new ContextThemeWrapper( context, R.style.AppTheme);
                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctw);
                    alertDialogBuilder.setTitle("Document  ");
                    alertDialogBuilder.setMessage("Document Downloaded Successfully ");
                    alertDialogBuilder.setCancelable(false);
                    alertDialogBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });

                    alertDialogBuilder.setNegativeButton("Open report",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            File pdfFile = new File(Environment.getExternalStorageDirectory()
                                    + "/CodePlayon/" + downloadFileName);  // -> filename = maven.pdf
                            Uri path =  FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName()
                                    + ".provider", pdfFile);;
                            Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
                            pdfIntent.setDataAndType(path, "application/pdf");
                            pdfIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            try{
                                context.startActivity(pdfIntent);
                            }catch(ActivityNotFoundException e){
                                Toast.makeText(context, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    alertDialogBuilder.show();
//                    Toast.makeText(context, "Document Downloaded Successfully", Toast.LENGTH_SHORT).show();
                } else {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                        }
                    }, 3000);

                    Log.e(TAG, "Download Failed");

                }
            } catch (Exception e) {
                e.printStackTrace();

                //Change button text if exception occurs

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                    }
                }, 3000);
                Log.e(TAG, "Download Failed with Exception - " + e.getLocalizedMessage());

            }


            super.onPostExecute(result);
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                URL url = new URL(downloadUrl);//Create Download URl
                HttpURLConnection c = (HttpURLConnection) url.openConnection();//Open Url Connection
                c.setRequestMethod("GET");//Set Request Method to "GET" since we are grtting data
                c.connect();//connect the URL Connection

                //If Connection response is not OK then show Logs
                if (c.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    Log.e(TAG, "Server returned HTTP " + c.getResponseCode()
                            + " " + c.getResponseMessage());

                }


                //Get File if SD card is present
                if (new CheckForSDCard().isSDCardPresent()) {

                    apkStorage = new File(Environment.getExternalStorageDirectory() + "/" + "CodePlayon");
                } else
                    Toast.makeText(context, "Oops!! There is no SD Card.", Toast.LENGTH_SHORT).show();

                //If File is not present create directory
                if (!apkStorage.exists()) {
                    apkStorage.mkdir();
                    Log.e(TAG, "Directory Created.");
                }

                outputFile = new File(apkStorage, downloadFileName);//Create Output file in Main File

                //Create New File if not present
                if (!outputFile.exists()) {
                    outputFile.createNewFile();
                    Log.e(TAG, "File Created");
                }

                FileOutputStream fos = new FileOutputStream(outputFile);//Get OutputStream for NewFile Location

                InputStream is = c.getInputStream();//Get InputStream for connection

                byte[] buffer = new byte[1024];//Set buffer type
                int len1 = 0;//init length
                while ((len1 = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len1);//Write new file
                }

                //Close all connection after doing task
                fos.close();
                is.close();

            } catch (Exception e) {

                //Read exception if something went wrong
                e.printStackTrace();
                outputFile = null;
                Log.e(TAG, "Download Error Exception " + e.getMessage());
            }

            return null;
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
                URL url1=new URL(urlF);
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
                            saveImage(bitmap,titF);
                        }

                    }
                    else
                    {
                        saveImage(bitmap,titF);
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