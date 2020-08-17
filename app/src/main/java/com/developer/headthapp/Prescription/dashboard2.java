package com.developer.headthapp.Prescription;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.headthapp.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class dashboard2 extends RecyclerView.Adapter<dashboard2.viewholder1>{
    ArrayList<presClass> list;
    Context context;
    int cats[];
    int anchor[];
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
        holder.prescribtion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,PrescriptionsView.class);
                context.startActivity(intent);
               // Toast.makeText(context,"Report Page soon",Toast.LENGTH_SHORT).show();
            }
        });
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
        public viewholder1(@NonNull View itemView) {
            super(itemView);
            prescribtion=(LinearLayout)itemView.findViewById(R.id.prescribtion);
            date=(TextView)itemView.findViewById(R.id.date);
            diesease=(TextView)itemView.findViewById(R.id.diesease);
            dr_name=(TextView)itemView.findViewById(R.id.dr_name);
            img=(ImageView)itemView.findViewById(R.id.img);
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

