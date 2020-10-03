package com.developer.headthapp;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.headthapp.ApiMethods.networkData;
import com.developer.headthapp.Report.ReportView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class dashmainadapter extends RecyclerView.Adapter<dashmainadapter.viewholder1>{
    ArrayList<reportClass> list;
    Context context;
    int cats[];
    int anchor[];
    int i=4;
    public dashmainadapter(ArrayList<reportClass> list, Context context)
    {
        this.list=list;
        this.context=context;

    }
    @NonNull
    @Override
    public viewholder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        View inflator=LayoutInflater.from(context).inflate(R.layout.report_item, parent,
                false);
        viewholder1 viewhold=new viewholder1(inflator);
        return viewhold;
    }

    @Override
    public void onBindViewHolder(@NonNull final viewholder1 holder, final int position) {
        final reportClass adapter=list.get(position);

        holder.reportitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(context,"Report Page soon",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(context, ReportView.class);
                intent.putExtra("doctor",adapter.getName());
                intent.putExtra("url",new networkData().url_image+adapter.getIcon());
                intent.putExtra("title",adapter.getType());
                intent.putExtra("id",adapter.getId());
                intent.putExtra("type",adapter.getTypeD());
                context.startActivity(intent);
            }
        });
        holder.name.setText(adapter.getType());
        holder.date.setText(adapter.getDate());
        holder.type.setText(adapter.getName());
        holder.icon.getSettings().setJavaScriptEnabled(true);
        String pdf = new networkData().url_image+adapter.getIcon();
        holder.icon.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=" + pdf);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class viewholder1 extends RecyclerView.ViewHolder
    {
        TextView name,date,type;
        WebView icon;
        LinearLayout reportitem;
        public viewholder1(@NonNull View itemView) {
            super(itemView);
        reportitem=(LinearLayout)itemView.findViewById(R.id.reportitem);
        name=(TextView)itemView.findViewById(R.id.name);
        date=(TextView)itemView.findViewById(R.id.date);
        type=(TextView)itemView.findViewById(R.id.type);
        icon=(WebView)itemView.findViewById(R.id.icon);
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

