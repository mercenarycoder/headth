package com.developer.headthapp.Prescription;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.headthapp.ApiMethods.networkData;
import com.developer.headthapp.DeleteClass;
import com.developer.headthapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class dashboard2 extends RecyclerView.Adapter<dashboard2.viewholder1>{
    ArrayList<presClass> list;
    Context context;
    int cats[];
    int anchor[];
    DeleteClass dd=new DeleteClass();
    int i=4;
    public dashboard2(ArrayList<presClass> list, Context context)
    {
        this.list=list;
        this.context=context;

    }
    @NonNull
    @Override
    public viewholder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        View inflator=LayoutInflater.from(context).inflate(R.layout.prescribtion_item, parent,
                false);
        viewholder1 viewhold=new viewholder1(inflator);
        return viewhold;
    }

    @Override
    public void onBindViewHolder(@NonNull final viewholder1 holder, final int position) {
        final presClass adapter=list.get(position);
        holder.prescribtion.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                holder.visible.setVisibility(View.VISIBLE);
                holder.check.setVisibility(View.VISIBLE);
                holder.check.setChecked(true);
                if(!dd.listD.containsKey(adapter.getId())) {
                    dd.listD.put(adapter.getId(), adapter.getImg_url());
                }
                return true;
            }
        });
        holder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!b)
                {
                    holder.visible.setVisibility(View.INVISIBLE);
                    holder.check.setVisibility(View.INVISIBLE);
                    if(!dd.listD.containsKey(adapter.getId())) {
                        dd.listD.put(adapter.getId(), adapter.getImg_url());
                    }
                }
                else
                {
                    holder.visible.setVisibility(View.VISIBLE);
                    holder.check.setVisibility(View.VISIBLE);
                    if(dd.listD.containsKey(adapter.getId())) {
                        dd.listD.remove(adapter.getId());
                    }
                }
            }
        });
        holder.prescribtion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,PrescriptionsView.class);
                intent.putExtra("title",adapter.getDiesease());
                intent.putExtra("doctor",adapter.getDoctor());
                intent.putExtra("observation",adapter.getObservation());
                intent.putExtra("image",new networkData().url.substring(0
                        ,new networkData().url.length()-4)+adapter.getImg_url());
                intent.putExtra("id",adapter.getId());
                intent.putExtra("date",adapter.getDate());
                context.startActivity(intent);
               // Toast.makeText(context,"Report Page soon",Toast.LENGTH_SHORT).show();
            }
        });
        holder.diesease.setText(adapter.getDiesease());
        String url=new networkData().url.substring(0,new networkData().url.length()-4)+adapter.getImg_url();
        if(url.contains(".jpeg")) {
            Picasso.with(context).load(url).placeholder(R.drawable.ic_pdf).into(holder.img);
        }
        else
        {
            holder.img.setVisibility(View.INVISIBLE);
            holder.web.setVisibility(View.VISIBLE);
            holder.web.getSettings().setJavaScriptEnabled(true);
            holder.web.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=" + url);
        }
        holder.date.setText(adapter.getDate());
        holder.dr_name.setText(adapter.getDoctor());
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class viewholder1 extends RecyclerView.ViewHolder
    {
        TextView date;
        TextView diesease,dr_name;
        ImageView img;
        LinearLayout prescribtion;
        WebView web;
        CheckBox check;
        TextView visible;
        public viewholder1(@NonNull View itemView) {
            super(itemView);
            prescribtion=(LinearLayout)itemView.findViewById(R.id.prescribtion);
            date=(TextView)itemView.findViewById(R.id.date);
            diesease=(TextView)itemView.findViewById(R.id.diesease);
            dr_name=(TextView)itemView.findViewById(R.id.dr_name);
            img=(ImageView)itemView.findViewById(R.id.img);
            web=(WebView)itemView.findViewById(R.id.web);
            check=(CheckBox)itemView.findViewById(R.id.check);
            visible=(TextView)itemView.findViewById(R.id.visibile);
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

