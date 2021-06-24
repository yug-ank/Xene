package com.xenecompany.xene;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.xenecompany.xene.adapter.pgDetailsAdapter;
import com.xenecompany.xene.model.pgDetailsModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class pgDetails extends AppCompatActivity {
    private Button wishlist_button;
    private Button book_button;
    private FirebaseFirestore db;
    private SessionManager sessionManager;
    private HashMap<String , String> sessionData;
    private ArrayList<pgDetailsModel> data;
    private ViewPager2 pager;
    private TabLayout tablayout;
    private LinearLayout linearLayout;
    private LinearLayout linearLayout1;
    private ImageView utilitiesIcon;
    private TextView nameOfPg;
    private TextView areaOfPg;
    private TextView rent;
    private TextView description;
    private RatingBar ratingBar;
    private ScrollView scrollView;
    private DatabaseReference db0;
    private Button startchat;
    private Map<String , Integer> iconString2Int;
    private Integer[] icons={
            R.drawable.ic_bed
            ,R.drawable.ic_cabinet
            ,R.drawable.ic_desk
            ,R.drawable.ic_revolving_chair
            ,R.drawable.ic_washing_machine
            ,R.drawable.ic_spoon
            ,R.drawable.ic_cooler
            ,R.drawable.ic_camera
            ,R.drawable.ic_security_gate
            ,R.drawable.ic_parking
            ,R.drawable.ic_maid
    };
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pg_details);

        db = FirebaseFirestore.getInstance();
        sessionManager = new SessionManager(pgDetails.this);
        sessionData=sessionManager.getUserDetailFromSession();

        final String hostelId=getIntent().getStringExtra("ItemId");

        token = getIntent().getStringExtra("token");
        setViews();
        setValues();
        initialiseIconString2Int();
        viewPagerConfig();
        setUtilitiesIcon();

        /// start chat
        startchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hostelMobNo = getIntent().getStringExtra("ItemId");
                String currentUserMobNo = FirebaseAuth.getInstance()
                        .getCurrentUser()
                        .getPhoneNumber();

                db0 = FirebaseDatabase.getInstance().getReference()
                        .child("user")
                        .child(currentUserMobNo)
                        .child(hostelMobNo);

                DatabaseReference db1 = db0.child("chatroomId");
                String chatroomId = currentUserMobNo+hostelMobNo;

                Map obj = new HashMap<>();

                obj.put(chatroomId, true);
                db1.updateChildren(obj);
                obj.clear();

                obj.put("hostelNo", hostelMobNo);
                db0.updateChildren(obj);
                obj.clear();

                db0 = FirebaseDatabase.getInstance().getReference()
                        .child("hostel")
                        .child(hostelMobNo)
                        .child(currentUserMobNo);

                db1 = db0.child("chatroomId");

                obj.put(chatroomId, true);
                db1.updateChildren(obj);
                obj.clear();

                obj.put("userNumber", currentUserMobNo);
                db0.updateChildren(obj);
                obj.clear();

                db0 = FirebaseDatabase.getInstance().getReference()
                        .child("chatrooms")
                        .child(chatroomId);

                String msgId = chatroomId;

                db1 = db0.child(msgId);
                obj.put("sender", "z");
                obj.put("text", true);
                obj.put("seen", false);
                obj.put("time", "time");
                db1.updateChildren(obj);

                FirebaseFirestore.getInstance().collection("Hostels")
                        .document(hostelMobNo)
                        .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(value.exists()){
                            Intent intent = new Intent(v.getContext(), ChatPersonal.class);
                            intent.putExtra(
                                    "name"
                                    ,value.get("hostelName").toString()
                            );
                            intent.putExtra(
                                    "profilePicture"
                                    ,value.get("profilePicture").toString()
                            );
                            intent.putExtra("chatroom" ,chatroomId);
                            v.getContext().startActivity(intent);
                        }
                    }
                });
            }
        });
        /// start chat


        ///wishlist buttons code
        wishlist_button=(Button) findViewById(R.id.wishlist_button);
        db.collection("Student").document("+91" + sessionData.get(SessionManager.Key_Phone_no)).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value.exists()){
                    List<String> wishlist= (List<String>) value.get("wishlist");
                    if(wishlist.contains(hostelId)){
                        wishlist_button.setText("REMOVE FROM WISHLIST");
                    }
                }
            }
        });
        wishlist_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                      if(wishlist_button.getText().toString().equals("ADD TO WISHLIST")) {
                        db.collection("Student").document("+91" + sessionData.get(SessionManager.Key_Phone_no))
                                .update("wishlist", FieldValue.arrayUnion(hostelId)).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(pgDetails.this, "Successfully added to wishlist", Toast.LENGTH_SHORT).show();
                                wishlist_button.setText("REMOVE FROM WISHLIST");
                            }
                        });
                    }
                    else{
                        db.collection("Student").document("+91" + sessionData.get(SessionManager.Key_Phone_no))
                                .update("wishlist", FieldValue.arrayRemove(hostelId)).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(pgDetails.this, "Successfully removed from wishlist", Toast.LENGTH_SHORT).show();
                                wishlist_button.setText("ADD TO WISHLIST");
                            }
                        });
                    }
                }
            });
        ///wishlist buttons code

        ///book button code
            book_button=(Button) findViewById(R.id.book_button);
            db.collection("Student").document("+91" + sessionData.get(SessionManager.Key_Phone_no)).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    if(value.exists()){
                        List<String> requested= (List<String>) value.get("requested");
                        if(requested.contains(hostelId)){
                            book_button.setText("CANCEL REQUEST");
                        }
                    }
                }
            });
             db.collection("Student").document("+91" + sessionData.get(SessionManager.Key_Phone_no)).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    if(value.exists()){
                         List<String> accepted= (List<String>) value.get("accepted");
                        if(accepted.contains(hostelId)){
                            book_button.setText("REMOVE CONTACT");
                        }
                    }
                }
            });
        book_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(book_button.getText().toString().equals("BOOK")) {
                    db.collection("Student").document("+91" + sessionData.get(SessionManager.Key_Phone_no))
                            .update("requested", FieldValue.arrayUnion(hostelId)).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(pgDetails.this, "Successfully requested", Toast.LENGTH_SHORT).show();
                        }
                    });
                    db.collection("Hostels").document(hostelId).
                            update("requested" , FieldValue.arrayUnion("+91" + sessionData.get(SessionManager.Key_Phone_no)));
                    String title="Request for contact";
                    String message="A student has requested for contact...";

                     new NotificationSend(token , message ,title);
                    book_button.setText("CANCEL REQUEST");
                }
                else if(book_button.getText().toString().equals("CANCEL REQUEST")){
                    db.collection("Student").document("+91" + sessionData.get(SessionManager.Key_Phone_no))
                            .update("requested", FieldValue.arrayRemove(hostelId)).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(pgDetails.this, "Successfully canceled request", Toast.LENGTH_SHORT).show();
                        }
                    });
                    db.collection("Hostels").document(hostelId).
                            update("requested" , FieldValue.arrayRemove("+91" + sessionData.get(SessionManager.Key_Phone_no)));
                    book_button.setText("BOOK");
                }
                else{
                    db.collection("Student").document("+91" + sessionData.get(SessionManager.Key_Phone_no))
                            .update("accepted", FieldValue.arrayRemove(hostelId)).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(pgDetails.this, "Successfully canceled request", Toast.LENGTH_SHORT).show();
                        }
                    });
                    db.collection("Hostels").document(hostelId).
                            update("accepted" , FieldValue.arrayRemove("+91" + sessionData.get(SessionManager.Key_Phone_no)));
                    book_button.setText("BOOK");
                }
            }
        });
        ///book button code
    }

    private void initialiseIconString2Int() {
        iconString2Int = new HashMap<>();
        iconString2Int.put("bed" , 0);
        iconString2Int.put("almirah" , 1);
        iconString2Int.put("table" , 2);
        iconString2Int.put("chair" , 3);
        iconString2Int.put("laundry" , 4);
        iconString2Int.put("mess" , 5);
        iconString2Int.put("Cooler" , 6);
        iconString2Int.put("CCTV" , 7);
        iconString2Int.put("Security Gaurd" , 8);
        iconString2Int.put("Parking" , 9);
        iconString2Int.put("House Keeping" , 10);
    }

    private void setViews(){
        startchat = (Button) findViewById(R.id.layoutPgPictures_startchat);
        ratingBar = (RatingBar) findViewById(R.id.layoutPgPictures_RatingBar);
        nameOfPg = (TextView) findViewById(R.id.layoutPgPictures_NameOfPg);
        areaOfPg = (TextView) findViewById(R.id.layoutPgPictures_AreaOfPg);
        rent = (TextView) findViewById(R.id.price_pgdetails);
        description = (TextView) findViewById(R.id.layoutPgDescription_Description);
    }

    private void setValues(){

        FirebaseFirestore.getInstance()
                .collection("Hostels")
                .document(getIntent().getStringExtra("ItemId"))
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value.exists()){
                    nameOfPg.setText(value.get("hostelName").toString());
                    areaOfPg.setText(value.get("hostelAddress").toString());
                    ratingBar.setRating(Float.parseFloat(value.get("rating").toString()));
                    rent.setText(value.get("price").toString());
                    description.setText(value.get("description").toString());
                }
            }
        });

    }

    private void setUtilitiesIcon(){
        linearLayout = findViewById(R.id.pgPicturesUtilitiesLayout);
        FirebaseFirestore.getInstance()
                .collection("Hostels")
                .document(getIntent().getStringExtra("ItemId"))
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(value.exists()) {
                            ArrayList<String> utilities;
                            utilities = (ArrayList<String>) value.get("hostelFacilities");
                            for( String i : utilities){
                                utilitiesIcon = new ImageView(pgDetails.this);
                                if(iconString2Int.get(i) != null) {
                                    utilitiesIcon.setImageResource(icons[iconString2Int.get(i)]);
                                }
                                utilitiesIcon.setPadding(12,6,12,0);
                                linearLayout.addView(utilitiesIcon);
                            }
                        }
                    }
                });
    }

    private void viewPagerConfig(){
        data = new ArrayList<>(4);
        FirebaseFirestore.getInstance().collection("Hostels").document(getIntent().getStringExtra("ItemId")).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value.exists()){
                    data.add(new pgDetailsModel(value.get("hostelImage1").toString().trim()));
                    data.add(new pgDetailsModel(value.get("hostelImage2").toString().trim()));
                    data.add(new pgDetailsModel(value.get("hostelImage3").toString().trim()));
                    data.add(new pgDetailsModel(value.get("hostelImage4").toString().trim()));
                }
            }
        });

        pager = findViewById(R.id.viewPager22);
        pager.setAdapter(new pgDetailsAdapter(data,this));
    }

}