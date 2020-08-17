package com.developer.headthapp.Qr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.developer.headthapp.R;

public class QrScanner extends AppCompatActivity {
ImageButton close_btn;
TextView next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_scanner);
        close_btn=(ImageButton)findViewById(R.id.close_btn);
        next=(TextView)findViewById(R.id.next);
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(QrScanner.this,QRotp.class);
                startActivity(intent);
            }
        });
    }
}