package com.developer.headthapp.FragmentMains;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

import com.developer.headthapp.HealthCart;
import com.developer.headthapp.R;
import com.developer.headthapp.typeClass;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class DiseaseFragment extends Fragment {
ArrayList<typeClass> dis;
Context context;
RecyclerView dis2;
SwipeRefreshLayout refresh_dis;
dialogRecyler adapter;
Button add,remove;
ImageButton close_btn;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getContext();
        formList();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.disease_fragment, container, false);
      add=(Button)view.findViewById(R.id.add);
      remove=(Button)view.findViewById(R.id.remove);
      add.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              dialogShower();
          }
      });
      dis2=(RecyclerView)view.findViewById(R.id.dis);
      refresh_dis=(SwipeRefreshLayout)view.findViewById(R.id.refresh_dis);
      adapter=new dialogRecyler(dis,context);
      dis2.setLayoutManager(new LinearLayoutManager(context));
      dis2.setHasFixedSize(true);
      dis2.setAdapter(adapter);
      close_btn=(ImageButton)view.findViewById(R.id.close_btn);
      close_btn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              new HealthCart();
              HealthCart.changeVisiblity();
          }
      });
      return view;
    }
    public void dialogShower()
    {
        final Dialog dialog=new Dialog(context, 0);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_deiseas);
        ImageButton close_btn2=(ImageButton)dialog.findViewById(R.id.close_btn2);
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
        dis=new ArrayList<>();
        dis.add(new typeClass("Malaria","disease","I have had asthama since the age of 10. I am a regular user of asthalin","",""));
        dis.add(new typeClass("Malaria","disease","I have had asthama since the age of 10. I am a regular user of asthalin","",""));
        dis.add(new typeClass("Malaria","disease","I have had asthama since the age of 10. I am a regular user of asthalin","",""));
        dis.add(new typeClass("Malaria","disease","I have had asthama since the age of 10. I am a regular user of asthalin","",""));
        dis.add(new typeClass("Malaria","disease","I have had asthama since the age of 10. I am a regular user of asthalin","",""));
    }
}
