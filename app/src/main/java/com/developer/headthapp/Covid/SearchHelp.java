package com.developer.headthapp.Covid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.developer.headthapp.R;
import com.developer.headthapp.SpinnerAdapter2;
import com.developer.headthapp.SpinnerClass;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class SearchHelp extends AppCompatActivity {
 ImageButton back;
 Button search;
 ArrayList<SpinnerClass> listH,listA,listB;
 FirebaseAuth mauth=FirebaseAuth.getInstance();
 Spinner blood,age;
 EditText city,pin;
 String cityS="",pinS="",bloodS="",ageS="";
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_help);
        formBlood();
        context=SearchHelp.this;
        formAge();
        back=(ImageButton)findViewById(R.id.back);
        search=(Button)findViewById(R.id.search);
        city=(EditText)findViewById(R.id.city);
        pin=(EditText)findViewById(R.id.pin);
        blood=(Spinner)findViewById(R.id.blood);
        SpinnerAdapter2 adapterB=new SpinnerAdapter2(context,listB);
        blood.setAdapter(adapterB);
        blood.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SpinnerClass obj=listB.get(i);
                bloodS=obj.getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        age=(Spinner)findViewById(R.id.age);
        SpinnerAdapter2 adapterA=new SpinnerAdapter2(context,listA);
        age.setAdapter(adapterA);
        age.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SpinnerClass obj=listA.get(i);
                ageS=obj.getId();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        context=SearchHelp.this;
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cityS=city.getText().toString().toLowerCase();
                pinS=pin.getText().toString().toLowerCase();
                if(cityS.length()<2){
                    Toast.makeText(context,"Please enter a valid city name",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(pinS.length()!=6){
                    Toast.makeText(context,"Please enter a valid Pincode",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(bloodS.length()<1)
                {
                    Toast.makeText(context,"Please choose a valid blood group",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(ageS.length()<2)
                {
                    Toast.makeText(context,"Please enter a valid age",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent=new Intent(context,SearchResults.class);
                intent.putExtra("blood",bloodS);
                intent.putExtra("age",ageS);
                intent.putExtra("city",cityS);
                intent.putExtra("pin",pinS);
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    public void formBlood()
    {
        listB=new ArrayList<>();
        listB.add(new SpinnerClass("all","All Blood Group"));
        listB.add(new SpinnerClass("O+","O+"));
        listB.add(new SpinnerClass("A+","A+"));
        listB.add(new SpinnerClass("B+","B+"));
        listB.add(new SpinnerClass("O-","O-"));
        listB.add(new SpinnerClass("A-","A-"));
        listB.add(new SpinnerClass("AB+","AB+"));
        listB.add(new SpinnerClass("AB-","AB-"));
        listB.add(new SpinnerClass("B-","B-"));
    }
    public void formAge(){
        listA=new ArrayList<>();
        for(int i=19;i<=60;i++)
        {
            listA.add(new SpinnerClass(String.valueOf(i),String.valueOf(i)));
        }
    }
}