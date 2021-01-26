package com.xenecompany.xene;

import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import android.os.Bundle;
import android.widget.ImageView;


public class profile extends AppCompatActivity {
    CircleImageView profileImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        profileImage=(CircleImageView)findViewById(R.id.profileImage);
   //     Picasso.with(getApplicationContext()).load(R.drawable.demo_profile).noFade().into(profileImage);

    }
}