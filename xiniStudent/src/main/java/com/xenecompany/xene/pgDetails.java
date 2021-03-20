package com.xenecompany.xene;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

public class pgDetails extends AppCompatActivity {
    Button wishlist_button , book_button;
    FirebaseFirestore db;
    SessionManager sessionManager;
    HashMap<String , String> sessionData;
    private ArrayList<pgDetailsModel> data;
    private ViewPager2 pager;
    private TabLayout tablayout;
    private LinearLayout linearLayout ,linearLayout1;
    private ImageView utilitiesIcon;
    private TextView nameOfPg ,areaOfPg ,rent ,description;
    private RatingBar ratingBar;
    private ScrollView scrollView;
    Integer[] icons={R.drawable.ic_bed,R.drawable.ic_cabinet,R.drawable.ic_desk,
                     R.drawable.ic_revolving_chair,R.drawable.ic_washing_machine,
                     R.drawable.ic_spoon};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pg_details);
        db=FirebaseFirestore.getInstance();
        sessionManager= new SessionManager(pgDetails.this);
        sessionData=sessionManager.getUserDetailFromSession();
        final String hostelId=getIntent().getStringExtra("ItemId");
        setViews();
        setValues();
        viewPagerConfig();
        setUtilitiesIcon();


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
                        List<String> wishlist= (List<String>) value.get("requested");
                        if(wishlist.contains(hostelId)){
                            book_button.setText("CANCEL REQUEST");
                        }
                    }
                }
            });
            db.collection("Student").document("+91" + sessionData.get(SessionManager.Key_Phone_no)).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    if(value.exists()){
                        List<String> wishlist= (List<String>) value.get("accepted");
                        if(wishlist.contains(hostelId)){
                            book_button.setText("REMOVE CONNECT");
                        }
                    }
                }
        });
        db.collection("Student").document("+91" + sessionData.get(SessionManager.Key_Phone_no)).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    if(value.exists()){
                         List<String> wishlist= (List<String>) value.get("accepted");
                        if(wishlist.contains(hostelId)){
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
                            .update("requested", FieldValue.arrayUnion(hostelId));
                    db.collection("Hostels").document(hostelId).
                            update("requested" , FieldValue.arrayUnion("+91" + sessionData.get(SessionManager.Key_Phone_no)));
                }
                else if(book_button.getText().equals("CANCEL REQUEST")){
                    db.collection("Student").document("+91" + sessionData.get(SessionManager.Key_Phone_no))
                            .update("requested", FieldValue.arrayRemove(hostelId));
                    db.collection("Hostels").document(hostelId).
                            update("requested" , FieldValue.arrayRemove("+91" + sessionData.get(SessionManager.Key_Phone_no)));
                    book_button.setText("BOOK");
                }
                else if(book_button.getText().equals("REMOVE CONNECTION")){
                    db.collection("Student").document("+91" + sessionData.get(SessionManager.Key_Phone_no))
                            .update("accepted", FieldValue.arrayRemove(hostelId));
                    db.collection("Hostels").document(hostelId)
                            .update("accepted", FieldValue.arrayRemove("+91" + sessionData.get(SessionManager.Key_Phone_no)));
                    book_button.setText("BOOK");
                }
            }
        });
        ///book button code
    }

    private void setViews(){
        ratingBar = (RatingBar) findViewById(R.id.layoutPgPictures_RatingBar);
        nameOfPg = (TextView) findViewById(R.id.layoutPgPictures_NameOfPg);
        areaOfPg = (TextView) findViewById(R.id.layoutPgPictures_AreaOfPg);
        rent = (TextView) findViewById(R.id.layoutPgPictures_Rent);
        description = (TextView) findViewById(R.id.layoutPgDescription_Description);
    }

    private void setValues(){
        ratingBar.setRating(2.5f);
        nameOfPg.setText("XYZ");
        areaOfPg.setText("area of pg");
        rent.setText("4000/-");
        description.setText(R.string.layout_pg_description_filler_text);
    }

    private void setUtilitiesIcon(){
        linearLayout = findViewById(R.id.pgPicturesUtilitiesLayout);
        for(int i=0;i<5;i++){
            utilitiesIcon = new ImageView(this);
            utilitiesIcon.setImageResource(R.drawable.bed);
            utilitiesIcon.setPadding(5,5,5,5);
            linearLayout.addView(utilitiesIcon);
        }
    }

    private void viewPagerConfig(){
        data = new ArrayList<>(4);
        data.add(new pgDetailsModel(R.drawable.demo_hostel));
        data.add(new pgDetailsModel(R.drawable.demo_hostel));
        data.add(new pgDetailsModel(R.drawable.demo_hostel));
        data.add(new pgDetailsModel(R.drawable.demo_hostel));

        pager = findViewById(R.id.viewPager22);
        pager.setAdapter(new pgDetailsAdapter(data,this));

//        CompositePageTransformer transformer = new CompositePageTransformer();
//        transformer.addTransformer(new MarginPageTransformer((8)));
//        transformer.addTransformer(new ViewPager2.PageTransformer() {
//            @Override
//            public void transformPage(@NonNull View page, float position) {
//                float v = 1 - Math.abs(position);
//                page.setScaleY(0.8f+v*0.2f);
//            }
//        });
//        pager.setPageTransformer(transformer);

//        tablayout = (TabLayout) findViewById(R.id.layoutPgPicturesTabLayout);
//        new TabLayoutMediator(tablayout ,pager ,
//                (tab , position) -> tab.select()).attach();
    }

}