package com.developer.headthapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.developer.headthapp.FragmentMains.DiseaseFragment;
import com.developer.headthapp.FragmentMains.FragmentAllergies;
import com.developer.headthapp.FragmentMains.FragmentHistiry;
import com.developer.headthapp.FragmentMains.MedicineFragment;
import com.developer.headthapp.Prescription.Prescriptions;
import com.developer.headthapp.Prescription.dashboard2;
import com.developer.headthapp.Prescription.presClass;
import com.developer.headthapp.Qr.QRone;
import com.developer.headthapp.Report.ReportActivity;

import java.util.ArrayList;

public class HealthCart extends AppCompatActivity {
RecyclerView recycler_report,recycler_pres;
dashmainadapter adapter;
Button view_pres,view_report;
dashboard2 adapter2;
ArrayList<reportClass> list;
ArrayList<presClass>  list2;
ArrayList<typeClass> dis,med,all,his;
Context context;
Button open_profile;
static FrameLayout layout_changer;
ImageButton open_notification,qr_act;
LinearLayout disease,medicine,allergies,history;
ImageButton disease_2,medicine_2,allergies_2,history_2;
public static void changeVisiblity()
{
    layout_changer.setVisibility(View.INVISIBLE);
}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_cart);
        formList();
        formList2();
        context=HealthCart.this;
        view_pres=(Button)findViewById(R.id.view_pres);
        view_report=(Button)findViewById(R.id.view_report);
        recycler_report=(RecyclerView)findViewById(R.id.recycler_report);
        adapter=new dashmainadapter(list,context);
        recycler_report.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        recycler_report.setAdapter(adapter);
        layout_changer=(FrameLayout)findViewById(R.id.layout_changer);
        //main buttons
        disease=(LinearLayout)findViewById(R.id.disease);
        medicine=(LinearLayout)findViewById(R.id.medicine);
        allergies=(LinearLayout)findViewById(R.id.allergies);
        history=(LinearLayout)findViewById(R.id.history);
        disease_2=(ImageButton)findViewById(R.id.disease_2);
        medicine_2=(ImageButton)findViewById(R.id.medicine_2);
        allergies_2=(ImageButton)findViewById(R.id.allergies_2);
        history_2=(ImageButton)findViewById(R.id.history_2);
        qr_act=(ImageButton)findViewById(R.id.qr_act);
        qr_act.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HealthCart.this, QRone.class);
                startActivity(intent);
            }
        });
        disease_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.layout_changer,new DiseaseFragment()).commit();
                layout_changer.setVisibility(View.VISIBLE);
            }
        });
        disease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
          getSupportFragmentManager().beginTransaction().replace(R.id.layout_changer,new DiseaseFragment()).commit();
          layout_changer.setVisibility(View.VISIBLE);
            }
        });
        medicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.layout_changer,new MedicineFragment()).commit();
                layout_changer.setVisibility(View.VISIBLE);
            }
        });
        medicine_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.layout_changer,new MedicineFragment()).commit();
                layout_changer.setVisibility(View.VISIBLE);
            }
        });
        allergies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.layout_changer,new FragmentAllergies()).commit();
                layout_changer.setVisibility(View.VISIBLE);
            }
        });
        allergies_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.layout_changer,new FragmentAllergies()).commit();
                layout_changer.setVisibility(View.VISIBLE);
            }
        });
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.layout_changer,new FragmentHistiry()).commit();
                layout_changer.setVisibility(View.VISIBLE);
            }
        });
        history_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.layout_changer,new FragmentHistiry()).commit();
                layout_changer.setVisibility(View.VISIBLE);
            }
        });
        //
        recycler_pres=(RecyclerView)findViewById(R.id.recycler_pres);
        adapter2=new dashboard2(list2,context);
        recycler_pres.setHasFixedSize(true);
        recycler_pres.setLayoutManager(new LinearLayoutManager(context));
        recycler_pres.setAdapter(adapter2);

        open_profile=(Button)findViewById(R.id.open_profile);
        open_notification=(ImageButton)findViewById(R.id.open_notification);

        open_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,Profile.class);
                startActivity(intent);
            }
        });
        open_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,Notifications.class);
                startActivity(intent);
            }
        });
        view_pres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, Prescriptions.class);
                startActivity(intent);
            }
        });
        view_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ReportActivity.class);
                startActivity(intent);
            }
        });
    }
    public void formList()
    {
        list=new ArrayList<>();
        list.add(new reportClass("Dr Manjeet Singh","Dna Report","23rd July","1",""));
        list.add(new reportClass("Dr Manjeet Singh","Dna Report","23rd July","1",""));
        list.add(new reportClass("Dr Manjeet Singh","Dna Report","23rd July","1",""));
        list.add(new reportClass("Dr Manjeet Singh","Dna Report","23rd July","1",""));
        list.add(new reportClass("Dr Manjeet Singh","Dna Report","23rd July","1",""));
        list.add(new reportClass("Dr Manjeet Singh","Dna Report","23rd July","1",""));
        list2=new ArrayList<>();
        list2.add(new presClass("Black Death","03/08/20","Dr. Manjeet Singh"," ","123"));
        list2.add(new presClass("Black Death","03/08/20","Dr. Manjeet Singh"," ","123"));
        list2.add(new presClass("Black Death","03/08/20","Dr. Manjeet Singh"," ","123"));
        list2.add(new presClass("Black Death","03/08/20","Dr. Manjeet Singh"," ","123"));
        list2.add(new presClass("Black Death","03/08/20","Dr. Manjeet Singh"," ","123"));
        list2.add(new presClass("Black Death","03/08/20","Dr. Manjeet Singh"," ","123"));
    }
    public void formList2()
    {
        dis=new ArrayList<>();
        dis.add(new typeClass("Malaria","disease","I have had asthama since the age of 10. I am a regular user of asthalin","",""));
        dis.add(new typeClass("Malaria","disease","I have had asthama since the age of 10. I am a regular user of asthalin","",""));
        dis.add(new typeClass("Malaria","disease","I have had asthama since the age of 10. I am a regular user of asthalin","",""));
        dis.add(new typeClass("Malaria","disease","I have had asthama since the age of 10. I am a regular user of asthalin","",""));
        dis.add(new typeClass("Malaria","disease","I have had asthama since the age of 10. I am a regular user of asthalin","",""));
        med=new ArrayList<>();
        med.add(new typeClass("valprol 500","medicine","for asthama","Duration - Weekly Once","Dosage - 1 puff"));
        med.add(new typeClass("valprol 500","medicine","for asthama","Duration - Weekly Once","Dosage - 1 puff"));
        med.add(new typeClass("valprol 500","medicine","for asthama","Duration - Weekly Once","Dosage - 1 puff"));
        med.add(new typeClass("valprol 500","medicine","for asthama","Duration - Weekly Once","Dosage - 1 puff"));
        med.add(new typeClass("valprol 500","medicine","for asthama","Duration - Weekly Once","Dosage - 1 puff"));
        all=new ArrayList<>();
        all.add(new typeClass("Urticeria","allergies","Triggers - Body Heat","",""));
        all.add(new typeClass("Urticeria","allergies","Triggers - Body Heat","",""));
        all.add(new typeClass("Urticeria","allergies","Triggers - Body Heat","",""));
        all.add(new typeClass("Urticeria","allergies","Triggers - Body Heat","",""));
        all.add(new typeClass("Urticeria","allergies","Triggers - Body Heat","",""));
        his=new ArrayList<>();
        his.add(new typeClass("Admitted","history"," For Asthamatic Attack Last Month","",""));
        his.add(new typeClass("Admitted","history"," For Asthamatic Attack Last Month","",""));
        his.add(new typeClass("Admitted","history"," For Asthamatic Attack Last Month","",""));
        his.add(new typeClass("Admitted","history"," For Asthamatic Attack Last Month","",""));
        his.add(new typeClass("Admitted","history"," For Asthamatic Attack Last Month","",""));
    }
}