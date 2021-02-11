package com.developer.headthapp;

import android.app.AlertDialog;
import android.app.AppComponentFactory;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.developer.headthapp.ApiMethods.JsonParser;
import com.developer.headthapp.ApiMethods.networkData;
import com.developer.headthapp.FragmentMains.FragmentHistiry;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.core.content.FileProvider;

import android.app.Dialog;

public class EmergencyContacts extends AppCompatActivity {
    EditText name1,name2,name3,name4,name5;
    EditText phone1,phone2,phone3,phone4,phone5;
    Button verify;
    ImageButton edit1,edit2,edit3,edit4,edit5;
    ImageButton delete1,delete2,delete3,delete4,delete5;
    ProgressDialog dialog;
    ArrayList<emergencyClass> list;
    Context context;
    FirebaseAuth mauth=FirebaseAuth.getInstance();
    String edit="false";
    String numbers[];
    int z=0,index=0;
    String rec_id1="null",rec_id2="null",rec_id3="null",rec_id4="null",rec_id5="null";
    String Rec_Id="",Phone="",Name="";
    SharedPreferences checker;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=EmergencyContacts.this;
        checker=getSharedPreferences("basicinfo",Context.MODE_PRIVATE);
        editor=checker.edit();

        setContentView(R.layout.activity_nominations);
        dialog=new ProgressDialog(context);
        dialog.setMessage("Please wait");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCanceledOnTouchOutside(false);
        initialize();
        edit=getIntent().getStringExtra("edit");
        numbers= new String[]{"null", "null", "null", "null", "null"};
        if(edit.equals("true"))
        {
            new getEmergency().execute();
        }
        else
        {

        }
        edit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             dialogShower();
             index=1;
            }
        });
        edit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            dialogShower();
            index=2;
            }
        });
        edit3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            dialogShower();
            index=3;
            }
        });
        edit4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            dialogShower();
            index=4;
            }
        });
        edit5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            dialogShower();
            index=5;
            }
        });
        delete1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContextThemeWrapper ctw = new ContextThemeWrapper( context, R.style.AppTheme);
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctw);
                alertDialogBuilder.setTitle("Delete Operation");
                alertDialogBuilder.setMessage("Are you sure you want to delete this contact?");
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Rec_Id=rec_id1;
                        index=1;
                       if(!rec_id1.equals("null"))
                       new deleteEmergency().execute();
                       else
                           Toast.makeText(context,"No contact their to be deleted",Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialogBuilder.setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alertDialogBuilder.show();
            }
        });
        delete2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ContextThemeWrapper ctw = new ContextThemeWrapper( context, R.style.AppTheme);
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctw);
                alertDialogBuilder.setTitle("Delete Operation");
                alertDialogBuilder.setMessage("Are you sure you want to delete this contact?");
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Rec_Id=rec_id2;
                        index=2;
                        if(!rec_id2.equals("null"))
                            new deleteEmergency().execute();
                        else
                            Toast.makeText(context,"No contact their to be deleted",Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialogBuilder.setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alertDialogBuilder.show();
            }
        });
        delete3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ContextThemeWrapper ctw = new ContextThemeWrapper( context, R.style.AppTheme);
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctw);
                alertDialogBuilder.setTitle("Delete Operation");
                alertDialogBuilder.setMessage("Are you sure you want to delete this contact?");
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Rec_Id=rec_id3;
                        index=3;
                        if(!rec_id3.equals("null"))
                            new deleteEmergency().execute();
                        else
                            Toast.makeText(context,"No contact their to be deleted",Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialogBuilder.setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alertDialogBuilder.show();
            }
        });
        delete4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ContextThemeWrapper ctw = new ContextThemeWrapper( context, R.style.AppTheme);
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctw);
                alertDialogBuilder.setTitle("Delete Operation");
                alertDialogBuilder.setMessage("Are you sure you want to delete this contact?");
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Rec_Id=rec_id4;
                        index=4;
                        if(!rec_id4.equals("null"))
                            new deleteEmergency().execute();
                        else
                            Toast.makeText(context,"No contact their to be deleted",Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialogBuilder.setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alertDialogBuilder.show();
            }
        });
        delete5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ContextThemeWrapper ctw = new ContextThemeWrapper( context, R.style.AppTheme);
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctw);
                alertDialogBuilder.setTitle("Delete Operation");
                alertDialogBuilder.setMessage("Are you sure you want to delete this contact?");
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Rec_Id=rec_id5;
                        index=5;
                        if(!rec_id5.equals("null"))
                            new deleteEmergency().execute();
                        else
                            Toast.makeText(context,"No contact their to be deleted",Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialogBuilder.setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alertDialogBuilder.show();
            }
        });
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,String.valueOf(z),Toast.LENGTH_SHORT).show();
                if(edit.equals("true"))
                {
                    if(z<=0)
                    {
                       // z=0;
                        Toast.makeText(context,"Atleast One Contact Should be their",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        finish();
                    }
                }
                else
                {
                    if(z<=0)
                    {
                        //z=0;
                        Toast.makeText(context,"Atleast One Contact Should be their",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        editor.putString("emergency","done");
                        editor.apply();
                        editor.commit();
                        Intent intent = new Intent(context, HealthCart.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
    }

    public boolean checkDuplicateContact(String phone)
    {
        Iterator<emergencyClass> item=list.iterator();
        while (item.hasNext())
        {
            emergencyClass node=item.next();
            if(node.getPhone().equals(phone))
            {
                return true;
            }
        }
        return false;
    }
    Dialog dialog2;
    public void dialogShower()
    {
        dialog2=new Dialog(context, 0);
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog2.setCancelable(false);
        dialog2.setContentView(R.layout.dialog_contact_chooser);
        ImageButton close_btn2=(ImageButton)dialog2.findViewById(R.id.close_btn2);
        Button choose=(Button)dialog2.findViewById(R.id.choose);
        Button manual=(Button)dialog2.findViewById(R.id.manual);

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Intent.ACTION_PICK);
                i.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                startActivityForResult(i, 100);
                dialog2.dismiss();
            }
        });
        manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            dialog2.dismiss();
            dialogManual();
            }
        });
        close_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog2.dismiss();
            }
        });
        dialog2.show();
    }
    Dialog dialogManual;
    public void dialogManual()
    {
        dialogManual=new Dialog(context,0);
        dialogManual.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogManual.setCancelable(false);
        dialogManual.setContentView(R.layout.dialog_manual_contact);
        ImageButton close_btn2=(ImageButton)dialogManual.findViewById(R.id.close_btn2);
        close_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogManual.dismiss();
            }
        });
        final EditText name=(EditText)dialogManual.findViewById(R.id.name);
        final EditText phone=(EditText)dialogManual.findViewById(R.id.phone);
        TextInputLayout height1=(TextInputLayout)dialogManual.findViewById(R.id.height1);
        TextInputLayout height2=(TextInputLayout)dialogManual.findViewById(R.id.height2);
        Button submit=(Button)dialogManual.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameG=name.getText().toString().trim();
                String phoneG = phone.getText().toString().trim();
                if(nameG.length()>0)
                {
                    if(phoneG.length()==10)
                    {
                        switch (index)
                        {
                            case 1:
                                if(checkDuplicateContact(phoneG))
                                {
                                    ContextThemeWrapper ctw = new ContextThemeWrapper( context, R.style.AppTheme);
                                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctw);
                                    alertDialogBuilder.setTitle("Duplicate Phone Number");
                                    alertDialogBuilder.setMessage("Duplicate phone number detected phone number should be different");
                                    alertDialogBuilder.setCancelable(false);
                                    alertDialogBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                        }
                                    });
                                    alertDialogBuilder.show();
                                    Toast.makeText(context,"Duplicate phone number detected phone number should be unique",Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    name1.setText(nameG);
                                    phone1.setText(phoneG);
                                    if (!rec_id1.equals("null")) {
                                        Name = nameG;
                                        Phone = phoneG;
                                        Rec_Id = rec_id1;
                                        new updateEmergency().execute();
                                    } else {
                                        Name = nameG;
                                        Phone = phoneG;
                                        new addEmergency().execute();
                                    }
                                }
                                dialogManual.dismiss();
                                break;
                            case 2:
                                if(checkDuplicateContact(phoneG))
                                {
                                    ContextThemeWrapper ctw = new ContextThemeWrapper( context, R.style.AppTheme);
                                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctw);
                                    alertDialogBuilder.setTitle("Duplicate Phone Number");
                                    alertDialogBuilder.setMessage("Duplicate phone number detected phone number should be different");
                                    alertDialogBuilder.setCancelable(false);
                                    alertDialogBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                        }
                                    });
                                    alertDialogBuilder.show();
                                    Toast.makeText(context,"Duplicate phone number detected phone number should be unique",Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    name2.setText(nameG);
                                    phone2.setText(phoneG);
                                    if (!rec_id2.equals("null")) {
                                        Name = nameG;
                                        Phone = phoneG;
                                        Rec_Id = rec_id2;
                                        new updateEmergency().execute();
                                    } else {
                                        Name = nameG;
                                        Phone = phoneG;
                                        new addEmergency().execute();
                                    }
                                }
                                dialogManual.dismiss();
                                break;
                            case 3:
                                if(checkDuplicateContact(phoneG))
                                {
                                    ContextThemeWrapper ctw = new ContextThemeWrapper( context, R.style.AppTheme);
                                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctw);
                                    alertDialogBuilder.setTitle("Duplicate Phone Number");
                                    alertDialogBuilder.setMessage("Duplicate phone number detected phone number should be different");
                                    alertDialogBuilder.setCancelable(false);
                                    alertDialogBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                        }
                                    });
                                    alertDialogBuilder.show();
                                    Toast.makeText(context,"Duplicate phone number detected phone number should be unique",Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    name3.setText(nameG);
                                    phone3.setText(phoneG);
                                    if (!rec_id3.equals("null")) {
                                        Name = nameG;
                                        Phone = phoneG;
                                        Rec_Id = rec_id3;
                                        new updateEmergency().execute();
                                    } else {
                                        Name = nameG;
                                        Phone = phoneG;
                                        new addEmergency().execute();
                                    }
                                }
                                dialogManual.dismiss();
                                break;
                            case 4:
                                if(checkDuplicateContact(phoneG))
                                {
                                    ContextThemeWrapper ctw = new ContextThemeWrapper( context, R.style.AppTheme);
                                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctw);
                                    alertDialogBuilder.setTitle("Duplicate Phone Number");
                                    alertDialogBuilder.setMessage("Duplicate phone number detected phone number should be different");
                                    alertDialogBuilder.setCancelable(false);
                                    alertDialogBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                        }
                                    });
                                    alertDialogBuilder.show();
                                    Toast.makeText(context,"Duplicate phone number detected phone number should be unique",Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    name4.setText(nameG);
                                    phone4.setText(phoneG);
                                    if (!rec_id4.equals("null")) {
                                        Name = nameG;
                                        Phone = phoneG;
                                        Rec_Id = rec_id4;
                                        new updateEmergency().execute();
                                    } else {
                                        Name = nameG;
                                        Phone = phoneG;
                                        new addEmergency().execute();
                                    }
                                }
                                dialogManual.dismiss();
                                break;
                            case 5:
                                if(checkDuplicateContact(phoneG))
                                {
                                    ContextThemeWrapper ctw = new ContextThemeWrapper( context, R.style.AppTheme);
                                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctw);
                                    alertDialogBuilder.setTitle("Duplicate Phone Number");
                                    alertDialogBuilder.setMessage("Duplicate phone number detected phone number should be different");
                                    alertDialogBuilder.setCancelable(false);
                                    alertDialogBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                        }
                                    });
                                    alertDialogBuilder.show();
                                    Toast.makeText(context,"Duplicate phone number detected phone number should be unique",Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    name5.setText(nameG);
                                    phone5.setText(phoneG);
                                    if (!rec_id5.equals("null")) {
                                        Name = nameG;
                                        Phone = phoneG;
                                        Rec_Id = rec_id5;
                                        new updateEmergency().execute();
                                    } else {
                                        Name = nameG;
                                        Phone = phoneG;
                                        new addEmergency().execute();
                                    }
                                }
                                dialogManual.dismiss();
                                break;
                        }
                    }
                    else{
                        Toast.makeText(context,"Invalid Phone number",Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(context,"Name should be atleast 1 character long",Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialogManual.show();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100&&resultCode==RESULT_OK)
        {
            Cursor cursor=null;
            try{
                String phoneNo=null;
                String name=null;

                Uri uri=data.getData();
                cursor=getContentResolver().query(uri,null,null,null,null);
                cursor.moveToFirst();
                int phoneIndex=cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                int nameIndex=cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                phoneNo=cursor.getString(phoneIndex);
                phoneNo=phoneNo.substring(3,phoneNo.length());
                name=cursor.getString(nameIndex);
                switch (index)
                {
                    case 1:
                        if(checkDuplicateContact(phoneNo))
                        {
                            ContextThemeWrapper ctw = new ContextThemeWrapper( context, R.style.AppTheme);
                            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctw);
                            alertDialogBuilder.setTitle("Duplicate Phone Number");
                            alertDialogBuilder.setMessage("Duplicate phone number detected phone number should be different");
                            alertDialogBuilder.setCancelable(false);
                            alertDialogBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });
                            alertDialogBuilder.show();
                            Toast.makeText(context,"Duplicate phone number detected phone number should be unique",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            name1.setText(name);
                            phone1.setText(phoneNo);
                            if (!rec_id1.equals("null")) {
                                Name = name;
                                Phone = phoneNo;
                                Rec_Id = rec_id1;
                                new updateEmergency().execute();
                            } else {
                                Name = name;
                                Phone = phoneNo;
                                new addEmergency().execute();
                            }
                        }
                        break;
                    case 2:
                        if(checkDuplicateContact(phoneNo))
                        {
                            ContextThemeWrapper ctw = new ContextThemeWrapper( context, R.style.AppTheme);
                            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctw);
                            alertDialogBuilder.setTitle("Duplicate Phone Number");
                            alertDialogBuilder.setMessage("Duplicate phone number detected phone number should be different");
                            alertDialogBuilder.setCancelable(false);
                            alertDialogBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });
                            alertDialogBuilder.show();
                            Toast.makeText(context,"Duplicate phone number detected phone number should be unique",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            name2.setText(name);
                            phone2.setText(phoneNo);
                            if (!rec_id2.equals("null")) {
                                Name = name;
                                Phone = phoneNo;
                                Rec_Id = rec_id2;
                                new updateEmergency().execute();
                            } else {
                                Name = name;
                                Phone = phoneNo;
                                new addEmergency().execute();
                            }
                        }
                        break;
                    case 3:
                        if(checkDuplicateContact(phoneNo))
                        {
                            ContextThemeWrapper ctw = new ContextThemeWrapper( context, R.style.AppTheme);
                            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctw);
                            alertDialogBuilder.setTitle("Duplicate Phone Number");
                            alertDialogBuilder.setMessage("Duplicate phone number detected phone number should be different");
                            alertDialogBuilder.setCancelable(false);
                            alertDialogBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });
                            alertDialogBuilder.show();
                            Toast.makeText(context,"Duplicate phone number detected phone number should be unique",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            name3.setText(name);
                            phone3.setText(phoneNo);
                            if (!rec_id3.equals("null")) {
                                Name = name;
                                Phone = phoneNo;
                                Rec_Id = rec_id3;
                                new updateEmergency().execute();
                            } else {
                                Name = name;
                                Phone = phoneNo;
                                new addEmergency().execute();
                            }
                        }
                        break;
                    case 4:
                        if(checkDuplicateContact(phoneNo))
                        {
                            ContextThemeWrapper ctw = new ContextThemeWrapper( context, R.style.AppTheme);
                            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctw);
                            alertDialogBuilder.setTitle("Duplicate Phone Number");
                            alertDialogBuilder.setMessage("Duplicate phone number detected phone number should be different");
                            alertDialogBuilder.setCancelable(false);
                            alertDialogBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });
                            alertDialogBuilder.show();
                            Toast.makeText(context,"Duplicate phone number detected phone number should be unique",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            name4.setText(name);
                            phone4.setText(phoneNo);
                            if (!rec_id4.equals("null")) {
                                Name = name;
                                Phone = phoneNo;
                                Rec_Id = rec_id4;
                                new updateEmergency().execute();
                            } else {
                                Name = name;
                                Phone = phoneNo;
                                new addEmergency().execute();
                            }
                        }
                        break;
                    case 5:
                        if(checkDuplicateContact(phoneNo))
                        {
                            ContextThemeWrapper ctw = new ContextThemeWrapper( context, R.style.AppTheme);
                            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctw);
                            alertDialogBuilder.setTitle("Duplicate Phone Number");
                            alertDialogBuilder.setMessage("Duplicate phone number detected phone number should be different");
                            alertDialogBuilder.setCancelable(false);
                            alertDialogBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });
                            alertDialogBuilder.show();
                            Toast.makeText(context,"Duplicate phone number detected phone number should be unique",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            name5.setText(name);
                            phone5.setText(phoneNo);
                            if (!rec_id5.equals("null")) {
                                Name = name;
                                Phone = phoneNo;
                                Rec_Id = rec_id5;
                                new updateEmergency().execute();
                            } else {
                                Name = name;
                                Phone = phoneNo;
                                new addEmergency().execute();
                            }
                        }
                        break;
                }
            }
            catch (Exception e)
            {

            }
        }
    }

    public void initialize()
    {
        name1=(EditText)findViewById(R.id.name1);
        name2=(EditText)findViewById(R.id.name21);
        name3=(EditText)findViewById(R.id.name3);
        name4=(EditText)findViewById(R.id.name4);
        name5=(EditText)findViewById(R.id.name5);
        name1.setEnabled(false);
        name2.setEnabled(false);
        name3.setEnabled(false);
        name4.setEnabled(false);
        name5.setEnabled(false);
        phone1=(EditText)findViewById(R.id.number1);
        phone2=(EditText)findViewById(R.id.number21);
        phone3=(EditText)findViewById(R.id.number3);
        phone4=(EditText)findViewById(R.id.number4);
        phone5=(EditText)findViewById(R.id.number5);
        phone1.setEnabled(false);
        phone2.setEnabled(false);
        phone3.setEnabled(false);
        phone4.setEnabled(false);
        phone5.setEnabled(false);
        verify=(Button)findViewById(R.id.verify);
        edit1=(ImageButton)findViewById(R.id.edit1);
        edit2=(ImageButton)findViewById(R.id.edit2);
        edit3=(ImageButton)findViewById(R.id.edit3);
        edit4=(ImageButton)findViewById(R.id.edit4);
        edit5=(ImageButton)findViewById(R.id.edit5);
        delete1=(ImageButton)findViewById(R.id.delete1);
        delete2=(ImageButton)findViewById(R.id.delete2);
        delete3=(ImageButton)findViewById(R.id.delete3);
        delete4=(ImageButton)findViewById(R.id.delete4);
        delete5=(ImageButton)findViewById(R.id.delete5);
    }
    public void setTextPhone(int i)
    {
        switch (i)
        {
            case 0:
            {
                emergencyClass obj=list.get(i);
                name1.setText(obj.getName());
                phone1.setText(obj.getPhone());
                rec_id1=obj.getId();
                numbers[i]=obj.getPhone();
                break;
            }
            case 1:
            {
                emergencyClass obj=list.get(i);
                name2.setText(obj.getName());
                phone2.setText(obj.getPhone());
                rec_id2=obj.getId();
                numbers[i]=obj.getPhone();
                break;
            }
            case 2:
            {
                emergencyClass obj=list.get(i);
                name3.setText(obj.getName());
                phone3.setText(obj.getPhone());
                rec_id3=obj.getId();
                numbers[i]=obj.getPhone();
                break;
            }
            case 3:
            {
                emergencyClass obj=list.get(i);
                name4.setText(obj.getName());
                phone4.setText(obj.getPhone());
                rec_id4=obj.getId();
                numbers[i]=obj.getPhone();
                break;
            }
            case 4:
            {
                emergencyClass obj=list.get(i);
                name5.setText(obj.getName());
                phone5.setText(obj.getPhone());
                rec_id5=obj.getId();
                numbers[i]=obj.getPhone();
                break;
            }
            default:
            {
                Toast.makeText(context,"Not here "+i,Toast.LENGTH_SHORT).show();
            }
        }
    }
    public class addEmergency extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String url=new networkData().url+new networkData().singleemergency;
            String number=mauth.getCurrentUser().getPhoneNumber();
            number=number.substring(3,number.length());
            String json=new JsonParser().addEmergency(url,number,Name,Phone);
            return json;
            // return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
            if(s!=null)
            {
                try {
                    JSONObject obj=new JSONObject(s);
                    String status = obj.getString("status");
                    System.out.println(obj);
                    z++;
                    if(status.equals("1"))
                    {
                        JSONArray array = obj.getJSONArray("rec_id");
                        JSONObject inObj=array.getJSONObject(0);
                        String rec_id = inObj.getString("rec_id");

                        switch(index)
                        {
                            case 1:
                                rec_id1=rec_id;
                                break;
                            case 2:
                                rec_id2=rec_id;
                                break;
                            case 3:
                                rec_id3=rec_id;
                                break;
                            case 4:
                                rec_id4=rec_id;
                                break;
                            case 5:
                                rec_id5=rec_id;
                                break;
                        }
                        list.add(new emergencyClass(rec_id,Name,Phone));
                        Toast.makeText(context,"Record Inserted",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(context,"Not Inserted",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context,"No Internet",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    public class updateEmergency extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String url=new networkData().url+new networkData().updateemergency;
            String number=mauth.getCurrentUser().getPhoneNumber();
            number=number.substring(3,number.length());
            String json=new JsonParser().updateHsnItem(url,Rec_Id,Name,Phone,number);
            return json;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
            if(s!=null)
            {
                try{
                    JSONObject object=new JSONObject(s);
                    String status = object.getString("status");
                    System.out.println(status);
                    list.remove(index-1);
                    list.add(new emergencyClass(Rec_Id,Name,Phone));
                    String msg=object.getString("msg");
                    Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
                }
                catch(Exception e)
                {
                    Toast.makeText(context,"Some error here",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    public class deleteEmergency extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String url=new networkData().url+new networkData().deleteEmergency;
            String number=mauth.getCurrentUser().getPhoneNumber();
            number=number.substring(3,number.length());
            String json=new JsonParser().deleteEmergency(url,Rec_Id);
            return json;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
            if(s!=null)
            {
                try{
                    JSONObject obj=new JSONObject(s);
                    String status = obj.getString("status");
                    Toast.makeText(context,"Deleted " ,Toast.LENGTH_SHORT).show();
                    if(status.equals("1")) {
                        z--;
                        switch (index) {
                            case 1:
                                rec_id1 = "null";
                                list.remove(0);
                                name1.setText("");
                                phone1.setText("");
                                break;
                            case 2:
                                rec_id2 = "null";
                                list.remove(1);
                                name2.setText("");
                                phone2.setText("");
                                break;
                            case 3:
                                rec_id3 = "null";
                                list.remove(2);
                                name3.setText("");
                                phone3.setText("");
                                break;
                            case 4:
                                rec_id4 = "null";
                                list.remove(3);
                                name4.setText("");
                                phone4.setText("");
                                break;
                            case 5:
                                rec_id5 = "null";
                                list.remove(4);
                                name5.setText("");
                                phone5.setText("");
                                break;
                        }
                    }
                    else
                    {
                        Toast.makeText(context, "Some Error in deleting contact try again", Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
    public class getEmergency extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String url=new networkData().url+new networkData().getemergency;
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
                    list=new ArrayList<>();
                    JSONObject jsonObject = new JSONObject(result);
                    final String responce = String.valueOf(jsonObject.get("status"));
                    // final String responce2=String.valueOf(jsonObject.get("msg"));
                    if(responce.equals("1"))
                    {
                        Toast.makeText(context,"Reching here",Toast.LENGTH_SHORT).show();
                        JSONArray data=jsonObject.getJSONArray("data");
                        for(int i=0;i<data.length();i++)
                        {
                            JSONObject obj=data.getJSONObject(i);
                            String rec_id = obj.getString("rec_id");
                            String name = obj.getString("name");
                            String phone = obj.getString("phone");
                            list.add(new emergencyClass(rec_id,name,phone));
                            z++;
                            setTextPhone(i);
                        }
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
}
