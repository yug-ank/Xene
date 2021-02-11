package com.xenecompany.xene;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;


public class profile extends AppCompatActivity {
    EditText profileName;
    EditText profileEmail;
    EditText profileContact;
    EditText profileInstituteId;
    EditText profileInstituteName;
    EditText profileInstituteAddress;
    EditText profileAadharNo;
    EditText profileGuardianName;
    EditText profileGuardianAddress;
    EditText profileGuardianContact;
    FloatingActionButton edit_profile;
    FloatingActionButton save_profile;
    FloatingActionButton clear_profile;
    CircleImageView profileImage;
    String user_no;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        profileImage=(CircleImageView)findViewById(R.id.profileImage);
        profileName=(EditText)findViewById(R.id.profileName);
        profileEmail=(EditText)findViewById(R.id.profileEmail);
        profileContact=(EditText)findViewById(R.id.profileContact);
        profileInstituteId=(EditText)findViewById(R.id.profileInstituteId);
        profileInstituteName=(EditText)findViewById(R.id.profileInstituteName);
        profileInstituteAddress=(EditText)findViewById(R.id.profileInstituteAddress);
        profileAadharNo=(EditText)findViewById(R.id.profileAadharNo);
        profileGuardianName=(EditText)findViewById(R.id.profileGuardianName);
        profileGuardianAddress=(EditText)findViewById(R.id.profileGuardianAddress);
        profileGuardianContact=(EditText)findViewById(R.id.profileGuardianContact);
        edit_profile=(FloatingActionButton)findViewById(R.id.edit_profile);
        save_profile=(FloatingActionButton)findViewById(R.id.save_profile);
        clear_profile=(FloatingActionButton)findViewById(R.id.clear_profile);
   //     Picasso.with(getApplicationContext()).load(R.drawable.demo_profile).noFade().into(profileImage);
        SessionManager sessionManager=new SessionManager(profile.this);
        final HashMap<String , String> sessionData=sessionManager.getUserDetailFromSession();
        final FirebaseFirestore db=FirebaseFirestore.getInstance();
        db.collection("Student").document("+91"+sessionData.get(SessionManager.Key_Phone_no)).addSnapshotListener(
                new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                            if(value.exists()) {
                                if(value.get("name").toString().trim().length()>0){
                                    profileName.setText(value.get("name").toString());
                                }
                                if(value.get("email").toString().trim().length()>0){
                                    profileEmail.setText(value.get("email").toString());
                                }
                                if(value.get("phoneNo").toString().trim().length()>0){
                                    profileContact.setText(value.get("phoneNo").toString());
                                }
                                if(value.get("instituteId").toString().trim().length()>0){
                                    profileInstituteId.setText(value.get("instituteId").toString());
                                }
                                if(value.get("instituteName").toString().trim().length()>0){
                                    profileInstituteName.setText(value.get("instituteName").toString());
                                }
                                if(value.get("instituteAddress").toString().trim().length()>0){
                                    profileInstituteAddress.setText(value.get("instituteAddress").toString());
                                }
                                if(value.get("aadharNo").toString().trim().length()>0){
                                    profileAadharNo.setText(value.get("aadharNo").toString());
                                }
                                if(value.get("gaurdianName").toString().trim().length()>0){
                                    profileGuardianName.setText(value.get("gaurdianName").toString());
                                }
                                if(value.get("gaurdianContactNo").toString().trim().length()>0){
                                    profileGuardianContact.setText(value.get("gaurdianContactNo").toString());
                                }
                                if(value.get("gaurdianAddress").toString().trim().length()>0){
                                    profileGuardianAddress.setText(value.get("gaurdianAddress").toString());
                                }
                            }
                    }
                });
        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profileName.setEnabled(true);
                profileEmail.setEnabled(true);
                profileContact.setEnabled(true);
                profileInstituteId.setEnabled(true);
                profileInstituteName.setEnabled(true);
                profileInstituteAddress.setEnabled(true);
                profileAadharNo.setEnabled(true);
                profileGuardianName.setEnabled(true);
                profileGuardianContact.setEnabled(true);
                profileGuardianAddress.setEnabled(true);
                edit_profile.setVisibility(View.GONE);
                save_profile.setVisibility(View.VISIBLE);
                clear_profile.setVisibility(View.VISIBLE);
            }
        });
        save_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String , Object> data=new HashMap<>();
                data.put("name" , profileName.getText().toString());
                data.put("email" , profileEmail.getText().toString());
                data.put("instituteId" ,profileInstituteId.getText().toString());
                data.put("instituteName" , profileInstituteName.getText().toString());
                data.put("instituteAddress" , profileInstituteAddress.getText().toString());
                data.put("instituteIdCard" , "");
                data.put("aadharId" , "");
                data.put("aadharNo" , profileAadharNo.getText().toString());
                data.put("gaurdianName" , profileGuardianName.getText().toString());
                data.put("gaurdianAddress" , profileGuardianAddress.getText().toString());
                data.put("gaurdianContactNo" ,profileGuardianContact.getText().toString());
                data.put("profilePicture" , "");
                data.put("profileCompleted"  , false);
                db.collection("Student").document("+91"+sessionData.get(SessionManager.Key_Phone_no)).update(data)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(profile.this , "profie updated successfully" , Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(profile.this , ""+e , Toast.LENGTH_SHORT).show();
                    }
                });
                profileName.setEnabled(false);
                profileEmail.setEnabled(false);
                profileContact.setEnabled(false);
                profileInstituteId.setEnabled(false);
                profileInstituteName.setEnabled(false);
                profileInstituteAddress.setEnabled(false);
                profileAadharNo.setEnabled(false);
                profileGuardianName.setEnabled(false);
                profileGuardianContact.setEnabled(false);
                profileGuardianAddress.setEnabled(false);
                save_profile.setVisibility(View.GONE);
                clear_profile.setVisibility(View.GONE);
                edit_profile.setVisibility(View.VISIBLE);
            }
        });
        clear_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profileName.setEnabled(false);
                profileEmail.setEnabled(false);
                profileContact.setEnabled(false);
                profileInstituteId.setEnabled(false);
                profileInstituteName.setEnabled(false);
                profileInstituteAddress.setEnabled(false);
                profileAadharNo.setEnabled(false);
                profileGuardianName.setEnabled(false);
                profileGuardianContact.setEnabled(false);
                profileGuardianAddress.setEnabled(false);
                save_profile.setVisibility(View.GONE);
                clear_profile.setVisibility(View.GONE);
                edit_profile.setVisibility(View.VISIBLE);
            }
        });
    }
}