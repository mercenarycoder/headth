package com.developer.headthapp.ApiMethods;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.developer.headthapp.LoginUser;
import com.developer.headthapp.R;

public class Verifier extends AppCompatActivity {
EditText edit1,edit2,edit3,edit4;
TextView btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn0,title;
String otp="";
String first="no";
SharedPreferences preferences;
SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifier);
        title=(TextView)findViewById(R.id.title);
        edit1=(EditText)findViewById(R.id.edit1);
        edit2=(EditText)findViewById(R.id.edit2);
        edit3=(EditText)findViewById(R.id.edit3);
        edit4=(EditText)findViewById(R.id.edit4);
        btn1=(TextView)findViewById(R.id.btn1);
        btn2=(TextView)findViewById(R.id.btn2);
        btn3=(TextView)findViewById(R.id.btn3);
        btn4=(TextView)findViewById(R.id.btn4);
        btn5=(TextView)findViewById(R.id.btn5);
        btn6=(TextView)findViewById(R.id.btn6);
        btn7=(TextView)findViewById(R.id.btn7);
        btn8=(TextView)findViewById(R.id.btn8);
        btn9=(TextView)findViewById(R.id.btn9);
        btn0=(TextView)findViewById(R.id.btn0);
        edit1.addTextChangedListener(new watcher(edit1));
        edit2.addTextChangedListener(new watcher(edit2));
        edit3.addTextChangedListener(new watcher(edit3));
        edit4.addTextChangedListener(new watcher(edit4));
        edit1.setKeyListener(null);
        edit2.setKeyListener(null);
        edit3.setKeyListener(null);
        edit4.setKeyListener(null);
        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    private class  watcher implements TextWatcher {
        View view;
        public watcher(View view)
        {
            this.view=view;
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String text = s.toString();
            switch (view.getId()) {
                case R.id.edit1: {
                    if (text.length() == 1) {
                        edit2.requestFocus();
                    }
                    break;
                }
                case R.id.edit2: {
                    if (text.length() == 1) {
                        edit3.requestFocus();
                    } else if (text.length() == 0) {
                        edit1.requestFocus();
                    }
                    break;
                }
                case R.id.edit3: {
                    if (text.length() == 1) {
                        edit4.requestFocus();
                    } else if (text.length() == 0) {
                        edit3.requestFocus();
                    }
                    break;
                }
                case R.id.edit4: {
                    if (text.length() == 0) {
                        edit4.requestFocus();
                    }
                    break;
                }
            }
        }
    }

}