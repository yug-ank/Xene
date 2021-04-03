package com.xenecompany.xinihostel;

import android.os.Bundle;

import com.xenecompany.xinihostel.adapter.notification_adapter;
import com.xenecompany.xinihostel.model.notification_model;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class notification extends AppCompatActivity {
    RecyclerView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ArrayList<notification_model> obj = new ArrayList<>(50);
        for(int i=0;i<50;i++)
            obj.add(new notification_model(getString(R.string.notification_data_heading),getString(R.string.notification_data_detail)));
        notification_adapter adapter = new notification_adapter(obj);
        rv = findViewById(R.id.recycleV1);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
    }
}