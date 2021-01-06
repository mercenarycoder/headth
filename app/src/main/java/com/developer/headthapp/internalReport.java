package com.developer.headthapp;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.developer.headthapp.ApiMethods.networkData;
import com.developer.headthapp.Report.ReportView;
import com.developer.headthapp.Report.reportOf3;

public class internalReport extends RecyclerView.Adapter<internalReport.viewholder1>{
    ArrayList<reportOf3> list;
    Context context;
   DeleteClass dd=new DeleteClass();
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
                    intent.putExtra("doctor",item1.getName());
                    intent.putExtra("url",new networkData().url_image+item1.getIcon());
                    intent.putExtra("title",item1.getType());
                    intent.putExtra("id",item1.getId());
                    intent.putExtra("share",item1.getIcon());
                    intent.putExtra("type",item1.getTypeD());
                    context.startActivity(intent);
                }
            });
            holder.reportitem1.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    holder.visible1.setVisibility(View.VISIBLE);
                    holder.check1.setVisibility(View.VISIBLE);
                    holder.check1.setChecked(true);
                    if(!dd.listD.containsKey(item1.getId())) {
                        dd.listD.put(item1.getId(),item1.getId());
                    }
                    return true;
                }
            });
            holder.check1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b)
                    {
                        holder.visible1.setVisibility(View.VISIBLE);
                        holder.check1.setVisibility(View.VISIBLE);
                        if(!dd.listD.containsKey(item1.getId())) {
                            dd.listD.put(item1.getId(),item1.getId());
                        }
                    }
                    else
                    {
                        holder.visible1.setVisibility(View.INVISIBLE);
                        holder.check1.setVisibility(View.INVISIBLE);
                        if(dd.listD.containsKey(item1.getId())) {
                            dd.listD.remove(item1.getId(),item1.getId());
                        }
                    }
                }
            });

            holder.name1.setText(item1.getType());
            holder.date1.setText(item1.getDate());
            holder.type1.setText(item1.getName());
//            holder.icon1.getSettings().setJavaScriptEnabled(true);
            String pdf = new networkData().url_image+item1.getIcon();
            if(item1.getTypeD().contains(".pdf"))
            {
            holder.icon1.setImageResource(R.drawable.ic_pdf);
//            holder.icon1.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=" + pdf);
            }
            else
            {
            holder.icon1.setImageResource(R.drawable.index);
//            holder.icon1.loadDataWithBaseURL(null,"<html><center><img src="+pdf+"></html>",
//                    "text/html","utf-8","");
            }
        }
        else
        {
            holder.reportitem1.setVisibility(View.INVISIBLE);
        }
        if(!item2.name.equals("null"))
        {
            holder.reportitem2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context, ReportView.class);
                    intent.putExtra("doctor",item2.getName());
                    intent.putExtra("url",new networkData().url_image+item2.getIcon());
                    intent.putExtra("title",item2.getType());
                    intent.putExtra("id",item2.getId());
                    intent.putExtra("share",item2.getIcon());
                    intent.putExtra("type",item2.getTypeD());
                    context.startActivity(intent);
                }
            });
            holder.reportitem2.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    holder.visible2.setVisibility(View.VISIBLE);
                    holder.check2.setVisibility(View.VISIBLE);
                    holder.check2.setChecked(true);
                    if(!dd.listD.containsKey(item2.getId())) {
                        dd.listD.put(item2.getId(),item2.getId());
                    }
                    return true;
                }
            });
            holder.check2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b)
                    {
                        holder.visible2.setVisibility(View.VISIBLE);
                        holder.check2.setVisibility(View.VISIBLE);
                        if(!dd.listD.containsKey(item2.getId())) {
                            dd.listD.put(item2.getId(),item2.getId());
                        }
                    }
                    else
                    {
                        holder.visible2.setVisibility(View.INVISIBLE);
                        holder.check2.setVisibility(View.INVISIBLE);
                        if(dd.listD.containsKey(item2.getId())) {
                            dd.listD.remove(item2.getId(),item2.getId());
                        }
                    }
                }
            });

            holder.name2.setText(item2.getType());
            holder.date2.setText(item2.getDate());
            holder.type2.setText(item2.getName());
//            holder.icon2.getSettings().setJavaScriptEnabled(true);
            String pdf = new networkData().url_image+item2.getIcon();
            if(item2.getTypeD().contains(".pdf"))
            {
            holder.icon2.setImageResource(R.drawable.ic_pdf);
//                holder.icon2.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=" + pdf);
            }
            else
            {
            holder.icon2.setImageResource(R.drawable.index);
//                holder.icon2.loadDataWithBaseURL(null,"<html><center><img src="+pdf+"></html>",
//                        "text/html","utf-8","");
            }
        }
        else
        {
            holder.reportitem2.setVisibility(View.INVISIBLE);
        }
        if(!item3.name.equals("null"))
        {
            holder.reportitem3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context, ReportView.class);
                    intent.putExtra("doctor",item3.getName());
                    intent.putExtra("url",new networkData().url_image+item3.getIcon());
                    intent.putExtra("title",item3.getType());
                    intent.putExtra("id",item3.getId());
                    intent.putExtra("share",item3.getIcon());
                    intent.putExtra("type",item3.getTypeD());
                    context.startActivity(intent); }
            });
            holder.reportitem3.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    holder.visible3.setVisibility(View.VISIBLE);
                    holder.check3.setVisibility(View.VISIBLE);
                    holder.check3.setChecked(true);
                    if(!dd.listD.containsKey(item3.getId())) {
                        dd.listD.put(item3.getId(),item3.getId());
                    }
                    return true;
                }
            });
            holder.check3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b)
                    {
                        holder.visible3.setVisibility(View.VISIBLE);
                        holder.check3.setVisibility(View.VISIBLE);
                        if(!dd.listD.containsKey(item3.getId())) {
                            dd.listD.put(item3.getId(),item3.getId());
                        }
                    }
                    else
                    {
                        holder.visible3.setVisibility(View.INVISIBLE);
                        holder.check3.setVisibility(View.INVISIBLE);
                        if(dd.listD.containsKey(item3.getId())) {
                            dd.listD.remove(item3.getId(),item3.getId());
                        }
                    }
                }
            });
            holder.name3.setText(item3.getType());
            holder.date3.setText(item3.getDate());
            holder.type3.setText(item3.getName());
//            holder.icon3.getSettings().setJavaScriptEnabled(true);
            String pdf = new networkData().url_image+item3.getIcon();
            if(item3.getTypeD().contains(".pdf"))
            {
                holder.icon3.setImageResource(R.drawable.ic_pdf);
//                holder.icon3.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=" + pdf);
            }
            else
            {
                holder.icon3.setImageResource(R.drawable.index);
//                holder.icon3.loadDataWithBaseURL(null,"<html><center><img src="+pdf+"></html>",
//                        "text/html","utf-8","");
            }
        }
        else
        {
            holder.reportitem3.setVisibility(View.INVISIBLE);
        }
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class viewholder1 extends RecyclerView.ViewHolder
    {
        TextView name1,date1,type1,name2,date2,type2,name3,date3,type3;
        ImageView icon1,icon2,icon3;
        CheckBox check1,check2,check3;
        TextView visible1,visible2,visible3;
        LinearLayout reportitem1,reportitem2,reportitem3;
        public viewholder1(@NonNull View itemView) {
            super(itemView);
            //item 1
            reportitem1=(LinearLayout)itemView.findViewById(R.id.reportitem1);
            name1=(TextView)itemView.findViewById(R.id.name1);
            date1=(TextView)itemView.findViewById(R.id.date1);
            type1=(TextView)itemView.findViewById(R.id.type1);
            icon1=(ImageView)itemView.findViewById(R.id.icon1);
            check1=(CheckBox)itemView.findViewById(R.id.check1);
            visible1=(TextView)itemView.findViewById(R.id.visibile1);
            //item 2
            reportitem2=(LinearLayout)itemView.findViewById(R.id.reportitem2);
            name2=(TextView)itemView.findViewById(R.id.name2);
            date2=(TextView)itemView.findViewById(R.id.date2);
            type2=(TextView)itemView.findViewById(R.id.type2);
            icon2=(ImageView)itemView.findViewById(R.id.icon2);
            check2=(CheckBox)itemView.findViewById(R.id.check2);
            visible2=(TextView)itemView.findViewById(R.id.visibile2);
            //item 3
            reportitem3=(LinearLayout)itemView.findViewById(R.id.reportitem3);
            name3=(TextView)itemView.findViewById(R.id.name3);
            date3=(TextView)itemView.findViewById(R.id.date3);
            type3=(TextView)itemView.findViewById(R.id.type3);
            icon3=(ImageView)itemView.findViewById(R.id.icon3);
            check3=(CheckBox)itemView.findViewById(R.id.check3);
            visible3=(TextView)itemView.findViewById(R.id.visibile3);
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


