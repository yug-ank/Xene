package com.xenecompany.xinihostel;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class chatAll extends AppCompatActivity {
    ArrayList<userObject> chatList;
    RecyclerView recyclerView;
    ImageView searchIcon;
    SearchView searchView;
    public  chat_all_adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_all);
        searchIcon = findViewById(R.id.activityChatAll_searchIcon);
        searchView = findViewById(R.id.activityChatAll_searchView);
        recyclerView = (RecyclerView) findViewById(R.id.activityChatAll_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(false);
        DisplayMetrics displayMetrics= new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width=displayMetrics.widthPixels;
        int height=displayMetrics.heightPixels;
        getCHatList();
        adapter = new chat_all_adapter(chatList , this , width , height);
        recyclerView.setAdapter(adapter);

        ///search
        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchIcon.setVisibility(View.GONE);
                findViewById(R.id.activityChatAll_textViewMessage).setVisibility(View.GONE);
                searchView.setVisibility(View.VISIBLE);
                searchView.setIconifiedByDefault(false);
                searchView.requestFocus();
                Log.i("yash", searchView.isInEditMode()+"");
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });


    }

    private void filter(String newText) {
        ArrayList<userObject> filterList = new ArrayList<>();
        for(userObject temp : chatList){
            if(temp.getUserName().toLowerCase().contains(newText.toLowerCase()))
                filterList.add(temp);
        }
        adapter.filteredList(filterList);
    }


    void getCHatList(){
        chatList = new ArrayList<>();
//        Log.i("mob no", FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("hostel").child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()));
//        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("hostel").child("917339703405");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot childrens : snapshot.getChildren()){
                        String temp = "";
                        for(DataSnapshot chatroom : childrens.child("chatroomId").getChildren())
                            temp = chatroom.getKey();
                        final userObject obj = new userObject( temp , childrens.child("userNumber").getValue().toString());
                    Log.i("chatroom and number ",obj.getChatroomId()+" "+obj.getUserNo());
                        FirebaseFirestore.getInstance().collection("Student").document(obj.getUserNo()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                if(value.exists()){
                                    if(value.get("name") != null)
                                        obj.setUserName(value.get("name").toString());
                                    if(value.get("profilePicture") != null)
                                        obj.setProfilePicture(value.get("profilePicture").toString());
                                    Log.i("name", obj.getUserName()+" "+obj.getProfilePicture());
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