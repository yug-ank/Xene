package com.xenecompany.xinihostel;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ChatPersonal extends AppCompatActivity {
    private EditText messagebox;
    EditText message;
    ImageView send;
    TextView nameOfHostel;
    ImageView profilePic;
    String name ,profilePicture ,messageId;
    ArrayList<chat_object> chat;
    RecyclerView recyclerView;
    DatabaseReference db;
    chatPersonalAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_personal);
        message = findViewById(R.id.activityChatPersonal_editText);
        nameOfHostel = findViewById(R.id.activityChatPersonal_name);
        profilePic = findViewById(R.id.activityChatPersonal_profilePicture);
        send = findViewById(R.id.activityChatPersonal_send);
        recyclerView = findViewById(R.id.activityChatPersonal_recyclerView);
        db = FirebaseDatabase.getInstance().getReference().child("chatrooms").child(getIntent().getStringExtra("chatroom"));
        name = getIntent().getStringExtra("name");
        profilePicture = getIntent().getStringExtra("profilePicture");
        nameOfHostel.setText(name);
        Picasso.get().load(profilePicture).into(profilePic);
        Log.i("yash", "came1 ");
        initializeMessages();
        getMessages();
    }

    private void getMessages() {
        Log.i("yash", "came9 ");
        DatabaseReference db1 = db;
        Log.i("yash", "came10 ");
        db1.addChildEventListener(new ChildEventListener() {
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

    public void sendMessage(View view) {
        messageId = db.push().getKey();
        DatabaseReference db1 = db.child(messageId);
        Map object = new HashMap<>();
        if(!message.getText().toString().isEmpty())
            object.put("text", message.getText().toString());
        object.put("sender", "U");
        object.put("time", "time");
        db1.updateChildren(object);
        message.setText(null);
    }
}