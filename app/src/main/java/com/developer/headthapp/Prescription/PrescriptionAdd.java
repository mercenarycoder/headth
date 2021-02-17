package com.developer.headthapp.Prescription;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.headthapp.ApiMethods.JsonParser;
import com.developer.headthapp.ApiMethods.networkData;
//import com.developer.headthapp.ImageRecyclerAdapter;
import com.developer.headthapp.ImageRecylerAdapter;
import com.developer.headthapp.Nominations;
import com.developer.headthapp.ProfileUpdate;
import com.developer.headthapp.Prescription.PrescriptionAdd;
import com.developer.headthapp.R;
import com.developer.headthapp.Report.ReportAdd;
import com.developer.headthapp.imageRecyclerClass;
import com.google.firebase.auth.FirebaseAuth;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import static androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL;

public class PrescriptionAdd extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int STORAGE_PERMISSION_CODE = 2;
    ImageButton back;
    EditText title,doc_name,observation;
    Button choose,upload;
    TextView date;
    Context context;
    Bitmap img=null;
    FirebaseAuth mauth;
    Calendar myCalendar;
    String titleF,docF,observationF,dateF,imageF="",typeF,idF;
    ProgressDialog progressDialog;
    //new multiple image adder code from here
    RecyclerView recycler;
    ArrayList<imageRecyclerClass> list=new ArrayList<>();
    ImageRecylerAdapter adapter;
    String mode="add";
    boolean pdfChecker=false,imgCheck=false;
    public static String imagePaths="";

    private void initRetrofitClient() {

        new networkData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_add);
        context=PrescriptionAdd.this;
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("Sending information");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        initiaLize();
        requestStoragePermission();
        mauth=FirebaseAuth.getInstance();
        back=(ImageButton)findViewById(R.id.back);
        initRetrofitClient();
        date=(TextView)findViewById(R.id.date);
        recycler=(RecyclerView)findViewById(R.id.recycler);
        //from here the edit code is begining to make edit option work
        mode=getIntent().getStringExtra("mode");
        titleF=getIntent().getStringExtra("title");
        dateF=getIntent().getStringExtra("date");
        observationF=getIntent().getStringExtra("observation");
        typeF=getIntent().getStringExtra("type");
        imagePaths=getIntent().getStringExtra("image");
        idF=getIntent().getStringExtra("id");
        docF=getIntent().getStringExtra("doctor");
        title.setText(titleF);
        date.setText(dateF);
        observation.setText(observationF);
        doc_name.setText(docF);
        if(mode!=null && mode.equals("edit"))
        {
            String fileName[]=imagePaths.split(";");
            for(int i=0;i<fileName.length;i++)
            {
                list.add(new imageRecyclerClass("",fileName[i],typeF));
            }
            adapter=new ImageRecylerAdapter(list,context);
            recycler.setLayoutManager(new LinearLayoutManager(context, HORIZONTAL,false));
            recycler.setHasFixedSize(true);
            recycler.setAdapter(adapter);
        }
        //keep this much code to make it dynamic
        adapter=new ImageRecylerAdapter(list,context);
        recycler.setLayoutManager(new LinearLayoutManager(context, HORIZONTAL,false));
        recycler.setHasFixedSize(true);
        recycler.setAdapter(adapter);
        //till here
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
                        String myFormat = "dd-MM-YYYY"; //In which you need put here
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
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              dialogShower();
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               titleF=title.getText().toString();
               dateF=date.getText().toString();
               String mod[]=dateF.split("-");
               dateF=mod[2]+"-"+mod[1]+"-"+mod[0];
               docF=doc_name.getText().toString();
               observationF=observation.getText().toString();
               if(list.size()<=0)
               {
                   Toast.makeText(context,"Choose atleast a image and try again",Toast.LENGTH_SHORT).show();
                   return;
               }
               else
               {
                   imagePaths="";
                   for(int i=0;i<list.size();i++)
                   {
                       imageRecyclerClass item=list.get(i);
                       imagePaths+=item.getId()+";";
                   }
                   Toast.makeText(context,imagePaths,Toast.LENGTH_SHORT).show();
                   if(titleF.isEmpty()||dateF.isEmpty()||docF.isEmpty())
                   {
                       Toast.makeText(context,"Please fill all the fields",Toast.LENGTH_SHORT).show();
                       return;
                   }
                   else
                   {
                    if(observationF.isEmpty())
                    {
                        observationF="Its a prescription";
                    }
                       //multipartImageUpload();
                       new uploadPres().execute();
                   }
               }
            }
        });
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
                if(!pdfChecker) {
                    openFileChooser();
                }
                else
                {

                }
            }
        });
        pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if(!imgCheck) {
                    openPdfChooser();
                }
                else
                {

                }
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
               if(!pdfChecker) {
                   if (ActivityCompat.checkSelfPermission(PrescriptionAdd.this, Manifest.permission.CAMERA) ==
                           PackageManager.PERMISSION_GRANTED) {
                       Intent cam = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                       startActivityForResult(cam, 9);
                   } else {
                       ActivityCompat.requestPermissions(PrescriptionAdd.this, new
                               String[]{Manifest.permission.CAMERA}, 34);
                       Toast.makeText(PrescriptionAdd.this, "Click on allow and then choose the camera option again", Toast.LENGTH_SHORT).show();
                   }
               }
               else
               {

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
            Toast.makeText(PrescriptionAdd.this, "Please Install a File Manager",Toast.LENGTH_SHORT).show();
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
    String nameF;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Toast.makeText(context, "here", Toast.LENGTH_SHORT).show();
        if(resultCode==RESULT_OK&&requestCode==STORAGE_PERMISSION_CODE&&data!=null&&data.getData()!=null)
        {
            Toast.makeText(context, "Uploaded Document", Toast.LENGTH_SHORT).show();
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
            new uploadData().execute();
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
                new uploadData().execute();
            }
            //path=getPath(imageuri);
           else if(data.getData() != null) {
                try {
                    Uri imageuri = data.getData();
                    Toast.makeText(context, "image choosed", Toast.LENGTH_SHORT).show();
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
                    new uploadData().execute();
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
                Toast.makeText(context,"image found",Toast.LENGTH_SHORT).show();
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
                new uploadData().execute();

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
                        list.add(new imageRecyclerClass(imgF[i], fileName, typeF));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            adapter=new ImageRecylerAdapter(list,context);
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
                        recycler.setLayoutManager(new LinearLayoutManager(context, HORIZONTAL,false));
                        recycler.setHasFixedSize(true);
                        recycler.setAdapter(adapter);
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

    public void initiaLize()
    {
        title=(EditText)findViewById(R.id.title);
        observation=(EditText)findViewById(R.id.observation);
        doc_name=(EditText)findViewById(R.id.doc_name);
        choose=(Button)findViewById(R.id.choose);
        upload=(Button)findViewById(R.id.upload);
//        image=(ImageView)findViewById(R.id.image);
    }

    private void openCameraIntent() {
        Intent pictureIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        if(pictureIntent.resolveActivity(context.getPackageManager()) != null){
            //Create a file to store the image
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(context.getApplicationContext(), context.getPackageName()
                        +".fileprovider", photoFile);
                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(pictureIntent, 1);
            }
        }
    }
    String imageFilePath;
    private File createImageFile() throws IOException {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir =
                PrescriptionAdd.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        imageFilePath = image.getAbsolutePath();
        return image;
    }
    private void selectImage() {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.
                app.AlertDialog.Builder(context,R.style.AlertDialogCustom);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    check_permissions();
                    requestWritePermission();
                    openCameraIntent();
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    requestStoragePermission();
                    requestWritePermission();
                        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, 2);

                }
                else if (options[item].equals("Cancel")) {

                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    //Requesting permission
    public boolean check_permissions() {

        String[] PERMISSIONS = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
        };
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)==PackageManager.PERMISSION_GRANTED)
        {
            return true;
        }
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CAMERA))
        {

        }
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},12);
        return false;
    }
    private boolean requestWritePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED)
            return true;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                13);
        return false;
    }
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED)
         return ;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                STORAGE_PERMISSION_CODE);
    }


    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
        if(requestCode==12)
        {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can take images", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
        if(requestCode==13)
        {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can write images", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }
public class uploadPres extends AsyncTask<String,String,String>
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
        String method=networkData.precription;
        String url=base+method;
        String number=mauth.getCurrentUser().getPhoneNumber();
        number=number.substring(3,number.length());
        String uploadId= UUID.randomUUID().toString();
        String json;
        if(mode!=null&&mode.equals("edit"))
        {
            method=networkData.updatePrescription;
            url=base+method;
            json=new JsonParser().updatePrescription(url,idF,number,titleF,docF,imagePaths,observationF,dateF);
        }
        else {
            json = new JsonParser().saveCategory(url, number, titleF, dateF, imagePaths, docF, observationF);
        }
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