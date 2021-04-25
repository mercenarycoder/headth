package com.developer.headthapp.Qr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.headthapp.ApiMethods.JsonParser;
import com.developer.headthapp.ApiMethods.networkData;
import com.developer.headthapp.Profile;
import com.developer.headthapp.R;
import com.developer.headthapp.serviceOtp.MyService;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.UUID;

public class QrScanner extends AppCompatActivity {
    ImageButton close_btn;
    TextView next;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    String intentData = "";
    SurfaceView surfaceView;
    FirebaseAuth mauth = FirebaseAuth.getInstance();
    TextView scanner;
    Button open_profile;
    Context context;
    ProgressDialog progressDialog;
    String numberF = "", accessF = "";
    private FusedLocationProviderClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = QrScanner.this;
        setContentView(R.layout.activity_qr_scanner);

        open_profile=(Button)findViewById(R.id.open_profile);
        open_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(QrScanner.this, Profile.class);
                startActivity(intent);
                finish();
            }
        });
        close_btn=(ImageButton)findViewById(R.id.close_btn);
        next=(TextView)findViewById(R.id.next);
        surfaceView=(SurfaceView)findViewById(R.id.surfaceView);
        scanner=(TextView)findViewById(R.id.scanner);
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(QrScanner.this,QRotp.class);
            //    startActivity(intent);
            }
        });
        new getAccessLevel().execute();
        new getProfile().execute();
    }
    private class getProfile extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

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
//            dialog.dismiss();
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
                        String nameF=obj.getString("name");
                        String mobileF = obj.getString("mobile");
                        String heightF = obj.getString("height");
                        String weightF = obj.getString("weight");
                        String dobF = obj.getString("dob");
                        String bloodF = obj.getString("blood");
                        open_profile.setText(nameF.substring(0,1).toUpperCase());
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
    private void initialiseDetectorsAndSources() {
        Toast.makeText(getApplicationContext(), "Barcode scanner started", Toast.LENGTH_SHORT).show();
        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true) //you should add this feature
                .build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(QrScanner.this, Manifest.permission.CAMERA) ==
                            PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(QrScanner.this, new
                                String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });


        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
                Toast.makeText(getApplicationContext(), "To prevent memory leaks barcode scanner has been stopped", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {
                    //Toast.makeText(QrScanner.this,barcodes.valueAt(0).displayValue,Toast.LENGTH_SHORT).show();
                scanner.post(new Runnable() {
                    @Override
                    public void run() {
                     //scanner.setText(barcodes.valueAt(0).displayValue);
                        client = LocationServices.getFusedLocationProviderClient(context);
                        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) !=
                                PackageManager.PERMISSION_GRANTED &&
                                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                                        PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(context,"Location must be enabled for using this feature",Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                        else{
                            client.getLastLocation().addOnSuccessListener(QrScanner.this, new OnSuccessListener<Location>() {
                                @Override
                                public void onSuccess(Location o) {
                                    if(o!=null||1==1)
                                    {
                                        Toast.makeText(context,String.valueOf(o),Toast.LENGTH_SHORT).show();
//                                    System.out.println(o.getLatitude()+" "+o.getLongitude());
                                        barcodeDetector.release();
                                        numberF=barcodes.valueAt(0).displayValue;
                                        Toast.makeText(context,numberF,Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(context,QRprofile.class);
                                        intent.putExtra("mobile",numberF);
                                        intent.putExtra("access",accessF);
//                                        MyService ss=new MyService(context);
//                                        startService(new Intent(getApplicationContext(), ss.getClass()));
                                        if(o!=null)
                                        {
                                            intent.putExtra("longitude",String.valueOf(o.getLongitude()));
                                            intent.putExtra("latitude",String.valueOf(o.getLatitude()));
                                        }
                                        else {
                                            intent.putExtra("longitude", "23.33");
                                            intent.putExtra("latitude", "33.33");
                                        }
                                        startActivity(intent);
                                    }
                                    else
                                    {
                                        Toast.makeText(context,String.valueOf(o),Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                });
                }
            }
        });
    }
    public class getAccessLevel extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(context);
            progressDialog.setMessage("Sending information");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            new networkData();
            String base= networkData.url;
            String method=networkData.getLevel;
            String url=base+method;
            String number=mauth.getCurrentUser().getPhoneNumber();
            number=number.substring(3,number.length());
            String uploadId= UUID.randomUUID().toString();

            String json=new JsonParser().viewOffer(url,number);
            return json;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            if(s!=null)
            {
                try{
                    JSONObject object=new JSONObject(s);
                    String status = object.getString("status");
                    String accessLevel = object.getString("accessLevel");
                    if(status.equals("1"))
                    {
                        accessF=accessLevel;
                    }
                    else
                    {
                        accessF=accessLevel;
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Update")
                            .setMessage("Some Error in fetching info please try again later..")
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                    builder.create();
                    builder.show();
                }
            }
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        cameraSource.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initialiseDetectorsAndSources();
    }
}