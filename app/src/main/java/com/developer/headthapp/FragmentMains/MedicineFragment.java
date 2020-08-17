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

public class MedicineFragment extends Fragment {
    Context context;
    RecyclerView meds;
    ArrayList<typeClass> list;
    SwipeRefreshLayout refresh_meds;
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
        View view = inflater.inflate(R.layout.medicine_fragment, container, false);
        meds=(RecyclerView)view.findViewById(R.id.meds);
        refresh_meds=(SwipeRefreshLayout)view.findViewById(R.id.refresh_meds);
        adapter=new dialogRecyler(list,context);
        add=(Button)view.findViewById(R.id.add);
        remove=(Button)view.findViewById(R.id.remove);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            dialogShower();
            }
        });
        meds.setLayoutManager(new LinearLayoutManager(context));
        meds.setHasFixedSize(true);
        meds.setAdapter(adapter);
        refresh_meds.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh_meds.setRefreshing(false);
            }
        });
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
        dialog.setContentView(R.layout.dialog_medicine);
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
    list=new ArrayList<>();
    list.add(new typeClass("Asthalin","medicine","For Asthama","Duration - Permanent","Dosage - 1 puff"));
    list.add(new typeClass("Asthalin","medicine","For Asthama","Duration - Permanent","Dosage - 1 puff"));
    list.add(new typeClass("Asthalin","medicine","For Asthama","Duration - Permanent","Dosage - 1 puff"));
    list.add(new typeClass("Asthalin","medicine","For Asthama","Duration - Permanent","Dosage - 1 puff"));
    list.add(new typeClass("Asthalin","medicine","For Asthama","Duration - Permanent","Dosage - 1 puff"));
    }
}
