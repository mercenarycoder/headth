package com.developer.headthapp;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.developer.headthapp.Report.ReportView;
import com.developer.headthapp.Report.reportOf3;

public class internalReport extends RecyclerView.Adapter<internalReport.viewholder1>{
    ArrayList<reportOf3> list;
    Context context;
    int cats[];
    int anchor[];
    int i=4;
    public internalReport(ArrayList<reportOf3> list, Context context)
    {
        this.list=list;
        this.context=context;

    }
    @NonNull
    @Override
    public viewholder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        View inflator=LayoutInflater.from(context).inflate(R.layout.internal_report, parent,
                false);
        viewholder1 viewhold=new viewholder1(inflator);
        return viewhold;
    }

    @Override
    public void onBindViewHolder(@NonNull final viewholder1 holder, final int position) {
        final reportOf3 adapter=list.get(position);
        reportClass item1,item2,item3;
        item1=adapter.getItem1();
        item2=adapter.getItem2();
        item3=adapter.getItem3();
        // ArrayList<reportOf3> childList=adapter.getList();
        if(!item1.name.equals("null"))
        {
            holder.reportitem1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context, ReportView.class);
                    context.startActivity(intent);
                }
            });
            holder.name1.setText(item1.getName());
            holder.date1.setText(item1.getDate());
            holder.type1.setText(item1.getType());
        }
        if(!item2.name.equals("null"))
        {
            holder.reportitem2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context, ReportView.class);
                    context.startActivity(intent);
                }
            });
            holder.name2.setText(item2.getName());
            holder.date2.setText(item2.getDate());
            holder.type2.setText(item2.getType());
        }
        if(!item3.name.equals("null"))
        {
            holder.reportitem3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context, ReportView.class);
                    context.startActivity(intent);
                }
            });
            holder.name3.setText(item3.getName());
            holder.date3.setText(item3.getDate());
            holder.type3.setText(item3.getType());
        }
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class viewholder1 extends RecyclerView.ViewHolder
    {
        TextView name1,date1,type1,name2,date2,type2,name3,date3,type3;
        WebView icon1,icon2,icon3;
        LinearLayout reportitem1,reportitem2,reportitem3;
        public viewholder1(@NonNull View itemView) {
            super(itemView);
            //item 1
            reportitem1=(LinearLayout)itemView.findViewById(R.id.reportitem1);
            name1=(TextView)itemView.findViewById(R.id.name1);
            date1=(TextView)itemView.findViewById(R.id.date1);
            type1=(TextView)itemView.findViewById(R.id.type1);
            icon1=(WebView)itemView.findViewById(R.id.icon1);
            //item 2
            reportitem2=(LinearLayout)itemView.findViewById(R.id.reportitem2);
            name2=(TextView)itemView.findViewById(R.id.name2);
            date2=(TextView)itemView.findViewById(R.id.date2);
            type2=(TextView)itemView.findViewById(R.id.type2);
            icon2=(WebView)itemView.findViewById(R.id.icon2);
            //item 3
            reportitem3=(LinearLayout)itemView.findViewById(R.id.reportitem3);
            name3=(TextView)itemView.findViewById(R.id.name3);
            date3=(TextView)itemView.findViewById(R.id.date3);
            type3=(TextView)itemView.findViewById(R.id.type3);
            icon3=(WebView)itemView.findViewById(R.id.icon3);
        }
    }
    public void setProgress()
    {
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("Data Loading");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }
    ProgressDialog progressDialog;
}


