package com.developer.headthapp.Prescription;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

import com.developer.headthapp.R;

import java.util.ArrayList;

public class Prescriptions extends AppCompatActivity {
RecyclerView previous_pres;
ArrayList<presClass> list2;
Context context;
ImageButton back,filter;
dashboard2 adapter;
Button add_prescription,remove_prescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescriptions);
        formList();
        context=Prescriptions.this;
        filter=(ImageButton)findViewById(R.id.filter);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogShower();
            }
            
        });
        back=(ImageButton)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        add_prescription=(Button)findViewById(R.id.add);
        remove_prescription=(Button)findViewById(R.id.remove);
        add_prescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Prescriptions.this, PrescriptionAdd.class);
                startActivity(intent);
            }
        });
        previous_pres=(RecyclerView)findViewById(R.id.previous_pres);
        adapter=new dashboard2(list2,context);
        previous_pres.setHasFixedSize(true);
        previous_pres.setLayoutManager(new LinearLayoutManager(context));
        previous_pres.setAdapter(adapter);
    }
    public void dialogShower()
    {
        final Dialog dialog=new Dialog(context, 0);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_filter);
        ImageButton close_btn2=(ImageButton)dialog.findViewById(R.id.close_dialog);
        close_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void formList()
    {
        list2=new ArrayList<>();
        list2.add(new presClass("Black Death","03/08/20","Dr. Manjeet Singh"," ","123"));
        list2.add(new presClass("Black Death","03/08/20","Dr. Manjeet Singh"," ","123"));
        list2.add(new presClass("Black Death","03/08/20","Dr. Manjeet Singh"," ","123"));
        list2.add(new presClass("Black Death","03/08/20","Dr. Manjeet Singh"," ","123"));
        list2.add(new presClass("Black Death","03/08/20","Dr. Manjeet Singh"," ","123"));
        list2.add(new presClass("Black Death","03/08/20","Dr. Manjeet Singh"," ","123"));
    }
}