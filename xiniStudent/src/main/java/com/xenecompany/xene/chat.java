package com.xenecompany.xene;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class chat {

    void getCHatList(){
        final ArrayList<ChatList> chatList = new ArrayList<>();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot childrens : snapshot.getChildren()){
                        ChatList obj = new ChatList(childrens.child("chatroomId").getValue().toString() , childrens.child("hostelName").getValue().toString());
                        chatList.add(obj);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    void getChat(){

    }
}

