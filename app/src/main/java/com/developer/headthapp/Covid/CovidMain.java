package com.developer.headthapp.Covid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.developer.headthapp.R;

public class CovidMain extends AppCompatActivity {
    Button search_2,search_1;
    Context context;
    ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_main);
        context=CovidMain.this;
        initialize();
        search_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,Volunteer.class);
                startActivity(intent);
            }
        });
        search_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,SearchHelp.class);
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
    public void initialize(){
        search_1=(Button)findViewById(R.id.search_1);
        search_2=(Button)findViewById(R.id.search_2);
        back=(ImageButton)findViewById(R.id.back);
    }
}