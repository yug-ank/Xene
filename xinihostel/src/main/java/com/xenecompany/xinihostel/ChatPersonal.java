package com.xenecompany.xinihostel;

import android.os.Bundle;
import android.util.DisplayMetrics;
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
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ChatPersonal extends AppCompatActivity {
//    private EditText messagebox;
    EditText message;
    ImageView send;
    TextView nameOfHostel;
    ImageView profilePic;
    String name ,profilePicture ,messageId;
    ArrayList<chat_object> chat;
    RecyclerView recyclerView;
    DatabaseReference db;
    chatPersonalAdapter adapter;
    int width;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_personal);
        DisplayMetrics displayMetrics= new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        width=displayMetrics.widthPixels;
         message = findViewById(R.id.activityChatPersonal_editText);
        nameOfHostel = findViewById(R.id.activityChatPersonal_name);
        profilePic = findViewById(R.id.activityChatPersonal_profilePicture);
         send = findViewById(R.id.activityChatPersonal_send);
        recyclerView = findViewById(R.id.activityChatPersonal_recyclerView);

        db = FirebaseDatabase.getInstance().getReference().child("chatrooms").child(getIntent().getStringExtra("chatroom"));
        name = getIntent().getStringExtra("name");
        profilePicture = getIntent().getStringExtra("profilePicture");
        nameOfHostel.setText(name);
        if(!profilePicture.isEmpty())
            Picasso.get().load(profilePicture).into(profilePic);
        initializeEditText();
        initializeMessages();
        getMessages();
    }

    private void initializeEditText() {
        db.child(getIntent().getStringExtra("chatroom")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    if((Boolean) snapshot.child("text").getValue()){
                        message.setEnabled(true);
                        message.setVisibility(View.VISIBLE);
                        send.setVisibility(View.VISIBLE);
                    }
                    else{
                        findViewById(R.id.activityChatPersonal_flag).setEnabled(true);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getMessages() {
        DatabaseReference db1 = db;
        db1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(snapshot.exists()){
                    chat.add(new chat_object(snapshot.child("text").getValue().toString(), snapshot.child("time").getValue().toString(), snapshot.child("sender").getValue().toString()));
                    adapter.notifyDataSetChanged();
                    Log.i("yash", "came13 ");
                    recyclerView.smoothScrollToPosition(chat.size()-1);
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(false);
        adapter = new chatPersonalAdapter(chat , this , width);
        recyclerView.setAdapter(adapter);
    }

    public void sendMessage(View view) {
        messageId = db.push().getKey();
        DatabaseReference db1 = db.child(messageId);
        Map object = new HashMap<>();
        if(!message.getText().toString().isEmpty())
            object.put("text", message.getText().toString());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        String currentDateandTime = sdf.format(new Date());
        object.put("sender", "H");
        object.put("time", currentDateandTime);
        db1.updateChildren(object);
        message.setText(null);
    }
}