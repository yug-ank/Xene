package com.xenecompany.xinihostel;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static android.view.View.GONE;

public class studentDetails extends AppCompatActivity {
    ImageView studentDetailImage;
    TextView studentDetailDescription;
    ImageView studentAadharCard;
    ImageView studentInstituteCard;
    Button book_button;
    Button wishlist_button;
    Button cancel_button;
    SessionManager sessionManager;
    HashMap<String , String> sessionData;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);
        studentDetailImage=(ImageView)findViewById(R.id.studentDetailImage);
        studentDetailDescription=(TextView)findViewById(R.id.studentDetailDescription);
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
                data+="Name: "+value.get("name").toString()+"\n";
                data+="Email: "+value.get("email").toString()+"\n";
                data+="PhoneNo: "+value.get("phoneNo").toString()+"\n";
                data+="AadharNO: "+value.get("aadharNo").toString()+"\n";
                data+="Institute Name: "+value.get("instituteName").toString()+"\n";
                data+="Institute Address: "+value.get("instituteAddress").toString()+"\n";
                data+="Institute Id: "+value.get("instituteId").toString()+"\n";
                data+="Gaurdian Name: "+value.get("gaurdianName").toString()+"\n";
                data+="Gaurdian PhoneNo: "+value.get("gaurdianContactNo").toString()+"\n";
                data+="Gaurdian Address: "+value.get("gaurdianAddress").toString()+"\n";
                studentDetailDescription.setText(data);
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
                        }
                    });
                }
            }
        });
        ///wishlist buttons code

        ///book button code
        book_button=(Button) findViewById(R.id.book_button);
        cancel_button=(Button) findViewById(R.id.cancel_button);
        db.collection("Hostels").document("+91" + sessionData.get(SessionManager.Key_Phone_no)).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value.exists()){
                    List<String> wishlist= (List<String>) value.get("requested");
                    if(wishlist.contains(ItemId)){
                        book_button.setText("ACCEPT REQUEST");
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
                    book_button.setVisibility(GONE);
                    wishlist_button.setVisibility(GONE);
                    cancel_button.setVisibility(GONE);
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
    }
}