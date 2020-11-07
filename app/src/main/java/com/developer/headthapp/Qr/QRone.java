package com.developer.headthapp.Qr;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGSaver;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.headthapp.ApiMethods.JsonParser;
import com.developer.headthapp.ApiMethods.networkData;
import com.developer.headthapp.Profile;
import com.developer.headthapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.zxing.WriterException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.UUID;

public class QRone extends AppCompatActivity {
ImageButton close_btn;
Button scanner,open_profile;
ImageView qr_logo;
Button share ,save;
String savePath = Environment.getExternalStorageDirectory().getPath() + "/QRCode/";
Bitmap bitmap;
Context context;
TextView name,number;
ProgressDialog dialog;
Uri for_share;
String nameF;
FirebaseAuth mauth=FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=QRone.this;
        setContentView(R.layout.activity_q_rone);
        qr_logo=(ImageView)findViewById(R.id.qr_logo);
        share=(Button)findViewById(R.id.share);
        name=(TextView)findViewById(R.id.name);
        number=(TextView)findViewById(R.id.number);
        open_profile=(Button)findViewById(R.id.open_profile);
        open_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(QRone.this, Profile.class);
                startActivity(intent);
                finish();
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareImageUri(for_share);
            }
        });
        save=(Button)findViewById(R.id.save);
        String number=mauth.getCurrentUser().getPhoneNumber();
        this.number.setText(number);
        number=number.substring(3,number.length());
        QRGEncoder qrgEncoder = new QRGEncoder(number, null, QRGContents.Type.TEXT, 400);
        try {
            // Getting QR-Code as Bitmap
            bitmap = qrgEncoder.encodeAsBitmap();
            // Setting Bitmap to ImageView
            qr_logo.setImageBitmap(bitmap);
        } catch (WriterException e) {
            //Log.v(TAG, e.toString());
            Toast.makeText(QRone.this,"Some issue in making qr",Toast.LENGTH_SHORT).show();
        }
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean save;
                String result;
                try {
                   if(isStoragePermissionGranted()) {
                       saveImage(bitmap,nameF+"qr");
                   }
                } catch (Exception e) {
                    e.printStackTrace();
                Toast.makeText(QRone.this,"Image was not saved",Toast.LENGTH_SHORT).show();
                }
            }
        });
        close_btn=(ImageButton)findViewById(R.id.close_btn);
        scanner=(Button)findViewById(R.id.scanner);
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(QRone.this,QrScanner.class);
                startActivity(intent);
            }
        });
        new getProfile().execute();
    }
    private void shareImageUri(Uri uri){
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setType("image/png");
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mauth.getCurrentUser()==null)
        {
            finish();
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
                ActivityCompat.requestPermissions(QRone.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Toast.makeText(context,"Permission granted",Toast.LENGTH_SHORT).show();
            return true;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            Toast.makeText(context,"Permission Needed",Toast.LENGTH_SHORT).show();
            //resume tasks needing this permission
        }
    }
    private class getProfile extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog=new ProgressDialog(QRone.this);
            dialog.setMessage("Please wait");
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String url=new networkData().url+new networkData().getprofile;
            String number=mauth.getCurrentUser().getPhoneNumber();
            number=number.substring(3,number.length());
            String uploadId= UUID.randomUUID().toString();
            String json=new JsonParser().viewOffer(url,number);
            return json;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dialog.dismiss();
            if(result!=null)
            {
                try{
                    JSONObject jsonObject = new JSONObject(result);
                    final String responce = String.valueOf(jsonObject.get("status"));
                    // final String responce2=String.valueOf(jsonObject.get("msg"));
                    if(responce.equals("1"))
                    {
                        JSONArray data=jsonObject.getJSONArray("data");
                        JSONObject obj=data.getJSONObject(0);
                        nameF=obj.getString("name");
                        String mobileF = obj.getString("mobile");
                        String heightF = obj.getString("height");
                        String weightF = obj.getString("weight");
                        String dobF = obj.getString("dob");
                        String bloodF = obj.getString("blood");
                        name.setText(nameF);
                    }
                    else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Update")
                                .setMessage("Some Error in fetching info please swipe to refresh")
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
                    catch (Exception e2)
                    {

                    }
                }
            }
            else
            {
                Toast.makeText(context,"Some Networking error try again later",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveImage(Bitmap finalBitmap, String image_name) {
        MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(),finalBitmap,image_name,"first save");
        Toast.makeText(context,"Image Saved",Toast.LENGTH_SHORT).show();
    }
}