package com.xenecompany.xene;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class wishlist extends AppCompatActivity {

    private androidx.appcompat.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);
        ////////toolbar
        toolbar = (androidx.appcompat.widget.Toolbar)findViewById(R.id.wishlistToolbar);
        toolbar.inflateMenu(R.menu.menu_main);
        FrameLayout wishlistLayout=(FrameLayout)toolbar.getMenu().findItem(R.id.toolbar_wishlist).getActionView();
        ImageView imageView=(ImageView)wishlistLayout.findViewById(R.id.toolbarHeartImageView);
        imageView.setImageResource(R.drawable.toolbar_heart_selected);

        FrameLayout notificationLayout=(FrameLayout)toolbar.getMenu().findItem(R.id.toolbar_notification).getActionView();
        notificationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView textView=(TextView)view.findViewById(R.id.notificationCount);
                textView.setText("0");
                ImageView imageView=(ImageView)findViewById(R.id.toolbarNotificationImageView);
                imageView.setImageResource(R.drawable.toolbar_notification_selected);
            }
        });

        FrameLayout messageLayout=(FrameLayout)toolbar.getMenu().findItem(R.id.toolbar_message).getActionView();
        messageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView textView=(TextView)view.findViewById(R.id.messageCount);
                textView.setText("0");
                ImageView imageView=(ImageView)findViewById(R.id.toolbarMessageImageView);
                imageView.setImageResource(R.drawable.toolbar_message_selected);
            }
        });
        ////////toolbar

    }
}