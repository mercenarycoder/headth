package com.developer.headthapp.Report;


import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.developer.headthapp.R;
import com.developer.headthapp.internalReport;
import com.developer.headthapp.reportClass;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.developer.headthapp.bundleReport;

public class dateReportAdapter extends RecyclerView.Adapter<dateReportAdapter.viewholder1>{
    ArrayList<bundleReport> list;
    Context context;
    int cats[];
    int anchor[];
    int i=4;
    public dateReportAdapter(ArrayList<bundleReport> list, Context context)
    {
        this.list=list;
        this.context=context;

    }
    @NonNull
    @Override
    public viewholder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        View inflator=LayoutInflater.from(context).inflate(R.layout.report_item_large, parent,
                false);
        viewholder1 viewhold=new viewholder1(inflator);
        return viewhold;
    }

    @Override
    public void onBindViewHolder(@NonNull final viewholder1 holder, final int position) {
        final bundleReport adapter=list.get(position);
        reportClass item1,item2,item3;
        ArrayList<reportOf3> childList=adapter.getList();
        internalReport adapter_in=new internalReport(childList,context);
        holder.internal_recycler.setLayoutManager(new LinearLayoutManager(context));
        holder.internal_recycler.setHasFixedSize(true);
        holder.internal_recycler.setAdapter(adapter_in);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class viewholder1 extends RecyclerView.ViewHolder
    {
        TextView date_date;
        RecyclerView internal_recycler;
        public viewholder1(@NonNull View itemView) {
            super(itemView);
            //item 1
            date_date=(TextView)itemView.findViewById(R.id.date_date);
            internal_recycler=(RecyclerView)itemView.findViewById(R.id.internal_recycler);
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

