package com.xenecompany.xinihostel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;

public class splash_screen extends AppCompatActivity {

    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (FirebaseAuth.getInstance().getCurrentUser()!=null) {
                    Intent intent = new Intent(splash_screen.this, HomePage.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Intent intent = new Intent(splash_screen.this, Loginpage.class);
                    startActivity(intent);
                    finish();
                }
            }
        },2000);
    }
}