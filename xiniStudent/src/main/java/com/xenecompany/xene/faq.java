package com.xenecompany.xene;

import android.os.Bundle;

import com.xenecompany.xene.adapter.faq_adapter;
import com.xenecompany.xene.model.faq_model;

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
        ArrayList<faq_model> faq = new ArrayList<>(5);
        faq.add(new faq_model("How to amend hostel details?" ,
                "You have to send us mail through ‘Contact Us’ option in side navigation bar then the " +
                        "authentication will be done and then the amendments will be done automatically"));
        faq.add((new faq_model("How to accept a request ?" , "You’ll get a notification when a person is interested in your property, " +
                "open his profile to accept the request.")));
        faq.add(new faq_model("How to start chatting with a customer?" , "To start chatting functionality you have to first accept the request " +
                "then the icon will automatically appear."));
        faq.add(new faq_model("How to save a profile for later?" , "When you open a request, an heart sign will appear between ACCEPT and REJECT " +
                "option, upon clicking the profile will be wishlisted and you can access it from wishlist in toolbar anytime."));
        faq.add(new faq_model("How to see all the chats?" , ".All the chats can be accessed through message icon in toolbar."));
        faq_adapter f = new faq_adapter(faq);
        l = findViewById(R.id.listview1);
        l.setLayoutManager(new LinearLayoutManager(this));
        l.setAdapter(f);
    }

}