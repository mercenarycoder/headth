package com.developer.headthapp.Qr;


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

import com.developer.headthapp.ApiMethods.networkData;
import com.developer.headthapp.R;
import com.google.android.gms.vision.text.Line;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class QrAdapter extends RecyclerView.Adapter<QrAdapter.viewholder1>{
    ArrayList<qrBase> list;
    Context context;

    public QrAdapter(ArrayList<qrBase> list, Context context)
    {
        this.list=list;
        this.context=context;

    }
    @NonNull
    @Override
    public viewholder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        View inflator=LayoutInflater.from(context).inflate(R.layout.item_qr, parent,
                false);
        viewholder1 viewhold=new viewholder1(inflator);
        return viewhold;
    }

    @Override
    public void onBindViewHolder(@NonNull final viewholder1 holder, final int position) {
        final qrBase adapter=list.get(position);
        holder.item.setText(adapter.getTitle());
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class viewholder1 extends RecyclerView.ViewHolder
    {
        TextView item;
        LinearLayout layout;
        public viewholder1(@NonNull View itemView) {
            super(itemView);
            item=(TextView)itemView.findViewById(R.id.item);
            layout=(LinearLayout)itemView.findViewById(R.id.layout);
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

