package com.developer.headthapp.NotificationsDir;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.headthapp.ApiMethods.JsonParser;
import com.developer.headthapp.ApiMethods.networkData;
import com.developer.headthapp.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONObject;

public class adapterNotification extends RecyclerView.Adapter<adapterNotification.viewholder1>{
    ArrayList<notiClass> list;
    Context context;
    String Id="";
    int actualPosition;
    public adapterNotification(ArrayList<notiClass> list, Context context)
    {
        this.list=list;
        this.context=context;

    }
    @NonNull
    @Override
    public viewholder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        View inflator=LayoutInflater.from(context).inflate(R.layout.notifcation_item, parent,
                false);
        viewholder1 viewhold=new viewholder1(inflator);
        return viewhold;
    }

    @Override
    public void onBindViewHolder(@NonNull final viewholder1 holder, final int position) {
        final notiClass adapter=list.get(position);
        holder.action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,NotificationLayout.class);
                intent.putExtra("title",adapter.getTitle());
                intent.putExtra("content",adapter.getDecription());
                intent.putExtra("date",adapter.getDate());
                intent.putExtra("id",adapter.getId());
                context.startActivity(intent);
//                Toast.makeText(context,"Layout unknown",Toast.LENGTH_SHORT).show();
            }
        });
        holder.hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Id=adapter.getId();
                new deleteNotification().execute();
                actualPosition = holder.getAdapterPosition();
            }
        });
        holder.date.setText(adapter.getDate());
        holder.decription.setText(adapter.getDecription());
        holder.title.setText(adapter.getTitle());
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class viewholder1 extends RecyclerView.ViewHolder
    {
        TextView title,date,decription;
        Button action,hide;
        public viewholder1(@NonNull View itemView) {
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.title);
            date=(TextView)itemView.findViewById(R.id.date);
            decription=(TextView)itemView.findViewById(R.id.description);
            action=(Button)itemView.findViewById(R.id.action);
            hide=(Button)itemView.findViewById(R.id.hide);
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
    public class deleteNotification extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            setProgress();
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String url=new networkData().url+new networkData().deleteNotification;
            String data=new JsonParser().deleteNotification(url,Id);
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            if(s!=null)
            {
                try{
                    JSONObject object = new JSONObject(s);
                    String status = object.getString("status");
                    if(status.equals("1"))
                    {
                        list.remove(actualPosition);
                        notifyItemRemoved(actualPosition);
                        notifyItemRangeChanged(actualPosition, list.size());
                    }
                    else
                    {

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

