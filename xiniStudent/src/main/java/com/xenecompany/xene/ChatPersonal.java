package com.xenecompany.xene;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChatPersonal extends AppCompatActivity {
    private EditText messagebox;
    EditText message;
    ImageView send;
    TextView nameOfHostel;
    ImageView profilePic;
    String name ,profilePicture ,messageId;
    ArrayList<chat_object> chat;
    RecyclerView recyclerView;
    DatabaseReference db , db1;
    chatPersonalAdapter adapter;
    ChildEventListener childEventListener;
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        db1.addChildEventListener(childEventListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        db1.removeEventListener(childEventListener);
    }

    private void getMessages() {
        db1 = db;
        Map<String , Object> temp = new HashMap<>();
        temp.put("seen", true);
        childEventListener =  new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(snapshot.exists()){
                    if(snapshot.child("sender").getValue().toString().equals("H")) {
                        db1.child(snapshot.getKey()).updateChildren(temp);
                    }
                    chat.add(
                            new chat_object(snapshot.child("text").getValue().toString()
                                    , snapshot.child("time").getValue().toString()
                                    , snapshot.child("sender").getValue().toString()
                                    , (Boolean) snapshot.child("seen").getValue()));
                    adapter.notifyDataSetChanged();
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
        };
    }

    private void initializeMessages() {
        chat = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        recyclerView.setHasFixedSize(false);
        adapter = new chatPersonalAdapter(chat , this);
        recyclerView.setAdapter(adapter);
    }

    public void sendMessage(View view) {
        messageId = db.push().getKey();
        DatabaseReference db1 = db.child(messageId);
        Map object = new HashMap<>();
        if(!message.getText().toString().isEmpty())
            object.put("text", message.getText().toString());
        object.put("sender", "U");
        object.put("seen", false);
        object.put("time", "time");
        db1.updateChildren(object);
        message.setText(null);
    }
}