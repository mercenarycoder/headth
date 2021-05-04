package com.developer.headthapp.Covid;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
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
import com.developer.headthapp.Prescription.presImageClass;
import com.developer.headthapp.R;
//import com.ortiz.touchview.TouchImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

public class CovidPlasmaAdapter extends RecyclerView.Adapter<CovidPlasmaAdapter.viewholder1>{
    ArrayList<HelpClass> list;
    Context context;
    String Name;
    int actualPosition;
    public CovidPlasmaAdapter(ArrayList<HelpClass> list, Context context)
    {
        this.list=list;
        this.context=context;

    }
    @NonNull
    @Override
    public CovidPlasmaAdapter.viewholder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        View inflator=LayoutInflater.from(context).inflate(R.layout.plasma_emergency, parent,
                false);
        CovidPlasmaAdapter.viewholder1 viewhold=new CovidPlasmaAdapter.viewholder1(inflator);
        return viewhold;
    }

    @Override
    public void onBindViewHolder(@NonNull final CovidPlasmaAdapter.viewholder1 holder, final int position) {
        final HelpClass adapter=list.get(position);
        holder.name.setText(adapter.getCaller());
        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number_dial = adapter.getCaller();
                Intent call_intent = new Intent(Intent.ACTION_CALL);
                call_intent.setData(Uri.parse("tel:" + number_dial));
                call_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)
                            == PackageManager.PERMISSION_GRANTED) {
                        context.startActivity(call_intent);
                        return;
                    }
                    else
                    {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[]{Manifest.permission.CALL_PHONE}, 1);
                    }
                }
                else
                {
                    context.startActivity(call_intent);
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class viewholder1 extends RecyclerView.ViewHolder
    {
        TextView name;
        ImageButton call;
        public viewholder1(@NonNull View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.name);
            call=(ImageButton)itemView.findViewById(R.id.call);
        }
    }
}




