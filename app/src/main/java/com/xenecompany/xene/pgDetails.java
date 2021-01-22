package com.xenecompany.xene;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.xenecompany.xene.adapter.pgDetailsAdapter;
import com.xenecompany.xene.model.pgDetailsModel;

import java.util.ArrayList;

public class pgDetails extends AppCompatActivity {
    ArrayList<pgDetailsModel> data;
    ViewPager2 pager;
    TabLayout tablayout;
    LayoutInflater inflater;
    LinearLayout linearly;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pg_details);

    //////view pager setting /start

        data = new ArrayList<>(4);
        data.add(new pgDetailsModel(R.drawable.demo_hostel));
        data.add(new pgDetailsModel(R.drawable.demo_hostel));
        data.add(new pgDetailsModel(R.drawable.demo_hostel));
        data.add(new pgDetailsModel(R.drawable.demo_hostel));

        pager = findViewById(R.id.viewPager22);
        pager.setAdapter(new pgDetailsAdapter(data,this));
        CompositePageTransformer transformer = new CompositePageTransformer();
        transformer.addTransformer(new MarginPageTransformer((8)));
        transformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float v = 1 - Math.abs(position);
                page.setScaleY(0.8f+v*0.2f);
            }
        });
        pager.setPageTransformer(transformer);

        tablayout = (TabLayout) findViewById(R.id.tabLayout2);
        new TabLayoutMediator(tablayout ,pager ,
                (tab , position) -> tab.select()).attach();
    //////vie pager setting /end

/*
    //////utilities setting /start
        linearly = (LinearLayout) findViewById(R.id.pgPicturesUtilitiesLayout);
        inflater = LayoutInflater.from(this);
        for(int i=0;i<10;i++){
            View view = inflater.inflate(R.layout.layout_pg_pictures_utilities_img,linearly,false);
            linearly.addView(view);
        }
*/
    }
}