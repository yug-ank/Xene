package com.xenecompany.xinihostel;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class studentDetails extends AppCompatActivity {
    ImageView studentDetailImage;
    TextView studentDetailDescription;
    ImageView studentAadharCard;
    ImageView studentInstituteCard;
    FloatingActionButton startchat;
    Button book_button;
    Button wishlist_button;
    Button cancel_button;
    SessionManager sessionManager;
    HashMap<String , String> sessionData;
    EditText profileName;
    EditText editdetailEmail;
    EditText editdetailPhoneNumber;
    EditText editdetailAdharNumber;
    EditText editdetailInstituteName;
    EditText editdetailInstituteAddress;
    EditText editdetailInstituteIDNumber;
    EditText editdetailGuardianName;
    EditText editdetailGuardianContact;
    EditText editdetailGuardianAddress;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);
        studentDetailImage=(ImageView)findViewById(R.id.studentDetailImage);
        startchat = findViewById(R.id.layoutPgPictures_startchat);
        profileName=(EditText)findViewById(R.id.profileName);
        editdetailEmail=(EditText)findViewById(R.id.editdetailEmail);
        editdetailPhoneNumber=(EditText)findViewById(R.id.editdetailPhoneNumber);
        editdetailAdharNumber=(EditText)findViewById(R.id.editdetailAdharNumber);
        editdetailInstituteName=(EditText)findViewById(R.id.editdetailInstituteName);
        editdetailInstituteAddress=(EditText)findViewById(R.id.editdetailInstituteAddress);
        editdetailInstituteIDNumber=(EditText)findViewById(R.id.editdetailInstituteIDNumber);
        editdetailGuardianName=(EditText)findViewById(R.id.editdetailGuardianName);
        editdetailGuardianContact=(EditText)findViewById(R.id.editdetailGuardianContact);
        editdetailGuardianAddress=(EditText)findViewById(R.id.editdetailGuardianAddress);
        studentAadharCard=(ImageView)findViewById(R.id.studentAadharCard);
        studentInstituteCard=(ImageView)findViewById(R.id.studentInstituteCard);
        sessionManager= new SessionManager(studentDetails.this);
        sessionData=sessionManager.getUserDetailFromSession();
        final String ItemId= getIntent().getStringExtra("ItemId");
        final String token=getIntent().getStringExtra("token");
        Log.i("rectify" , ""+token);
        final FirebaseFirestore db=FirebaseFirestore.getInstance();
        db.collection("Student").document(ItemId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value.exists()){
                     if(value.get("profilePicture").toString().length()>0){
                         Picasso.get().load(value.get("profilePicture").toString()).noFade().fit().into(studentDetailImage, new Callback() {
                             @Override
                             public void onSuccess() {

                             }

                             @Override
                             public void onError(Exception e) {
                                 Toast.makeText(studentDetails.this , ""+e , Toast.LENGTH_LONG).show();
                             }
                         });
                     }
                }
                else{
                    Picasso.get().load(R.drawable.ic_male_avatr).noFade().fit()
                            .into(studentDetailImage);
                }
                if(value.get("aadharId").toString().length()>0){
                    Picasso.get().load(value.get("aadharId").toString()).noFade().fit().into(studentAadharCard, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(Exception e) {
                            Toast.makeText(studentDetails.this , ""+e , Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else{
                Picasso.get().load(R.drawable.ic_male_avatr).noFade().fit()
                        .into(studentAadharCard);
            }
                if(value.get("instituteIdCard").toString().length()>0){
                    Picasso.get().load(value.get("instituteIdCard").toString()).noFade().fit().into(studentInstituteCard, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(Exception e) {
                            Toast.makeText(studentDetails.this , ""+e , Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else{
                Picasso.get().load(R.drawable.ic_male_avatr).noFade().fit()
                        .into(studentInstituteCard);
            }
                String data="";
                if(value.get("name").toString().trim().length()>0){
                    profileName.setText(value.get("name").toString());
                }
                if(value.get("email").toString().trim().length()>0){
                    editdetailEmail.setText(value.get("email").toString());
                }
                if(value.get("phoneNo").toString().trim().length()>0){
                    editdetailPhoneNumber.setText(value.get("phoneNo").toString());
                }
                if(value.get("aadharNo").toString().trim().length()>0){
                    editdetailAdharNumber.setText(value.get("aadharNo").toString());
                }
                if(value.get("instituteName").toString().trim().length()>0){
                    editdetailInstituteName.setText(value.get("instituteName").toString());
                }
                if(value.get("instituteAddress").toString().trim().length()>0){
                    editdetailInstituteAddress.setText(value.get("instituteAddress").toString());
                }
                if(value.get("instituteId").toString().trim().length()>0){
                    editdetailInstituteIDNumber.setText(value.get("instituteId").toString());
                }
                if(value.get("gaurdianName").toString().trim().length()>0){
                    editdetailGuardianName.setText(value.get("gaurdianName").toString());
                }
                if(value.get("gaurdianContactNo").toString().trim().length()>0){
                    editdetailGuardianContact.setText(value.get("gaurdianContactNo").toString());
                }
                if(value.get("gaurdianAddress").toString().trim().length()>0){
                    editdetailGuardianAddress.setText(value.get("gaurdianAddress").toString());
                }
            }
        });

        ///wishlist buttons code
        wishlist_button=(Button) findViewById(R.id.wishlist_button);
        db.collection("Hostels").document("+91" + sessionData.get(SessionManager.Key_Phone_no)).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value.exists()){
                    List<String> wishlist= (List<String>) value.get("wishlist");
                    if(wishlist.size()!=0 && wishlist.contains(ItemId)){
                        wishlist_button.setText("REMOVE FROM WISHLIST");
                    }
                }
            }
        });
        wishlist_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(wishlist_button.getText().toString().equals("ADD TO WISHLIST")) {
                    db.collection("Hostels").document("+91" + sessionData.get(SessionManager.Key_Phone_no))
                            .update("wishlist", FieldValue.arrayUnion(ItemId)).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(studentDetails.this, "Successfully added to wishlist", Toast.LENGTH_SHORT).show();
                            wishlist_button.setText("REMOVE FROM WISHLIST");
                            wishlist_button.setBackgroundResource(R.drawable.toolbar_heart);
                        }
                    });
                }
                else{
                    db.collection("Hostels").document("+91" + sessionData.get(SessionManager.Key_Phone_no))
                            .update("wishlist", FieldValue.arrayRemove(ItemId)).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(studentDetails.this, "Successfully removed from wishlist", Toast.LENGTH_SHORT).show();
                            wishlist_button.setText("ADD TO WISHLIST");
                            wishlist_button.setBackgroundResource(R.drawable.toolbar_heart_selected);

                        }
                    });
                }
            }
        });
        ///wishlist buttons code

        ///book button code
        book_button=(Button) findViewById(R.id.button_accept);
        cancel_button=(Button) findViewById(R.id.button_reject);
        db.collection("Hostels").document("+91" + sessionData.get(SessionManager.Key_Phone_no)).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value.exists()){
                    List<String> wishlist= (List<String>) value.get("requested");
                    if(wishlist.contains(ItemId)){
                        book_button.setText("ACCEPT REQUEST");
                        startchat.setVisibility(View.GONE);
                    }
                }
            }
        });
        db.collection("Hostels").document("+91" + sessionData.get(SessionManager.Key_Phone_no)).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value.exists()){
                    List<String> wishlist= (List<String>) value.get("accepted");
                    if(wishlist.contains(ItemId)){
                        book_button.setText("REMOVE CONNECTION");
                        startchat.setVisibility(View.VISIBLE);
                        cancel_button.setText("SEND MESSAGE");
                    }
                }
            }
        });

        book_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(book_button.getText().toString().equals("ACCEPT REQUEST")) {
                    db.collection("Hostels").document("+91" + sessionData.get(SessionManager.Key_Phone_no))
                            .update("accepted", FieldValue.arrayUnion(ItemId));
                    db.collection("Student").document(ItemId).
                            update("accepted" , FieldValue.arrayUnion("+91" + sessionData.get(SessionManager.Key_Phone_no)));
                    db.collection("Hostels").document("+91" + sessionData.get(SessionManager.Key_Phone_no))
                            .update("requested", FieldValue.arrayRemove(ItemId));
                    db.collection("Student").document(ItemId).
                            update("requested" , FieldValue.arrayRemove("+91" + sessionData.get(SessionManager.Key_Phone_no)));
                    book_button.setText("REMOVE CONNECTION");
                     String title="Accepted your request";
                    String message="A hostel has accepted your request.\nNow you can contact the hostel";
                    new NotificationSend(token , message ,title);
                }
                else if(book_button.getText().equals("REMOVE CONNECTION")){
                    db.collection("Hostels").document("+91" + sessionData.get(SessionManager.Key_Phone_no))
                            .update("accepted", FieldValue.arrayRemove(ItemId));
                    db.collection("Student").document(ItemId)
                            .update("accepted", FieldValue.arrayRemove("+91" + sessionData.get(SessionManager.Key_Phone_no)));
                    Toast.makeText(studentDetails.this, "Press back to get to homepage", Toast.LENGTH_SHORT).show();
                    book_button.setVisibility(View.GONE);
                    wishlist_button.setVisibility(View.GONE);
                    cancel_button.setVisibility(View.GONE);

                    /// stop chat of that person
                    DatabaseReference flag = FirebaseDatabase.getInstance().getReference().child("chatrooms").child(ItemId+"+91"+sessionData.get(SessionManager.Key_Phone_no)).child(ItemId+"+91"+sessionData.get(SessionManager.Key_Phone_no)).child("text");
                    flag.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                flag.setValue(false);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    /// stop chat of that person
                }
            }
        });
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cancel_button.getText().toString().equals("CANCEL REQUEST")){
                    db.collection("Hostels").document("+91" + sessionData.get(SessionManager.Key_Phone_no))
                            .update("requested", FieldValue.arrayRemove(ItemId));
                    db.collection("Student").document(ItemId).
                            update("requested" , FieldValue.arrayRemove("+91" + sessionData.get(SessionManager.Key_Phone_no)));
                    Toast.makeText(studentDetails.this, "Press back to get to homepage", Toast.LENGTH_SHORT).show();
                    book_button.setEnabled(false);
                    cancel_button.setEnabled(false);
                    wishlist_button.setEnabled(false);
                }
            }
        });
        ///book button code

        ///startchat code
        startchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String studentMobNo = getIntent().getStringExtra("ItemId");
                String currentHostelOwnerMobNo = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
//                DatabaseReference db0 = FirebaseDatabase.getInstance().getReference("hostel\n"+currentHostelOwnerMobNo+"\n"+studentMobNo);
                DatabaseReference db0 = FirebaseDatabase.getInstance().getReference().child("hostel").child(currentHostelOwnerMobNo).child(studentMobNo);
                DatabaseReference db1 = db0.child("chatroomId");
//                String chatroomId = db1.push().getKey();
                String chatroomId = studentMobNo+currentHostelOwnerMobNo;
                Map obj = new HashMap<>();
                obj.put(chatroomId, true);
                db1.updateChildren(obj);
                obj.clear();
                obj.put("userNumber", studentMobNo);
                db0.updateChildren(obj);
                obj.clear();

                db0 = FirebaseDatabase.getInstance().getReference().child("user").child(studentMobNo).child(currentHostelOwnerMobNo);
                db1 = db0.child("chatroomId");
                obj.put(chatroomId, true);
                db1.updateChildren(obj);
                obj.clear();
                obj.put("hostelNo", currentHostelOwnerMobNo);
                db0.updateChildren(obj);
                obj.clear();

                db0 = FirebaseDatabase.getInstance().getReference().child("chatrooms").child(chatroomId);
//                String msgId = db0.push().getKey();
                String msgId = chatroomId;
                db1 = db0.child(msgId);
                obj.put("sender", "z");
                obj.put("text", true);
                obj.put("time", "time");
                obj.put("seen", false);
                db1.updateChildren(obj);


                FirebaseFirestore.getInstance().collection("Student").document(studentMobNo).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(value.exists()){
                            Intent intent = new Intent(v.getContext(), ChatPersonal.class);
                            intent.putExtra("name" , value.get("name").toString());
                            intent.putExtra("profilePicture", value.get("profilePicture").toString());
                            intent.putExtra("chatroom", chatroomId);
                            v.getContext().startActivity(intent);
                        }
                    }
                });
                /*else {
                    db0.child("chatroomId").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                            if (snapshot.exists()) {
                                String chatroomId = snapshot.getKey();
                                FirebaseFirestore.getInstance().collection("Student").document(studentMobNo).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                    @Override
                                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                        if(value.exists()){
                                            Intent intent = new Intent(v.getContext(), ChatPersonal.class);
                                            intent.putExtra("name" , value.get("name").toString());
                                            intent.putExtra("profilePicture", value.get("profilePicture").toString());
                                            intent.putExtra("chatroom", chatroomId);
                                            v.getContext().startActivity(intent);
                                        }
                                    }
                                });
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
                }*/
////
            }
        });
        ///startchat code


    }
}