package com.xenecompany.xene;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;


public class HomePage extends Activity implements NavigationView.OnNavigationItemSelectedListener {

    private androidx.appcompat.widget.Toolbar toolbar;
    private RecyclerView parentRecyclerView;
    private int width;
    private ProgressBar progressBar;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private TextView navigationName;
    private CircleImageView navigationImage;
    HashMap<String , String> sessionData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_with_navigation_drawer);
        SessionManager sessionManager = new SessionManager(HomePage.this);
        HashMap<String , String> userdata=sessionManager.getUserDetailFromSession();
        progressBar = (ProgressBar)findViewById(R.id.homepageProgressBar);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        sessionData=sessionManager.getUserDetailFromSession();
        ////////toolbar
        toolbar = (androidx.appcompat.widget.Toolbar)findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.activityHomePageWithNavigation_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toolbar.inflateMenu(R.menu.menu_main);
        FrameLayout navigationLayout=(FrameLayout)toolbar.getMenu().findItem(R.id.toolbar_notification).getActionView();
        NavigationView navigationView=findViewById(R.id.nav_view);
        View headerLayout=navigationView.getHeaderView(0);
        navigationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               TextView textView=(TextView)view.findViewById(R.id.notificationCount);
               textView.setText("0");
            }
        });
        navigationName=(TextView)headerLayout.findViewById(R.id.navigationName);
        navigationImage=(CircleImageView)headerLayout.findViewById(R.id.navigationImage);
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        db.collection("Student").document("+91"+sessionData.get(SessionManager.Key_Phone_no)).addSnapshotListener(
                new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(value.exists()){
                            if(value.get("name").toString().trim().length()>0){
                                navigationName.setText(value.get("name").toString());
                            }
                            if(value.get("profilePicture").toString().trim().length()>0) {
                                Picasso.get().load(value.get("profilePicture").toString()).noFade().into(navigationImage, new Callback() {
                                    @Override
                                    public void onSuccess() {

                                    }

                                    @Override
                                    public void onError(Exception e) {
                                        Toast.makeText(HomePage.this, "" + e, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            else{
                                navigationImage.setImageResource(R.drawable.ic_male_avatr);
                            }
                        }
                    }
                });
        navigationImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HomePage.this , profile.class);
                startActivity(intent);
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.myDeals: {
                Toast.makeText(this, "My Deals selected", Toast.LENGTH_LONG).show();
                startActivity(new Intent(this , my_deals.class));
                break;
            }
            case R.id.help: {
                Toast.makeText(this, "Help selected", Toast.LENGTH_LONG).show();
                startActivity(new Intent(this , faq.class));
                break;
            }
            case R.id.activityMainDrawer_signOut : {
                Toast.makeText(this, "Sign-Out selected", Toast.LENGTH_LONG).show();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this,Loginpage.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK));
                break;
            }
            case R.id.about_us :
            {
                Toast.makeText(this, "About Us selected", Toast.LENGTH_LONG).show();
                startActivity(new Intent(this , About_Us.class));
                break;
            }
            case R.id.Contact_Us : {
                Toast.makeText(this, "Contact Us selected", Toast.LENGTH_LONG).show();
                startActivity(new Intent(this , contact_us.class));
                break;
            }

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}