package com.xenecompany.xinihostel;

import android.os.Bundle;

import com.xenecompany.xinihostel.adapter.faq_adapter;
import com.xenecompany.xinihostel.model.faq_model;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class faq extends AppCompatActivity {
    private RecyclerView l;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        ArrayList<faq_model> faq = new ArrayList<>(20);
        for(int i=0;i<20;i++)
            faq.add(new faq_model(getString(R.string.faq_q1),getString(R.string.faq_a1)));
        faq_adapter f = new faq_adapter(faq);
        l = findViewById(R.id.listview1);
        l.setLayoutManager(new LinearLayoutManager(this));
        l.setAdapter(f);
    }

}