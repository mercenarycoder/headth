package com.developer.headthapp.Qr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.developer.headthapp.R;

public class QRone extends AppCompatActivity {
ImageButton close_btn;
Button scanner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_rone);
        close_btn=(ImageButton)findViewById(R.id.close_btn);
        scanner=(Button)findViewById(R.id.scanner);
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(QRone.this,QrScanner.class);
                startActivity(intent);
            }
        });
    }
}