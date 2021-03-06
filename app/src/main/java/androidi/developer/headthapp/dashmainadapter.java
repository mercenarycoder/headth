package androidi.developer.headthapp;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidi.developer.headthapp.ApiMethods.networkData;

//import com.developer.headthapp.R;
import androidi.developer.headthapp.R;
import androidi.developer.headthapp.Report.ReportView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
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
                new GlobalVariables();
                if(GlobalVariables.Helper) {
                    new HealthCart();
                    HealthCart.check =true;
                    Intent intent = new Intent(context, ReportView.class);
                    intent.putExtra("doctor", adapter.getName());
                    intent.putExtra("url", adapter.getIcon());
                    intent.putExtra("title", adapter.getType());
                    intent.putExtra("id", adapter.getId());
                    intent.putExtra("date", adapter.getDate());
                    intent.putExtra("details",adapter.getDetails());
                    intent.putExtra("type", adapter.getTypeD());
                    intent.putExtra("category",adapter.getCats());
                    context.startActivity(intent);
                }

            }
        });
        holder.name.setText(adapter.getType());
        holder.date.setText(adapter.getDate());
        holder.type.setText(adapter.getName());
//        holder.icon.getSettings().setJavaScriptEnabled(true);
        String pdf = new networkData().url_image+adapter.getIcon();
        if(adapter.getIcon().contains(".pdf"))
        {
            holder.icon.setImageResource(R.drawable.ic_pdf);
//            holder.icon1.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=" + pdf);
        }
        else
        {
            holder.icon.setImageResource(R.drawable.index);
//            holder.icon1.loadDataWithBaseURL(null,"<html><center><img src="+pdf+"></html>",
//                    "text/html","utf-8","");
        }
//        holder.icon.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=" + pdf);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class viewholder1 extends RecyclerView.ViewHolder
    {
        TextView name,date,type;
        ImageView icon;
        LinearLayout reportitem;
        public viewholder1(@NonNull View itemView) {
            super(itemView);
        reportitem=(LinearLayout)itemView.findViewById(R.id.reportitem);
        name=(TextView)itemView.findViewById(R.id.name);
        date=(TextView)itemView.findViewById(R.id.date);
        type=(TextView)itemView.findViewById(R.id.type);
        icon=(ImageView)itemView.findViewById(R.id.icon);
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

