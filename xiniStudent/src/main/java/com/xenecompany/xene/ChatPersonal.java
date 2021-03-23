package com.xenecompany.xene;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ChatPersonal extends AppCompatActivity {
    private EditText messagebox;
    String name ,profilePicture;
    ArrayList<chat_object> chat;
    RecyclerView recyclerView;
    DatabaseReference db;
    chatPersonalAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_personal);
        recyclerView = findViewById(R.id.activityChatPersonal_recyclerView);
        name = getIntent().getStringExtra("name");
        profilePicture = getIntent().getStringExtra("profilePicture");
        Log.i("yash", "came1 ");
        initializeMessages();
        getMessages();
    }

    private void getMessages() {
        Log.i("yash", "came9 ");
        db = FirebaseDatabase.getInstance().getReference().child("chatrooms").child(getIntent().getStringExtra("chatroom"));
        Log.i("yash", "came10 ");
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(snapshot.exists()){
                    Log.i("yash", snapshot.child("text").getValue().toString());
                    chat.add(new chat_object(snapshot.child("text").getValue().toString(), snapshot.child("time").getValue().toString(), snapshot.child("sender").getValue().toString()));
                    Log.i("yash", "came12 ");
                    adapter.notifyDataSetChanged();
                    Log.i("yash", "came13 ");
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initializeMessages() {
        chat = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
        recyclerView.setHasFixedSize(false);
        Log.i("yash", "came2 ");
        adapter = new chatPersonalAdapter(chat);
        Log.i("yash", "came3 ");
        recyclerView.setAdapter(adapter);
        Log.i("yash", "came4 ");
    }
}