package com.developer.headthapp.Report;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.developer.headthapp.R;
import com.developer.headthapp.bundleReport;
import com.developer.headthapp.reportClass;

import java.util.ArrayList;

public class ReportActivity extends AppCompatActivity {
ImageButton back;
ArrayList<bundleReport> list2;
ArrayList<reportClass> list;
ArrayList<reportOf3> list3;
RecyclerView previous_report;
dateReportAdapter adapter;
Context context;
Button add_report,remove_report;
ImageButton filter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        back=(ImageButton)findViewById(R.id.back);
        context=ReportActivity.this;
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        formList();
        filter=(ImageButton)findViewById(R.id.filter);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogShower();
            }
        });
        add_report=(Button)findViewById(R.id.add_report);
        remove_report=(Button)findViewById(R.id.remove_report);
        add_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ReportActivity.this, ReportAdd.class);
                startActivity(intent);
            }
        });
        remove_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"Remove Functionality will be here soon",Toast.LENGTH_SHORT).show();
            }
        });
        context=ReportActivity.this;
        previous_report=(RecyclerView)findViewById(R.id.previous_reports);
        previous_report.setLayoutManager(new LinearLayoutManager(context));
        previous_report.setHasFixedSize(true);
        adapter=new dateReportAdapter(list2,context);
        previous_report.setAdapter(adapter);
    }
    public void dialogShower()
    {
        final Dialog dialog=new Dialog(context, 0);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_filter);
        ImageButton close_btn2=(ImageButton)dialog.findViewById(R.id.close_dialog);
        close_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void formList()
    {
        list2=new ArrayList<>();
        list=new ArrayList<>();
        reportClass item=new reportClass("Dr Manjeet Singh","Dna Report","23rd July","1","");
        list.add(new reportClass("Dr Manjeet Singh","Dna Report","23rd July","1",""));
        list.add(new reportClass("Dr Manjeet Singh","Dna Report","23rd July","1",""));
        list.add(new reportClass("Dr Manjeet Singh","Dna Report","23rd July","1",""));
        list3=new ArrayList<>();
        list3.add(new reportOf3(item,item,item));
        list3.add(new reportOf3(item,item,item));
        list3.add(new reportOf3(item,item,item));
        list2.add(new bundleReport("24/10/20",list3));
        list2.add(new bundleReport("25/10/20",list3));
        list2.add(new bundleReport("04/11/20",list3));
    }
}