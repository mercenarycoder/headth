package com.developer.headthapp.FragmentMains;


import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.headthapp.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.developer.headthapp.typeClass;

public class dialogRecyler extends RecyclerView.Adapter<dialogRecyler.viewholder1>{
    ArrayList<typeClass> list;
    Context context;
    int cats[];
    int anchor[];
    int i=4;
    public dialogRecyler(ArrayList<typeClass> list, Context context)
    {
        this.list=list;
        this.context=context;

    }
    @NonNull
    @Override
    public viewholder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        View inflator=LayoutInflater.from(context).inflate(R.layout.dialog_item_recycler, parent,
                false);
        viewholder1 viewhold=new viewholder1(inflator);
        return viewhold;
    }

    @Override
    public void onBindViewHolder(@NonNull final viewholder1 holder, final int position) {
        final typeClass adapter=list.get(position);
        if(adapter.getType().equals("disease"))
        {
         holder.name.setText(adapter.getTitle());
         holder.thing1.setText(adapter.getThing1());
         holder.thing2.setText(adapter.getThing2());
         holder.thing3.setText(adapter.getThing3());
         holder.edit.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Toast.makeText(context,"disease",Toast.LENGTH_SHORT).show();
             }
         });
        }
        if(adapter.getType().equals("medicine"))
        {
            holder.name.setText(adapter.getTitle());
            holder.thing1.setText(adapter.getThing1());
            holder.thing2.setText(adapter.getThing2());
            holder.thing3.setText(adapter.getThing3());
            holder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context,"medicine",Toast.LENGTH_SHORT).show();
                }
            });
        }
        if(adapter.getType().equals("allergies"))
        {
            holder.name.setText(adapter.getTitle());
            holder.thing1.setText(adapter.getThing1());
            holder.thing2.setText("");
            holder.thing3.setText("");
            holder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context,"allergies",Toast.LENGTH_SHORT).show();
                }
            });
        }
        if(adapter.getType().equals("history"))
        {
            holder.name.setText(adapter.getTitle());
            holder.thing1.setText(adapter.getThing1());
            holder.thing2.setText(adapter.getThing2());
            holder.thing3.setText(adapter.getThing3());
            holder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context,"history",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class viewholder1 extends RecyclerView.ViewHolder
    {
        TextView name,thing1,thing2,thing3;
        Button edit;
        public viewholder1(@NonNull View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.name);
            edit=(Button)itemView.findViewById(R.id.edit);
            thing1=(TextView)itemView.findViewById(R.id.thing1);
            thing2=(TextView)itemView.findViewById(R.id.thing2);
            thing3=(TextView)itemView.findViewById(R.id.thing3);
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

