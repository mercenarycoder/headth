package com.developer.headthapp;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.headthapp.ApiMethods.JsonParser;
import com.developer.headthapp.ApiMethods.networkData;
import com.developer.headthapp.DeleteClass;
import com.developer.headthapp.GlobalVariables;
import com.developer.headthapp.Prescription.PrescriptionAdd;
import com.developer.headthapp.R;
import com.developer.headthapp.Report.ReportAdd;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ImageRecylerAdapter extends RecyclerView.Adapter<ImageRecylerAdapter.viewholder1>{
    ArrayList<imageRecyclerClass> list;
    Context context;
    String Name;
    int actualPosition;
    public ImageRecylerAdapter(ArrayList<imageRecyclerClass> list, Context context)
    {
        this.list=list;
        this.context=context;

    }
    @NonNull
    @Override
    public viewholder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        View inflator=LayoutInflater.from(context).inflate(R.layout.add_image_recycler, parent,
                false);
        viewholder1 viewhold=new viewholder1(inflator);
        return viewhold;
    }

    @Override
    public void onBindViewHolder(@NonNull final viewholder1 holder, final int position) {
        final imageRecyclerClass adapter=list.get(position);
        holder.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Name=adapter.getId();
                actualPosition=holder.getAdapterPosition();
                new removeData().execute();
//                Toast.makeText(context,"This will remove item",Toast.LENGTH_SHORT).show();
            }
        });
        if(!adapter.getType().equals(".pdf"))
        Picasso.with(context).load(new networkData().url_image+adapter.getId()).into(holder.icon);
        else
        {
            holder.icon.setImageResource(R.drawable.ic_pdf);
        }
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class viewholder1 extends RecyclerView.ViewHolder
    {
        ImageButton close;
        ImageView icon;
        public viewholder1(@NonNull View itemView) {
            super(itemView);
            close=(ImageButton)itemView.findViewById(R.id.close);
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
    public void alterThings(){
        if(list.size()<=0)
        {
            new ReportAdd();
            ReportAdd.pdfChecker =false;
            new ReportAdd();
            ReportAdd.imgCheck =false;
            new PrescriptionAdd();
            PrescriptionAdd.pdfChecker=false;
            new PrescriptionAdd();
            PrescriptionAdd.imgCheck=false;
        }
    }
    ProgressDialog progressDialog;
    public class removeData extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            setProgress();
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String url=new networkData().url+new networkData().deleteData;
            String data=new JsonParser().deleteImage(url,Name);
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            if(s!=null)
            {
                try{
                    JSONObject obj=new JSONObject(s);
                    String status=String.valueOf(obj.get("status"));
                    if(status.equals("1"))
                    {
                        list.remove(actualPosition);
                        alterThings();
                        notifyItemRemoved(actualPosition);
                        notifyItemRangeChanged(actualPosition, list.size());
                        String data=new PrescriptionAdd().imagePaths;
                        if(data.contains(Name))
                        {
                            data.replace(Name+";","");
                        }
                        new PrescriptionAdd().imagePaths=data;
                        Toast.makeText(context,"Data Deleted",Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}

