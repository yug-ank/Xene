
package com.xenecompany.xene;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    ArrayList<userObject> chatList;
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

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        adapter = new chat_all_adapter(
                chatList , this , width , height);

        recyclerView.setAdapter(adapter);
    }

    void getCHatList(){
        chatList = new ArrayList<>();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference()
                .child("user")
                .child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot childrens : snapshot.getChildren()){

                        if(childrens.getKey().charAt(0) == 's') {
                            continue;
                        }

                        String temp = "";

                        for(DataSnapshot chatroom : childrens.child("chatroomId").getChildren()) {
                            temp = chatroom.getKey();
                        }
                        final userObject obj =
                                new userObject(temp , childrens.child("hostelNo").getValue().toString());

                        // is the user online
                        FirebaseDatabase.getInstance().getReference()
                                .child("hostel")
                                .child(obj.getHostelNo())
                                .child("status")
                                .addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if(snapshot.exists()){
                                            obj.setIsOnline((Boolean) snapshot.getValue());
                                            Log.i("rectify", obj.getIsOnline()+"");
                                            adapter.notifyDataSetChanged();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                        //is the user online

                        DocumentReference db = FirebaseFirestore.getInstance()
                                .collection("Hostels")
                                .document(obj.getHostelNo());

                        FirebaseFirestore.getInstance()
                                .collection("Hostels")
                                .document(obj.getHostelNo())
                                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                    @Override
                                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                        if(value.exists()){
                                            obj.setHostelName(value.get("hostelName").toString());
                                            obj.setProfilePicture(value.get("profilePicture").toString());
                                            chatList.add(obj);
                                            Log.i("object received :", ""+obj.getHostelName());
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