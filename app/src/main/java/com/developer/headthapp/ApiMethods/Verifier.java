package com.developer.headthapp.ApiMethods;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.headthapp.LoginUser;
import com.developer.headthapp.R;

public class Verifier extends AppCompatActivity {
EditText edit1,edit2,edit3,edit4;
TextView btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn0,title;
String otp="";
String first="no";
SharedPreferences preferences;
SharedPreferences.Editor editor;
Button delete;
Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifier);
        context=Verifier.this;
        title=(TextView)findViewById(R.id.title);
        edit1=(EditText)findViewById(R.id.edit11);
        edit2=(EditText)findViewById(R.id.edit2);
        edit3=(EditText)findViewById(R.id.edit3);
        edit4=(EditText)findViewById(R.id.edit4);
        delete=(Button)findViewById(R.id.delete);
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
//        edit1.addTextChangedListener(new watcher(edit1));
//        edit2.addTextChangedListener(new watcher(edit2));
//        edit3.addTextChangedListener(new watcher(edit3));
//        edit4.addTextChangedListener(new watcher(edit4));
        edit1.setKeyListener(null);
        edit2.setKeyListener(null);
        edit3.setKeyListener(null);
        edit4.setKeyListener(null);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch(otp.length())
                {
                    case 1:
                        edit1.setText("");
                        otp="";
                        break;
                    case 2:
                        edit2.setText("");
                        otp=otp.substring(0,1);
                        Toast.makeText(context,otp,Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        edit3.setText("");
                        otp=otp.substring(0,2);
                        Toast.makeText(context,otp,Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        edit4.setText("");
                        otp=otp.substring(0,3);
                        Toast.makeText(context,otp,Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(context,"Nothing to delete",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           otpSetter(0);
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           otpSetter(1);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            otpSetter(2);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            otpSetter(3);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            otpSetter(4);
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            otpSetter(5);
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            otpSetter(6);
            }
        });
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            otpSetter(7);
            }
        });
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            otpSetter(8);
            }
        });
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            otpSetter(9);
            }
        });
    }
    public void otpSetter(int i)
    {
        String num="";
        switch(i)
        {
            case 0:
                num="0";
                break;
            case 1:
                num="1";
                break;
            case 2:
                num="2";
                break;
            case 3:
                num="3";
                break;
            case 4:
                num="4";
                break;
            case 5:
                num="5";
                break;
            case 6:
                num="6";
                break;
            case 7:
                num="7";
                break;
            case 8:
                num="8";
                break;
            case 9:
                num="9";
                break;
        }
        if(otp.length()<4)
        {
            otp+=num;
            switch (otp.length())
            {
                case 1:
                    edit1.setText("*");
                    break;
                case 2:
                    edit2.setText("*");
                    break;
                case 3:
                    edit3.setText("*");
                    break;
                case 4:
                    edit4.setText("*");
                    break;
            }
        }
        else
        {
            Toast.makeText(context,"Otp can be of 4 digit only",Toast.LENGTH_SHORT).show();
        }
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