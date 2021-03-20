package com.xenecompany.xinihostel;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class studentDetails extends AppCompatActivity {
    ImageView studentDetailImage;
    TextView studentDetailDescription;
    ImageView studentAadharCard;
    ImageView studentInstituteCard;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);
        studentDetailImage=(ImageView)findViewById(R.id.studentDetailImage);
        studentDetailDescription=(TextView)findViewById(R.id.studentDetailDescription);
        studentAadharCard=(ImageView)findViewById(R.id.studentAadharCard);
        studentInstituteCard=(ImageView)findViewById(R.id.studentInstituteCard);
        String ItemId= getIntent().getStringExtra("ItemId").toString();
        FirebaseFirestore db=FirebaseFirestore.getInstance();
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
    }
}