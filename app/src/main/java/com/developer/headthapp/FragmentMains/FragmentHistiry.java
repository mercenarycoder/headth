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

public class FragmentHistiry extends Fragment{
Context context;
RecyclerView history;
SwipeRefreshLayout refresh_history;
ArrayList<typeClass> list;
dialogRecyler adapter;
Button add,remove;
ImageButton close_btn;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getActivity();
        formList();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.history_fragment, container, false);
        history=(RecyclerView)view.findViewById(R.id.history);
        add=(Button)view.findViewById(R.id.add);
        remove=(Button)view.findViewById(R.id.remove);
        refresh_history=(SwipeRefreshLayout)view.findViewById(R.id.refresh_history);
        adapter=new dialogRecyler(list,context);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogShower();
            }
        });
        history.setLayoutManager(new LinearLayoutManager(context));
        history.setHasFixedSize(true);
        history.setAdapter(adapter);
        refresh_history.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh_history.setRefreshing(false);
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
        dialog.setContentView(R.layout.dialog_history);
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
        list.add(new typeClass("Admitted","history","For Asthamatic Attack Last Month","",""));
        list.add(new typeClass("Admitted","history","For Asthamatic Attack Last Month","",""));
        list.add(new typeClass("Admitted","history","For Asthamatic Attack Last Month","",""));
        list.add(new typeClass("Admitted","history","For Asthamatic Attack Last Month","",""));
        list.add(new typeClass("Admitted","history","For Asthamatic Attack Last Month","",""));
    }
}
