package com.developer.headthapp.Covid;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.method.ScrollingMovementMethod;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.headthapp.ApiMethods.JsonParser;
import com.developer.headthapp.ApiMethods.networkData;
import com.developer.headthapp.ImageRecylerAdapter;
import com.developer.headthapp.Prescription.PrescriptionAdd;
import com.developer.headthapp.R;
import com.developer.headthapp.imageRecyclerClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import static androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL;

public class CovidMain extends AppCompatActivity {
    Button search_2,search_1;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int STORAGE_PERMISSION_CODE = 2;
    Context context;
    Button upload_1,upload_2,upload_c;
    ImageButton download_c,download_1,download_2;
    TextView recovery,shot1,shot2;
    String titleF,docF,observationF,dateF,imageF="",typeF,idF;
    boolean pdfChecker=false,imgCheck=false;
    ImageButton back;
    ProgressDialog progressDialog;
    FirebaseAuth mauth=FirebaseAuth.getInstance();
    boolean volChecker=false,shotChecker,recoverChecker;
    private boolean shot2Checker;
    public static String imagePaths="";
    private String recoveryurl="no data",shot1url="no data",shot2url="no data";
    private boolean rDialog=false,s1Dialog=false,s2Dialog=false;
    private String Name="";
    private boolean refresh=false,deleter=false,deleteR=false,deleteS1=false,deleteS2=false,deleteMsg=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_main);
        context=CovidMain.this;
        if(mauth.getCurrentUser()==null)
        {
            Toast.makeText(context,"you are not logged in to be here",Toast.LENGTH_SHORT).show();
            finish();
        }
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("Sending information");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        initialize();
        download_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(recoveryurl.equals("no data"))
                {
                Toast.makeText(context,"No Data to be removed",Toast.LENGTH_SHORT).show();
                }
                else{
                    Name=recoveryurl;
                    recoveryurl="no data";
                    deleter=true;
                    deleteR=true;
                    deleteMsg=true;
                    new removeData().execute();
                }
            }
        });
        download_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(shot1url.equals("no data"))
                {
                    Toast.makeText(context,"No Data to be removed",Toast.LENGTH_SHORT).show();
                }
                else{
                    Name=shot1url;
                    shot1url="no data";
                    deleter=true;
                    deleteS1=true;
                    deleteMsg=true;
                    new removeData().execute();
                }
            }
        });
        download_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(shot2url.equals("no data"))
                {
                    Toast.makeText(context,"No Data to be removed",Toast.LENGTH_SHORT).show();
                }
                else{
                    Name=shot2url;
                    shot2url="no data";
                    deleter=true;
                    deleteS2=true;
                    deleteMsg=true;
                    new removeData().execute();
                }
            }
        });
        upload_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rDialog=true;
                s1Dialog=false;
                s2Dialog=false;
                deleteMsg=false;
                dialogShower();
            }
        });
        upload_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rDialog=false;
                s1Dialog=true;
                s2Dialog=false;
                deleteMsg=false;
                dialogShower();
            }
        });
        upload_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rDialog=false;
                s1Dialog=false;
                s2Dialog=true;
                deleteMsg=false;
                dialogShower();
            }
        });
        search_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(recoveryurl.length()>10)){
//                    Toast.makeText(context,"Reaching here",Toast.LENGTH_SHORT).show();
                    String msg="You need to submit your recovery report in order to be a volunteer!!";
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Alert")
                            .setMessage(msg)
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    finish();
                                }
                            });
                    builder.create();
                    builder.show();
                }
                if(volChecker)
                {

                    String msg="Do you really want to unvolunteer yourself!!";
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Alert")
                            .setMessage(msg)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    finish();
                                    new updateVolunteer().execute();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                    builder.create();
                    builder.show();
                }
                else if(shot1url.length()>10||shot2url.length()>10)
                {
                    Toast.makeText(context,"Not eligible to become volunteer as you are vaccinated",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(recoveryurl.length()>10){
                    refresh=true;
                    dialog=new Dialog(context, 0);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.dialog_phone);
                    Button accept=dialog.findViewById(R.id.accept);
                    TextView content=(TextView)dialog.findViewById(R.id.content);
                    TextView title=(TextView)dialog.findViewById(R.id.title);
                    content.setText(R.string.guidelines);
                    content.setMovementMethod(new ScrollingMovementMethod());
                    title.setText("Guidelines");
                    accept.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent=new Intent(context,Volunteer.class);
                            startActivity(intent);
                            dialog.dismiss();
                        }
                    });
                    ImageButton close_btn2=dialog.findViewById(R.id.close_btn2);
                    close_btn2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
//                    return;
                }
            }
        });
        search_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,SearchHelp.class);
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        new getMyProfile().execute();
    }

    @Override
    protected void onResume() {
        if(refresh)
        {
            refresh=false;
            new getMyProfile().execute();
        }
        super.onResume();

    }

    private class updateVolunteer extends AsyncTask<String,String,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String url=new networkData().url+new networkData().volunteer;
            String number=mauth.getCurrentUser().getPhoneNumber();
            number=number.substring(3,number.length());
            String uploadId= UUID.randomUUID().toString();
            String json=new JsonParser().changeStatus(url,number,0);
            return json;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            if(s!=null){
                try{
                    JSONObject object=new JSONObject(s);
                    String status = String.valueOf(object.get("status"));
                    final String responce2=String.valueOf(object.get("msg"));
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    if(status.equals("1"))
                    {
                        builder.setTitle("Update")
                                .setMessage(responce2)
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
//                                        finish();
                                        new getMyProfile().execute();
                                    }
                                });
                    }
                    else {
                        builder.setTitle("Update")
                                .setMessage(responce2)
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                    }
                    builder.create();
                    builder.show();
                }catch (Exception e){

                }
            }
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
                dialog.dismiss();
                openFileChooser();
            }
        });
        pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                openPdfChooser();
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) ==
                            PackageManager.PERMISSION_GRANTED) {
                        Intent cam = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cam, 9);
                    } else {
                        ActivityCompat.requestPermissions(CovidMain.this, new
                                String[]{Manifest.permission.CAMERA}, 34);
                        Toast.makeText(context, "Click on allow and then choose the camera option again", Toast.LENGTH_SHORT).show();
                    }
            }
        });
        dialog.show();
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
            Toast.makeText(context, "Please Install a File Manager",Toast.LENGTH_SHORT).show();
        }
    }
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(intent, PICK_IMAGE_REQUEST);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        startActivityForResult(intent.createChooser(intent,"Select Picture"),PICK_IMAGE_REQUEST);
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
    String nameF;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Toast.makeText(context, "here", Toast.LENGTH_SHORT).show();
        if(resultCode==RESULT_OK&&requestCode==STORAGE_PERMISSION_CODE&&data!=null&&data.getData()!=null)
        {
//            Toast.makeText(context, "Uploaded Document", Toast.LENGTH_SHORT).show();
            Uri pdfUri = data.getData();
            Bitmap bitmap=null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), pdfUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            imageF=getStringPdf(pdfUri);
            imageF="data:application/pdf;base64,"+imageF;
//            image.setImageResource(R.drawable.ic_pdf);
            System.out.println("------------------"+imageF);
            typeF=".pdf";
            //keep this much code to make it dynamic
            String uploadId= UUID.randomUUID().toString();
            nameF=uploadId;
            pdfChecker=true;
            if(rDialog){
                if(recoveryurl.length()>10)
                {
//                    Toast.makeText(context,"Reching here",Toast.LENGTH_SHORT).show();
                    Name=recoveryurl;
                    recoveryurl="no data";
                    new removeData().execute();
                }
                else{
                    new uploadData().execute();
                }
            }
            else if(s1Dialog){
                if(shot1url.length()>10){
                    Name=shot1url;
                    shot1url="no data";
                    new removeData().execute();
                }
                else{
                    new uploadData().execute();
                }
                //shot1url=imagePaths;
            }
            else {
                if(shot2url.length()>10)
                {
//                    Toast.makeText(context,"Reching here",Toast.LENGTH_SHORT).show();
                    Name=shot2url;
                    shot2url="no data";
                    new removeData().execute();
                }
                else{
                    new uploadData().execute();
                }
                //shot2url=imagePaths;
            }
            //till here

        }
        if(requestCode==PICK_IMAGE_REQUEST&&resultCode==RESULT_OK)
        {
            assert data != null;
            if(data.getClipData()!=null) {
                imageF="";
                int count = data.getClipData().getItemCount();
                System.out.println("------------------------------Found multiple-----------------------");
                for (int i = 0; i < count; i++) {
                    try {
                        Uri imageuri2 = data.getClipData().getItemAt(i).getUri();
                        Bitmap bitmap2 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageuri2);
                        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                        bitmap2.compress(Bitmap.CompressFormat.JPEG,95,byteArrayOutputStream);
                        byte [] byteArray=byteArrayOutputStream.toByteArray();
                        String image = Base64.encodeToString(byteArray, Base64.NO_WRAP);
                        imageF += "data:image/jpeg;base64," + image+"HareKrishna";
                        typeF = ".jpeg";
//                        System.out.println("-------------------------------------" + imageF);
                        //keep this much code to make it dynamic
                        String uploadId = UUID.randomUUID().toString();
                        System.out.println("------------------------------DETECING-----------------------");
                        nameF += uploadId+"HareKrishna";
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(imageF.endsWith("HareKrishna"))
                {
                    imageF=imageF.substring(0,imageF.length()-11);
                    nameF=nameF.substring(0,nameF.length()-11);
                }
                imgCheck=true;
                if(rDialog){
                    if(recoveryurl.length()>10)
                    {
//                        Toast.makeText(context,"Reching here",Toast.LENGTH_SHORT).show();
                        Name=recoveryurl;
                        recoveryurl="no data";
                        new removeData().execute();
                    }
                    else{
                        new uploadData().execute();
                    }
                }
                else if(s1Dialog){
                    if(shot1url.length()>10){
                        Name=shot1url;
                        shot1url="no data";
                        new removeData().execute();
                    }
                    else{
                        new uploadData().execute();
                    }
                    //shot1url=imagePaths;
                }
                else {
                    if(shot2url.length()>10)
                    {
                        Name=shot2url;
                        shot2url="no data";
                        new removeData().execute();
                    }
                    else{
                        new uploadData().execute();
                    }
                    //shot2url=imagePaths;
                }
//                new uploadData().execute();
            }
            //path=getPath(imageuri);
            else if(data.getData() != null) {
                try {
                    Uri imageuri = data.getData();
//                    Toast.makeText(context, "image choosed", Toast.LENGTH_SHORT).show();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageuri);
                    //imageView_pic.setImageURI(imageuri);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 75, byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    imageF = Base64.encodeToString(byteArray, Base64.NO_WRAP);
                    imageF = "data:image/jpeg;base64," + imageF;
                    typeF = ".jpeg";
//                    System.out.println("-------------------------------------" + imageF);
                    //keep this much code to make it dynamic
                    String uploadId = UUID.randomUUID().toString();
                    nameF = uploadId;
                    imgCheck = true;
                    if(rDialog){
                        if(recoveryurl.length()>10)
                        {
//                            Toast.makeText(context,"Reching here",Toast.LENGTH_SHORT).show();
                            Name=recoveryurl;
                            recoveryurl="no data";
                            new removeData().execute();
                        }
                        else{
                            new uploadData().execute();
                        }
                    }
                    else if(s1Dialog){
                        if(shot1url.length()>10){
                            Name=shot1url;
                            shot1url="no data";
                            new removeData().execute();
                        }
                        else{
                            new uploadData().execute();
                        }
                        //shot1url=imagePaths;
                    }
                    else {
                        if(shot2url.length()>10)
                        {
                            Name=shot2url;
                            shot2url="no data";
                            new removeData().execute();
                        }
                        else{
                            new uploadData().execute();
                        }
                        //shot2url=imagePaths;
                    }
//                    new uploadData().execute();
                    //till here
                    //                Log.d("image ", "doInBackground: "+convertImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if(requestCode==9&&resultCode==RESULT_OK)
        {
            Uri imageuri=data.getData();
            //path=getPath(imageuri);
            try
            {
//                Toast.makeText(context,"image found",Toast.LENGTH_SHORT).show();
                Bitmap bitmap=(Bitmap)data.getExtras().get("data");
                //imageView_pic.setImageURI(imageuri);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,75,byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                imageF= Base64.encodeToString(byteArray,Base64.NO_WRAP);
                imageF="data:image/jpeg;base64,"+imageF;
                typeF=".jpeg";
                //keep this much code to make it dynamic
                String uploadId= UUID.randomUUID().toString();
                nameF=uploadId;
                imgCheck=true;
                if(rDialog){
                    if(recoveryurl.length()>10)
                    {
//                        Toast.makeText(context,"Reching here",Toast.LENGTH_SHORT).show();
                        Name=recoveryurl;
                        recoveryurl="no data";
                        new removeData().execute();
                    }
                    else{
                        new uploadData().execute();
                    }
                }
                else if(s1Dialog){
                    if(shot1url.length()>10){
                        Name=shot1url;
                        shot1url="no data";
                        new removeData().execute();
                    }
                    else{
                        new uploadData().execute();
                    }
                    //shot1url=imagePaths;
                }
                else {
                    if(shot2url.length()>10)
                    {
                        Name=shot2url;
                        shot2url="no data";
                        new removeData().execute();
                    }
                    else{
                        new uploadData().execute();
                    }
                    //shot2url=imagePaths;
                }
                //till here
//                System.out.println("-------------------------------------"+imageF);
                //                Log.d("image ", "doInBackground: "+convertImage);

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

        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(String... strings) {
            String url=new networkData().url+new networkData().addImage64;
            String imgF[]=imageF.split("HareKrishna");
            String nmF[]=nameF.split("HareKrishna");

            System.out.println("------------------------------Count is----"+imgF.length);
            for(int i=0;i<imgF.length;i++) {
                System.out.println("------------------------------INSERTING-----------------------");
                String data = new JsonParser().addImage(url, imgF[i], nmF[i], typeF);
                try {
                    JSONObject obj = new JSONObject(data);
                    String status = String.valueOf(obj.get("status"));
                    if (status.equals("1")) {
                        String fileName = String.valueOf(obj.get("name"));
                        String msg = String.valueOf(obj.get("msg"));
                        imagePaths += fileName + ";";
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(rDialog){

                    recoveryurl=imagePaths;
                }
                else if(s1Dialog){
                    shot1url=imagePaths;
                }
                else {
                    shot2url=imagePaths;
                }
            }
            return "data";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            if(s!=null)
            {
                try{
//                    JSONObject obj=new JSONObject(s);
                    String status="1";
//                    String status=String.valueOf(obj.get("status"));
                    if (1==1||status.equals("1"))
                    {
//                        String fileName=String.valueOf(obj.get("name"));
//                        String msg=String.valueOf(obj.get("msg"));
//                        imagePaths+=fileName+";";
//                        list.add(new imageRecyclerClass(imageF,fileName,typeF));
//                        adapter=new ImageRecylerAdapter(list,context);

//                        Toast.makeText(context,"Image Inserted",Toast.LENGTH_SHORT).show();
                        new submitData().execute();
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
    public class submitData extends AsyncTask<String,String,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            new networkData();
            String base= networkData.url;
            String method=networkData.submitData;
            String url=base+method;
            String number=mauth.getCurrentUser().getPhoneNumber();
            number=number.substring(3,number.length());
            String uploadId= UUID.randomUUID().toString();
            String json=null;
            if(rDialog){
                json=new JsonParser().submitCovidData(url,number,recoveryurl,"certificate");
//                recoveryurl=imagePaths;
            }
            else if(s1Dialog){
                json=new JsonParser().submitCovidData(url,number,shot1url,"shot1");
//                shot1url=imagePaths;
            }
            else if(deleter)
            {
                deleter=false;
                if(deleteR)
                {
                    deleteR=false;
                    json=new JsonParser().submitCovidData(url,number,recoveryurl,"certificate");
                }
                else if(deleteS1)
                {
                    deleteS1=false;
                    json=new JsonParser().submitCovidData(url,number,shot1url,"shot1");
                }
                else if(deleteS2)
                {
                    deleteS2=false;
                    json=new JsonParser().submitCovidData(url,number,shot2url,"shot2");
                }
            }
            else if(s2Dialog){
                json=new JsonParser().submitCovidData(url,number,shot2url,"shot2");
//                shot2url=imagePaths;
            }
            return json;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            System.out.println(s);
            if(s!=null){
                try{
                    JSONObject obj=new JSONObject(s);
                    String status=String.valueOf(obj.get("status"));
                    if(status.equals("1")) {
                        if((s1Dialog||s2Dialog)&&volChecker)
                        {
                            Toast.makeText(context,"You cannot be a volunteer anymore",Toast.LENGTH_SHORT).show();
                            new updateVolunteer().execute();
                        }
                        if(recoveryurl.equals("no data")&&volChecker)
                        {
                            Toast.makeText(context,"You cannot be a volunteer anymore",Toast.LENGTH_SHORT).show();
                            new updateVolunteer().execute();
                        }
                        String msg = String.valueOf(obj.get("msg"));
                        if(deleteMsg&&msg.contains("certificate"))
                        {
                            msg="Certificate has been removed";
                        }else if(deleteMsg&&msg.contains("shot1")){
                            msg="Ist Shot certificate has been removed";
                        }else if(deleteMsg&&msg.contains("shot2")){
                            msg="IInd Shot certificate has been removed";
                        }
                        deleteMsg=false;
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Update")
                                .setMessage(msg)
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
//                                    finish();
                                        new getMyProfile().execute();
                                    }
                                });
                        builder.create();
                        builder.show();
                    }
                    else{
                        final String msg = String.valueOf(obj.get("msg"));
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Update")
                                .setMessage(msg)
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
//                                    finish();
                                        new getMyProfile().execute();
                                    }
                                });
                        builder.create();
                        builder.show();
                    }
                }catch (Exception e){

                    final String msg = "Some trouble has occurred please try again after sometime";
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Update")
                            .setMessage(msg)
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    finish();
//                                    new getMyProfile().execute();
                                }
                            });
                    builder.create();
                    builder.show();
                }
            }else {
                Toast.makeText(context,"Please Check your Internet connection",Toast.LENGTH_SHORT).show();
            }
        }
    }
    public class removeData extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            setProgress();
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String url=new networkData().url+new networkData().deleteData;
            String arr[]=Name.split(";");
            String data=null;
            for(int i=0;i<arr.length;i++)
            {
                if(arr[i].length()>10)
                {
//                    if(arr[i].endsWith())
                    System.out.println("Getting here");
                    data=new JsonParser().deleteImage(url,arr[i]);
                }
            }
            System.out.println(data);
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            System.out.println(s);
            progressDialog.dismiss();
            if(s!=null)
            {
                try{
                    JSONObject obj=new JSONObject(s);
                    String status=String.valueOf(obj.get("status"));
                    if(status.equals("1"))
                    {
                        Toast.makeText(context,"Previous Data Deleted",Toast.LENGTH_SHORT).show();
                        if(deleter){
//                            deleter=false;
                            new submitData().execute();
                        }
                        else{
                            new uploadData().execute();
                        }
                    }else{
                        if(deleter){
//                            deleter=false;
                            new submitData().execute();
                        }
                        else{
                            new uploadData().execute();
                        }
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            else {
                Toast.makeText(context,"Please Check your Internet connection",Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void initialize(){
        search_1=(Button)findViewById(R.id.search_1);
        search_2=(Button)findViewById(R.id.search_2);
        back=(ImageButton)findViewById(R.id.back);
        upload_1=(Button)findViewById(R.id.upload_1);
        upload_2=(Button)findViewById(R.id.upload_2);
        upload_c=(Button)findViewById(R.id.upload_c);
        download_c=(ImageButton)findViewById(R.id.download_c);
        download_1=(ImageButton)findViewById(R.id.download_1);
        download_2=(ImageButton)findViewById(R.id.download_2);
        recovery=(TextView)findViewById(R.id.recovery);
        shot1=(TextView)findViewById(R.id.shot1);
        shot2=(TextView)findViewById(R.id.shot2);
    }
    public class getMyProfile extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            new networkData();
            String base= networkData.url;
            String method=networkData.getCovidProfile;
            String url=base+method;
            String number=mauth.getCurrentUser().getPhoneNumber();
            number=number.substring(3,number.length());
            String uploadId= UUID.randomUUID().toString();
            String json=new JsonParser().viewOffer(url,number);
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
                    if(responce.equals("1"))
                    {
                        JSONArray arr=jsonObject.getJSONArray("data");
                        JSONObject obj=arr.getJSONObject(0);
                        String recoveryD = obj.getString("recovery");
                        recoveryurl=recoveryD;
                        String shot1D = obj.getString("shot1");
                        shot1url=shot1D;
                        String shot2D=obj.getString("shot2");
                        shot2url=shot2D;
                        String volunteerD = String.valueOf(obj.get("volunter"));
                        if(shot1D.equals("no data"))
                        {
//                            Toast.makeText(context,recoveryurl,Toast.LENGTH_SHORT).show();
                            upload_1.setText("Upload");
                            shot1.setText("1st Shot");
                            shotChecker=false;
                        }else{
                            upload_1.setText("Reupload");
                            shot1.setText("1st Shot (Uploaded)");
                            shotChecker=true;
                        }
//                        Toast.makeText(context,shot2D,Toast.LENGTH_SHORT).show();
                        if(shot2D.equals("no data"))
                        {
                            upload_2.setText("Upload");
                            shot2.setText("2nd Shot");
                            shot2Checker=false;
                        }else{
                            upload_2.setText("Reupload");
                            shot2.setText("2nd Shot (Uploaded)");
                            shot2Checker=true;
                        }
                        if(recoveryD.equals("no data"))
                        {
                            recoverChecker=false;
                            recovery.setText("Recovery Certificate \n (Or latest negative RT-PCR report)");
                            upload_c.setText("Upload");
                        }
                        else{
                            recoverChecker=true;
                            upload_c.setText("Reupload");
                            recovery.setText("Recovery Certificate \n (Or latest negative RT-PCR report) (Uploaded)");
                        }
                        if(volunteerD.equals("0")){
                            search_2.setEnabled(true);
                            volChecker=false;
                            search_2.setText("Volunteer for Plasma Donation");
                        }
                        else {
                            volChecker=true;
                            search_2.setEnabled(true);
                            search_2.setText("Unvolunteer for Plasma Donation");
                        }
                    }
                    else {
                        final String msg=String.valueOf(jsonObject.get("msg"));
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Update")
                                .setMessage(msg)
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        finish();
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
                                        finish();
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