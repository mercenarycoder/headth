package com.developer.headthapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
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
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.developer.headthapp.FragmentMains.DiseaseFragment;
import com.developer.headthapp.FragmentMains.FragmentAllergies;
import com.developer.headthapp.FragmentMains.FragmentHistiry;
import com.developer.headthapp.FragmentMains.MedicineFragment;
import com.developer.headthapp.Prescription.Prescriptions;
import com.developer.headthapp.Prescription.dashboard2;
import com.developer.headthapp.Prescription.presClass;
import com.developer.headthapp.Qr.QRone;
import com.developer.headthapp.Report.ReportActivity;
import com.google.android.gms.vision.text.Line;
import com.skydoves.balloon.ArrowOrientation;
import com.skydoves.balloon.Balloon;
import com.skydoves.balloon.BalloonAnimation;
import com.skydoves.balloon.OnBalloonClickListener;

import java.util.ArrayList;

public class HealthCart extends AppCompatActivity {
RecyclerView recycler_report,recycler_pres;
dashmainadapter adapter;
Button view_pres,view_report,help;
dashboard2 adapter2;
ArrayList<reportClass> list;
ArrayList<presClass>  list2;
ArrayList<typeClass> dis,med,all,his;
Context context;
Button open_profile;
static FrameLayout layout_changer;
ImageButton open_notification,qr_act;
LinearLayout disease,medicine,allergies,history,help_layout,disease_help,medicine_hep,allergies_help,history_help,qr_help;
LinearLayout d_help,m_help,a_help,h_help,noti_help,profile_help,report_help2,pres_help2;
Button next_d,next_m,next_a,next_h,next_qr,next_n,next_p,next_r,next_pres,report_help,pres_help,profile_2;
RelativeLayout help_layout2;
ImageButton disease_2,medicine_2,allergies_2,history_2,qr,noti;

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
        initiaLize();
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           help_layout2.setVisibility(View.VISIBLE);
           qr.setVisibility(View.VISIBLE);
           qr_help.setVisibility(View.VISIBLE);
            }
        });
        next_qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qr.setVisibility(View.INVISIBLE);
                qr_help.setVisibility(View.INVISIBLE);
                noti.setVisibility(View.VISIBLE);
                noti_help.setVisibility(View.VISIBLE);
            }
        });
        next_n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noti.setVisibility(View.INVISIBLE);
                noti_help.setVisibility(View.INVISIBLE);
                profile_2.setVisibility(View.VISIBLE);
                profile_help.setVisibility(View.VISIBLE);
            }
        });
        next_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profile_2.setVisibility(View.INVISIBLE);
                profile_help.setVisibility(View.INVISIBLE);
                help_layout2.setVisibility(View.INVISIBLE);
                help_layout.setVisibility(View.VISIBLE);
                disease_help.setVisibility(View.VISIBLE);
                d_help.setVisibility(View.VISIBLE);
            }
        });
        next_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                medicine_hep.setVisibility(View.VISIBLE);
                disease_help.setVisibility(View.INVISIBLE);
                m_help.setVisibility(View.VISIBLE);
                d_help.setVisibility(View.INVISIBLE);
            }
        });
        next_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                medicine_hep.setVisibility(View.INVISIBLE);
                allergies_help.setVisibility(View.VISIBLE);
                m_help.setVisibility(View.INVISIBLE);
                a_help.setVisibility(View.VISIBLE);
            }
        });
        next_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allergies_help.setVisibility(View.INVISIBLE);
                history_help.setVisibility(View.VISIBLE);
                a_help.setVisibility(View.INVISIBLE);
                h_help.setVisibility(View.VISIBLE);
            }
        });
        next_h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                history_help.setVisibility(View.INVISIBLE);
                h_help.setVisibility(View.INVISIBLE);
                help_layout.setVisibility(View.INVISIBLE);
                help_layout2.setVisibility(View.VISIBLE);
                report_help.setVisibility(View.VISIBLE);
                report_help2.setVisibility(View.VISIBLE);
            }
        });
        next_r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                report_help.setVisibility(View.INVISIBLE);
                report_help2.setVisibility(View.INVISIBLE);
               pres_help.setVisibility(View.VISIBLE);
               pres_help2.setVisibility(View.VISIBLE);
            }
        });
        next_pres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pres_help.setVisibility(View.INVISIBLE);
                pres_help2.setVisibility(View.INVISIBLE);
                help_layout2.setVisibility(View.INVISIBLE);
            }
        });
        context=HealthCart.this;
        adapter=new dashmainadapter(list,context);
        recycler_report.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        recycler_report.setAdapter(adapter);

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
    public void initiaLize()
    {
        help=(Button)findViewById(R.id.help);
        view_pres=(Button)findViewById(R.id.view_pres);
        view_report=(Button)findViewById(R.id.view_report);
        recycler_report=(RecyclerView)findViewById(R.id.recycler_report);
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
        //helper layouts from here
        help_layout=(LinearLayout)findViewById(R.id.help_layout);
        disease_help=(LinearLayout)findViewById(R.id.disease_help);
        medicine_hep=(LinearLayout)findViewById(R.id.medicine_help);
        allergies_help=(LinearLayout)findViewById(R.id.allergies_help);
        history_help=(LinearLayout)findViewById(R.id.history_help);
        next_d=(Button)findViewById(R.id.next_d);
        next_h=(Button)findViewById(R.id.next_h);
        next_a=(Button)findViewById(R.id.next_a);
        next_m=(Button)findViewById(R.id.next_m);
        d_help=(LinearLayout)findViewById(R.id.d_help);
        a_help=(LinearLayout)findViewById(R.id.a_help);
        h_help=(LinearLayout)findViewById(R.id.h_help);
        m_help=(LinearLayout)findViewById(R.id.m_help);
        help_layout2=(RelativeLayout)findViewById(R.id.help_layout2);
        qr=(ImageButton)findViewById(R.id.qr);
        noti=(ImageButton)findViewById(R.id.noti);
        profile_2=(Button)findViewById(R.id.profile_2);
        next_qr=(Button)findViewById(R.id.next_qr);
        next_n=(Button)findViewById(R.id.next_n);
        next_p=(Button)findViewById(R.id.next_p);
        next_r=(Button)findViewById(R.id.next_r);
        next_pres=(Button)findViewById(R.id.next_pres);
        qr_help=(LinearLayout)findViewById(R.id.qr_help);
        noti_help=(LinearLayout)findViewById(R.id.noti_help);
        profile_help=(LinearLayout)findViewById(R.id.profile_help);
        report_help=(Button)findViewById(R.id.report_help);
        report_help2=(LinearLayout)findViewById(R.id.report_help2);
        pres_help=(Button)findViewById(R.id.pres_help);
        pres_help2=(LinearLayout)findViewById(R.id.pres_help2);
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
        list2.add(new presClass("Black Death","03/08/20","Dr. Manjeet Singh"," ","123",""));
        list2.add(new presClass("Black Death","03/08/20","Dr. Manjeet Singh"," ","123",""));
        list2.add(new presClass("Black Death","03/08/20","Dr. Manjeet Singh"," ","123",""));
        list2.add(new presClass("Black Death","03/08/20","Dr. Manjeet Singh"," ","123",""));
        list2.add(new presClass("Black Death","03/08/20","Dr. Manjeet Singh"," ","123",""));
        list2.add(new presClass("Black Death","03/08/20","Dr. Manjeet Singh"," ","123",""));
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