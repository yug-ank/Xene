package com.xenecompany.xene;

import androidx.activity.ComponentActivity;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ServerValue;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ServerTimestamp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.type.Date;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.w3c.dom.Text;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
    TextView profileIdCardTextView;
    TextView profileAadharIdCardTextView;
    FloatingActionButton edit_profile;
    FloatingActionButton save_profile;
    FloatingActionButton clear_profile;
    FloatingActionButton edit_profilePicture;
    CircleImageView profileImage;
    ImageView editProfileIdCard;
    ImageView editProfileAadharIdCard;
    FirebaseFirestore db;
    StorageReference storageReference;
    HashMap<String , String> sessionData;
    String profilePicture="";
    String aadharCardPicture="";
    String instituteIdPicture="";
    FirebaseAuth auth;
    int PROFILE_PICTURE=0 , AADHAR_ID=0 , INSTITUE_ID=0;
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
        edit_profilePicture=(FloatingActionButton)findViewById(R.id.edit_profile_picture);
        editProfileIdCard=(ImageView)findViewById(R.id.editProfileIdCard);
        editProfileAadharIdCard=(ImageView)findViewById(R.id.editProfileAadharIdCard);
        profileIdCardTextView=(TextView)findViewById(R.id.profileIdCard);
        profileAadharIdCardTextView=(TextView)findViewById(R.id.profileAadharIdCard);
        SessionManager sessionManager=new SessionManager(profile.this);
        sessionData=sessionManager.getUserDetailFromSession();
        db=FirebaseFirestore.getInstance();
        storageReference= FirebaseStorage.getInstance().getReference("StudentImages");
        auth=FirebaseAuth.getInstance();
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
                                if(value.get("profilePicture").toString().trim().length()>0) {
                                    profilePicture=value.get("profilePicture").toString();
                                    Picasso.get().load(value.get("profilePicture").toString()).noFade().into(profileImage, new Callback() {
                                        @Override
                                        public void onSuccess() {

                                        }

                                        @Override
                                        public void onError(Exception e) {
                                            Toast.makeText(profile.this, "" + e, Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                                if(value.get("aadharId").toString().trim().length()>0) {
                                    aadharCardPicture=value.get("aadharId").toString();
                                    profileAadharIdCardTextView.setText("aadhar"+auth.getCurrentUser().getUid());
                                }
                                if(value.get("instituteIdCard").toString().trim().length()>0) {
                                    instituteIdPicture=value.get("instituteIdCard").toString();
                                    profileIdCardTextView.setText("institute"+auth.getCurrentUser().getUid());
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
                data.put("instituteIdCard" , instituteIdPicture);
                data.put("aadharId" , aadharCardPicture);
                data.put("aadharNo" , profileAadharNo.getText().toString());
                data.put("gaurdianName" , profileGuardianName.getText().toString());
                data.put("gaurdianAddress" , profileGuardianAddress.getText().toString());
                data.put("gaurdianContactNo" ,profileGuardianContact.getText().toString());
                data.put("profilePicture" , profilePicture);
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
        edit_profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity().setAllowRotation(true).setAllowFlipping(true)
                        .setAspectRatio(1 ,1).setFixAspectRatio(true).
                        setCropShape(CropImageView.CropShape.OVAL).setRequestedSize(300 ,300).
                        start(profile.this);
                        PROFILE_PICTURE=1;
                        AADHAR_ID=0;
                        INSTITUE_ID=0;
            }
        });
        editProfileIdCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity().setAllowRotation(true).setAllowFlipping(true)
                        .setAspectRatio(1 ,1).setFixAspectRatio(true).
                        setCropShape(CropImageView.CropShape.OVAL).setRequestedSize(300 ,300).
                        start(profile.this);
                PROFILE_PICTURE=0;
                AADHAR_ID=0;
                INSTITUE_ID=1;
            }
        });
        editProfileAadharIdCard.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                CropImage.activity().setAllowRotation(true).setAllowFlipping(true)
                        .setAspectRatio(1 ,1).setFixAspectRatio(true).
                        setCropShape(CropImageView.CropShape.OVAL).setRequestedSize(300 ,300).
                        start(profile.this);
                PROFILE_PICTURE=0;
                AADHAR_ID=1;
                INSTITUE_ID=0;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(profile.this, "" + result.getError(), Toast.LENGTH_SHORT).show();
            }
            else{
                Uri imageUri = result.getUri();
                String name="";
                if(PROFILE_PICTURE==1) {
                    name = "profile" + auth.getCurrentUser().getUid()+"."+getExtension(imageUri);
                }
                if(AADHAR_ID==1){
                    name = "aadhar" + auth.getCurrentUser().getUid()+"."+getExtension(imageUri);
                }
                if(INSTITUE_ID==1){
                    name="institute"+auth.getCurrentUser().getUid()+"."+getExtension(imageUri);
                }
                final StorageReference imgref=storageReference.child(name);
                UploadTask uploadTask=imgref.putFile(imageUri);
                uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful()){
                                throw task.getException();
                        }
                        return imgref.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){
                                Map<String , Object> data=new HashMap<>();
                                if(PROFILE_PICTURE==1) {
                                    data.put("profilePicture", task.getResult().toString());
                                }
                                if(AADHAR_ID==1){
                                    data.put("aadharId", task.getResult().toString());
                                }
                                if(INSTITUE_ID==1){
                                    data.put("instituteIdCard", task.getResult().toString());
                                }
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

                        }else if(!task.isSuccessful()){
                            Toast.makeText(profile.this , ""+task.getException().toString() , Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }
    private String getExtension(Uri uri){
        try{
            ContentResolver contentResolver=getContentResolver();
            MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
            return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
        }
        catch (Exception e){
                Toast.makeText(profile.this , ""+e , Toast.LENGTH_SHORT).show();
        }
        return null;
    }
}