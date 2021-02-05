package com.xenecompany.xene;


import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;

import android.util.DisplayMetrics;
import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import java.lang.ref.PhantomReference;
import java.util.HashMap;


public class HomePage extends Activity {

    private androidx.appcompat.widget.Toolbar toolbar;
    private RecyclerView parentRecyclerView;
    private int width;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        SessionManager sessionManager = new SessionManager(HomePage.this);
        HashMap<String , String> userdata=sessionManager.getUserDetailFromSession();
        progressBar = (ProgressBar)findViewById(R.id.homepageProgressBar);
        ////////toolbar
        toolbar = (androidx.appcompat.widget.Toolbar)findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_main);
        FrameLayout notificationLayout=(FrameLayout)toolbar.getMenu().findItem(R.id.toolbar_notification).getActionView();
        notificationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               TextView textView=(TextView)view.findViewById(R.id.notificationCount);
               textView.setText("0");
            }
        });
        FrameLayout wishlistLayout=(FrameLayout)toolbar.getMenu().findItem(R.id.toolbar_wishlist).getActionView();
        wishlistLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HomePage.this , wishlist.class);
                startActivity(intent);
            }
        });
        FrameLayout messageLayout=(FrameLayout)toolbar.getMenu().findItem(R.id.toolbar_message).getActionView();
        messageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView textView=(TextView)view.findViewById(R.id.messageCount);
                textView.setText("0");
            }
        });
        ////////toolbar

        ////////////parent recycler view
        DisplayMetrics displayMetrics= new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        width=displayMetrics.widthPixels;
        parentRecyclerView =(RecyclerView)findViewById(R.id.homePageParentRecyclerView);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        parentRecyclerView.setLayoutManager(linearLayoutManager);
        parentRecyclerView.setHasFixedSize(true);
        homePageParentRecyclerView homePageParentRecyclerViewAdapter =new homePageParentRecyclerView(this , width , progressBar);
        homePageParentRecyclerViewAdapter.notifyDataSetChanged();
        parentRecyclerView.setAdapter(homePageParentRecyclerViewAdapter);
        ////////////parent recycler view
    }
}