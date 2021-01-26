package com.xenecompany.xene;


import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;

import android.os.Bundle;

import android.util.DisplayMetrics;
import android.util.Log;

import android.view.MenuItem;
import android.widget.ProgressBar;
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
        toolbar.setOnMenuItemClickListener(new androidx.appcompat.widget.Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(HomePage.this , "selected" , Toast.LENGTH_LONG).show();
                switch (item.getItemId()){
                    case R.id.toolbar_wishlist:
                        Log.i("rectify" , "working");
                        Toast.makeText(HomePage.this , "wishlist" , Toast.LENGTH_LONG).show();
                        break;
                    case R.id.toolbar_notification:
                        Toast.makeText(HomePage.this , "notification" , Toast.LENGTH_LONG).show();
                        break;
                    case R.id.toolbar_message:
                        Toast.makeText(HomePage.this , "message" , Toast.LENGTH_LONG).show();
                        break;
                }
                return true;
            }
        });
        toolbar.setTitle("XINI");
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