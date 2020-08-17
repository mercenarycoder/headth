package com.developer.headthapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;

public class Notifications extends AppCompatActivity {
ArrayList<notiClass> list;
adapterNotification adapter;
Context context;
ImageButton back;
RecyclerView get_notification;
SwipeRefreshLayout refresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        context=Notifications.this;
        formList();
        back=(ImageButton)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        get_notification=(RecyclerView)findViewById(R.id.get_notification);
        refresh=(SwipeRefreshLayout)findViewById(R.id.refresh);
        get_notification.setHasFixedSize(true);
        get_notification.setLayoutManager(new LinearLayoutManager(context));
        adapter=new adapterNotification(list,context);
        get_notification.setAdapter(adapter);

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh.setRefreshing(false);
            }
        });
    }
    public void formList()
    {
        list=new ArrayList<>();
        list.add(new notiClass("","","",""));
        list.add(new notiClass("","","",""));
        list.add(new notiClass("","","",""));
        list.add(new notiClass("","","",""));
        list.add(new notiClass("","","",""));
        list.add(new notiClass("","","",""));
    }
}