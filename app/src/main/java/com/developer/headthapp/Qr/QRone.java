package com.developer.headthapp.Qr;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGSaver;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.developer.headthapp.R;
import com.google.zxing.WriterException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class QRone extends AppCompatActivity {
ImageButton close_btn;
Button scanner;
ImageView qr_logo;
Button share ,save;
String savePath = Environment.getExternalStorageDirectory().getPath() + "/QRCode/";
Bitmap bitmap;
Context context;
Uri for_share;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=QRone.this;
        setContentView(R.layout.activity_q_rone);
        qr_logo=(ImageView)findViewById(R.id.qr_logo);
        share=(Button)findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareImageUri(for_share);
            }
        });
        save=(Button)findViewById(R.id.save);
        QRGEncoder qrgEncoder = new QRGEncoder("7389438159", null, QRGContents.Type.TEXT, 400);
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
                       //save = QRGSaver.save(savePath, "sjkbfk", bitmap, QRGContents.ImageType.IMAGE_JPEG);
                       for_share = saveImageExternal(bitmap);
                       result=for_share.toString();
                       Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
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
    }
    private void shareImageUri(Uri uri){
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setType("image/png");
        startActivity(intent);
    }
    private Uri saveImageExternal(Bitmap image) {
        //TODO - Should be processed in another thread
        Uri uri = null;
        try {
            File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "to-share.png");
            FileOutputStream stream = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.PNG, 90, stream);
            stream.close();
            uri = Uri.fromFile(file);
        } catch (IOException e) {
            Log.d("TAG", "IOException while trying to write file for sharing: " + e.getMessage());
        }
        return uri;
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
}