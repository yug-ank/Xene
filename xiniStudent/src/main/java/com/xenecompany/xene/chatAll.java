package com.xenecompany.xene;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;

public class chatAll extends AppCompatActivity {
    ArrayList<ChatList> chatList;
    RecyclerView recyclerView;
    public  chat_all_adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_all);
        recyclerView = (RecyclerView) findViewById(R.id.activityChatAll_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(false);
        getCHatList();
        adapter = new chat_all_adapter(chatList , this);
        recyclerView.setAdapter(adapter);
    }

    void getCHatList(){
        chatList = new ArrayList<>();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("user").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Log.i("data snaphot exist", FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
                    for(DataSnapshot childrens : snapshot.getChildren()){
                        Log.i("child", childrens.child("chatroomId").getValue().toString());
                        final ChatList obj = new ChatList(childrens.child("chatroomId").getValue().toString() , childrens.child("hostelNo").getValue().toString());
                        DocumentReference db = FirebaseFirestore.getInstance().collection("Hostels").document(obj.getHostelNo());
                        FirebaseFirestore.getInstance().collection("Hostels").document(obj.getHostelNo()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                if(value.exists()){
                                    obj.setHostelName(value.get("hostelName").toString());
                                    obj.setProfilePicture(value.get("profilePicture").toString());
                                    chatList.add(obj);
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        });

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}