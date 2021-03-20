package com.xenecompany.xinihostel;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import de.hdodenhof.circleimageview.CircleImageView;


public class profile extends AppCompatActivity implements OnMapReadyCallback {

    Double Latitude;
    Double Longitude;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;
    CircleImageView profileImage;
    EditText profileName;
    EditText profileEmail;
    EditText profileContact;
    EditText profileHostelName;
    EditText profileHostelAddress;
    EditText profileRent;
    EditText profileDescription;
    TextView profileMou;
    MaterialCheckBox profileFacilityBed;
    ImageView profileHostelImage1;
    ImageView profileHostelImage2;
    ImageView profileHostelImage3;
    ImageView profileHostelImage4;
    Button profileNextButton;
    FloatingActionButton edit_profile_picture;
    String emailregex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    Pattern emailPattern=Pattern.compile(emailregex);
    String errorMessage="";
    FirebaseAuth auth;
    FirebaseFirestore db;
    StorageReference storageReference;
    HashMap<String , String> sessionData;
    final Map<String , Object> data=new HashMap<>();
    int PROFILE_PICTURE=0 , HOSTELIMAGE1=0 , HOSTELIMAGE2=0 , HOSTELIMAGE3=0 , HOSTELIMAGE4=0 , MOU=0;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        edit_profile_picture = (FloatingActionButton) findViewById(R.id.edit_profile_picture);
        profileImage = (CircleImageView) findViewById(R.id.profileImage);
        profileName = (EditText) findViewById(R.id.profileName);
        profileEmail = (EditText) findViewById(R.id.profileEmail);
        profileContact = (EditText) findViewById(R.id.profileContact);
        profileHostelName = (EditText) findViewById(R.id.profileHostelName);
        profileHostelAddress = (EditText) findViewById(R.id.profileHostelAddress);
        profileRent=(EditText)findViewById(R.id.profileRent);
        profileDescription=(EditText)findViewById(R.id.profileDescription);
        profileMou = (TextView) findViewById(R.id.profileMou);
        profileFacilityBed = (MaterialCheckBox) findViewById(R.id.profileFacilityBed);
        profileHostelImage1 = (ImageView) findViewById(R.id.profileHostelImage1);
        profileHostelImage2 = (ImageView) findViewById(R.id.profileHostelImage2);
        profileHostelImage3 = (ImageView) findViewById(R.id.profileHostelImage3);
        profileHostelImage4 = (ImageView) findViewById(R.id.profileHostelImage4);
        profileNextButton = (Button) findViewById(R.id.profileNextButton);
        SessionManager sessionManager = new SessionManager(profile.this);
        sessionData = sessionManager.getUserDetailFromSession();
        db = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference("hostels-images");
        final DisplayMetrics displayMetrics= new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final int width=displayMetrics.widthPixels;
        final int height=displayMetrics.heightPixels;

       if (getIntent().getStringExtra("from").equals("otp")) {
            edit_profile_picture.setVisibility(View.VISIBLE);
            profileNextButton.setVisibility(View.VISIBLE);
            profileName.setEnabled(true);
            profileEmail.setEnabled(true);
            profileHostelName.setEnabled(true);
            profileHostelAddress.setEnabled(true);
            profileFacilityBed.setEnabled(true);
            profileRent.setEnabled(true);
            profileDescription.setEnabled(true);
            auth = FirebaseAuth.getInstance();
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
            fetchLocation();
            edit_profile_picture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CropImage.activity().setAllowRotation(true).setAllowFlipping(true)
                            .setAspectRatio(1, 1).setFixAspectRatio(true).setRequestedSize(300, 300)
                            .setCropShape(CropImageView.CropShape.OVAL).
                            start(profile.this);
                    PROFILE_PICTURE = 1;
                    HOSTELIMAGE1 = 0;
                    HOSTELIMAGE2 = 0;
                    HOSTELIMAGE3 = 0;
                    HOSTELIMAGE4 = 0;
                    MOU = 0;
                }
            });
            profileHostelImage1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CropImage.activity().setAllowRotation(true).setAllowFlipping(true)
                            .setAspectRatio(1, 1).setFixAspectRatio(true).setRequestedSize(300, 300).
                            start(profile.this);
                    PROFILE_PICTURE = 0;
                    HOSTELIMAGE1 = 1;
                    HOSTELIMAGE2 = 0;
                    HOSTELIMAGE3 = 0;
                    HOSTELIMAGE4 = 0;
                    MOU = 0;
                }
            });
            profileHostelImage1.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View holder) {
                    View view=getLayoutInflater().inflate(R.layout.imageviewer , null);
                    final AlertDialog.Builder builder=new AlertDialog.Builder(profile.this);
                    final ImageView imageView=(ImageView)view.findViewById(R.id.imagerViewer);
                    final ImageView deleteImageView=(ImageView)view.findViewById(R.id.deleteImageViewer);
                    deleteImageView.setVisibility(View.VISIBLE);
                    final ImageButton imageButton=(ImageButton)view.findViewById(R.id.closeImageViewer);
                    db.collection("Hostels").document("+91"+sessionData.get(SessionManager.Key_Phone_no))
                            .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                @Override
                                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                    if(value.exists()) {
                                        String name = "hostelImage1" + "+91" + sessionData.get(SessionManager.Key_Phone_no) + ".null";
                                        final StorageReference imgref = storageReference.child(name);

                                        Picasso.get().load(value.get(imgref.getDownloadUrl().toString()).toString()).noFade()
                                                .resize(imageView.getWidth(), imageView.getHeight()).into(imageView, new Callback() {
                                            @Override
                                            public void onSuccess() {

                                            }

                                            @Override
                                            public void onError(Exception e) {
                                                Toast.makeText(profile.this, "" + e, Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                    }
                            });
                    builder.setView(view);
                    final AlertDialog dialog=builder.create();
                    dialog.show();
                    WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                    layoutParams.copyFrom(dialog.getWindow().getAttributes());
                    layoutParams.width = width;
                    layoutParams.height = height/2;
                    dialog.getWindow().setAttributes(layoutParams);
                    imageButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    deleteImageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String name = "hostelImage1" + "+91" + sessionData.get(SessionManager.Key_Phone_no) + ".null";
                            final StorageReference imgref = storageReference.child(name);
                            imgref.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(profile.this  , "image deleted successfuly" , Toast.LENGTH_LONG).show();
                                    Picasso.get().load(R.drawable.add_image).fit().into(imageView);
                                }
                            });
                        }
                    });
                    return true;
                }
            });
            profileHostelImage2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CropImage.activity().setAllowRotation(true).setAllowFlipping(true)
                            .setAspectRatio(1, 1).setFixAspectRatio(true).setRequestedSize(300, 300).
                            start(profile.this);
                    PROFILE_PICTURE = 0;
                    HOSTELIMAGE1 = 0;
                    HOSTELIMAGE2 = 1;
                    HOSTELIMAGE3 = 0;
                    HOSTELIMAGE4 = 0;
                    MOU = 0;
                }
            });
            profileHostelImage3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CropImage.activity().setAllowRotation(true).setAllowFlipping(true)
                            .setAspectRatio(1, 1).setFixAspectRatio(true).setRequestedSize(300, 300).
                            start(profile.this);
                    PROFILE_PICTURE = 0;
                    HOSTELIMAGE1 = 0;
                    HOSTELIMAGE2 = 0;
                    HOSTELIMAGE3 = 1;
                    HOSTELIMAGE4 = 0;
                    MOU = 0;
                }
            });
            profileHostelImage4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CropImage.activity().setAllowRotation(true).setAllowFlipping(true)
                            .setAspectRatio(1, 1).setFixAspectRatio(true).setRequestedSize(300, 300).
                            start(profile.this);
                    PROFILE_PICTURE = 0;
                    HOSTELIMAGE1 = 0;
                    HOSTELIMAGE2 = 0;
                    HOSTELIMAGE3 = 0;
                    HOSTELIMAGE4 = 1;
                    MOU = 0;
                }
            });
            if (!(profileName.getText().toString().trim().length() > 0)) {
                errorMessage += "enter a valid name\n";
            }
            if (!(profileHostelName.getText().toString().trim().length() > 0)) {
                errorMessage += "enter a valid Hostel Name\n";
            }
            if (!(profileHostelAddress.getText().toString().trim().length() > 0)) {
                errorMessage += "enter a valid hostel address\n";
            }
            profileNextButton.setEnabled(true);
            profileNextButton.setBackgroundResource(R.drawable.sign_up_button);
            profileNextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    data.put("name", profileName.getText().toString());
                    if (emailPattern.matcher(profileEmail.getText().toString().trim()).matches()) {
                        data.put("email", profileEmail.getText().toString());
                    } else {
                        data.put("email", "");
                        errorMessage += "invalid user email address\n";
                    }
                    data.put("hostelName", profileHostelName.getText().toString());
                    data.put("hostelAddress", profileHostelAddress.getText().toString());
                    data.put("price" , profileRent.getText().toString());
                    data.put("description" , profileDescription.getText().toString());
                    ArrayList<String> facilities=new ArrayList<>();
                    if (profileFacilityBed.isChecked())
                        facilities.add(profileFacilityBed.getText().toString());
                    data.put("hostelFacilities", facilities);
                    db.collection("Hostels").document("+91" + sessionData.get(SessionManager.Key_Phone_no)).update(data)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(profile.this, "profie updated successfully", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(profile.this, "" + e, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });

       }
       else{
            db.collection("Hostels").document("+91"+sessionData.get(SessionManager.Key_Phone_no)).addSnapshotListener(
                    new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                            if(value.exists()) {
                                profileName.setText(value.get("name").toString());
                                profileEmail.setText(value.get("email").toString());
                                profileContact.setText(value.get("phoneNo").toString());
                                profileHostelName.setText(value.get("hostelName").toString());
                                profileHostelAddress.setText(value.get("hostelAddress").toString());
                                Latitude = (Double) value.get("lat");
                                Longitude = (Double) value.get("lot");
                                profileRent.setText(value.get(("price")).toString());
                                profileDescription.setText(value.get("description").toString());
                                List<String> facilities=(List<String>)value.get("hostelFacilities");
                                for(String i:facilities){
                                    if(i.equals("BED")){
                                        profileFacilityBed.setChecked(true);
                                    }
                                }
                                SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.profileMap);
                                assert supportMapFragment != null;
                                supportMapFragment.getMapAsync(profile.this);
                                if (value.get("profilePicture").toString().length() > 0) {
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
                            }
                                if(value.get("hostelImage1").toString().trim().length()>0){
                                    Picasso.get().load(value.get("hostelImage1").toString()).noFade().into(profileHostelImage1, new Callback() {
                                        @Override
                                        public void onSuccess() {

                                        }

                                        @Override
                                        public void onError(Exception e) {
                                            Toast.makeText(profile.this, "" + e, Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    profileHostelImage1.setPadding(0 , 0 , 0 , 0);
                                }
                            if(value.get("hostelImage2").toString().trim().length()>0){
                                Picasso.get().load(value.get("hostelImage2").toString()).noFade().into(profileHostelImage2, new Callback() {
                                    @Override
                                    public void onSuccess() {

                                    }

                                    @Override
                                    public void onError(Exception e) {
                                        Toast.makeText(profile.this, "" + e, Toast.LENGTH_SHORT).show();
                                    }
                                });
                                profileHostelImage2.setPadding(0 , 0 , 0 , 0);

                            }
                           if(value.get("hostelImage3").toString().trim().length()>0){
                                Picasso.get().load(value.get("hostelImage3").toString()).noFade().into(profileHostelImage3, new Callback() {
                                    @Override
                                    public void onSuccess() {

                                    }

                                    @Override
                                    public void onError(Exception e) {
                                        Toast.makeText(profile.this, "" + e, Toast.LENGTH_SHORT).show();
                                    }
                                });
                               profileHostelImage3.setPadding(0 , 0 , 0 , 0);

                           }
                            if(value.get("hostelImage4").toString().trim().length()>0){
                                Picasso.get().load(value.get("hostelImage4").toString()).noFade().into(profileHostelImage4, new Callback() {
                                    @Override
                                    public void onSuccess() {

                                    }

                                    @Override
                                    public void onError(Exception e) {
                                        Toast.makeText(profile.this, "" + e, Toast.LENGTH_SHORT).show();
                                    }
                                });
                                profileHostelImage4.setPadding(0 , 0 , 0 , 0);

                            }
                            if(value.get("hostelMou").toString().trim().length()>0){

                            }

                        }
                    });
                 profileImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View holder) {
                    View view=getLayoutInflater().inflate(R.layout.imageviewer , null);
                    final AlertDialog.Builder builder=new AlertDialog.Builder(profile.this);
                    final ImageView imageView=(ImageView)view.findViewById(R.id.imagerViewer);
                    final ImageButton imageButton=(ImageButton)view.findViewById(R.id.closeImageViewer);
                    db.collection("Hostels").document("+91"+sessionData.get(SessionManager.Key_Phone_no))
                            .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                @Override
                                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                    if(value.exists()){
                                        if(value.get("profilePicture").toString().trim().length()>0) {
                                            Picasso.get().load(value.get("profilePicture").toString()).noFade()
                                                    .resize(imageView.getWidth() , imageView.getHeight()).into(imageView, new Callback() {
                                                @Override
                                                public void onSuccess() {

                                                }

                                                @Override
                                                public void onError(Exception e) {
                                                    Toast.makeText(profile.this, "" + e, Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                        else{
                                           Picasso.get().load(R.drawable.ic_male_avatr).fit().into(imageView);
                                        }
                                    }
                                }
                            });
                    builder.setView(view);
                    final AlertDialog dialog=builder.create();
                    dialog.show();
                    WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                    layoutParams.copyFrom(dialog.getWindow().getAttributes());
                    layoutParams.width = width;
                    layoutParams.height = height/2;
                    dialog.getWindow().setAttributes(layoutParams);
                    imageButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    }
                    });
                 profileHostelImage1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View holder) {
                    View view=getLayoutInflater().inflate(R.layout.imageviewer , null);
                    final AlertDialog.Builder builder=new AlertDialog.Builder(profile.this);
                    final ImageView imageView=(ImageView)view.findViewById(R.id.imagerViewer);
                    final ImageButton imageButton=(ImageButton)view.findViewById(R.id.closeImageViewer);
                    db.collection("Hostels").document("+91"+sessionData.get(SessionManager.Key_Phone_no))
                            .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                @Override
                                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                    if(value.exists()){
                                        if(value.get("hostelImage1").toString().trim().length()>0) {
                                            Picasso.get().load(value.get("hostelImage1").toString()).noFade()
                                                    .resize(imageView.getWidth() , imageView.getHeight()).into(imageView, new Callback() {
                                                @Override
                                                public void onSuccess() {

                                                }

                                                @Override
                                                public void onError(Exception e) {
                                                    Toast.makeText(profile.this, "" + e, Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                        else{
                                            imageView.setImageResource(R.drawable.add_image);
                                        }
                                    }
                                }
                            });
                    builder.setView(view);
                    final AlertDialog dialog=builder.create();
                    dialog.show();
                    WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                    layoutParams.copyFrom(dialog.getWindow().getAttributes());
                    layoutParams.width = width;
                    layoutParams.height = height/2;
                    dialog.getWindow().setAttributes(layoutParams);
                    imageButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                }
            });
            profileHostelImage2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View holder) {
                    View view=getLayoutInflater().inflate(R.layout.imageviewer , null);
                    final AlertDialog.Builder builder=new AlertDialog.Builder(profile.this);
                    final ImageView imageView=(ImageView)view.findViewById(R.id.imagerViewer);
                    final ImageButton imageButton=(ImageButton)view.findViewById(R.id.closeImageViewer);
                    db.collection("Hostels").document("+91"+sessionData.get(SessionManager.Key_Phone_no))
                            .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                @Override
                                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                    if(value.exists()){
                                        if(value.get("hostelImage2").toString().trim().length()>0) {
                                            Picasso.get().load(value.get("hostelImage2").toString()).noFade()
                                                    .resize(imageView.getWidth() , imageView.getHeight()).into(imageView, new Callback() {
                                                @Override
                                                public void onSuccess() {

                                                }

                                                @Override
                                                public void onError(Exception e) {
                                                    Toast.makeText(profile.this, "" + e, Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                        else{
                                            imageView.setImageResource(R.drawable.add_image);
                                        }
                                    }
                                }
                            });
                    builder.setView(view);
                    final AlertDialog dialog=builder.create();
                    dialog.show();
                    WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                    layoutParams.copyFrom(dialog.getWindow().getAttributes());
                    layoutParams.width = width;
                    layoutParams.height = height/2;
                    dialog.getWindow().setAttributes(layoutParams);
                    imageButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                }
            });
            profileHostelImage3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View holder) {
                    View view=getLayoutInflater().inflate(R.layout.imageviewer , null);
                    final AlertDialog.Builder builder=new AlertDialog.Builder(profile.this);
                    final ImageView imageView=(ImageView)view.findViewById(R.id.imagerViewer);
                    final ImageButton imageButton=(ImageButton)view.findViewById(R.id.closeImageViewer);
                    db.collection("Hostels").document("+91"+sessionData.get(SessionManager.Key_Phone_no))
                            .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                @Override
                                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                    if(value.exists()){
                                        if(value.get("hostelImage3").toString().trim().length()>0) {
                                            Picasso.get().load(value.get("hostelImage3").toString()).noFade()
                                                    .resize(imageView.getWidth() , imageView.getHeight()).into(imageView, new Callback() {
                                                @Override
                                                public void onSuccess() {

                                                }

                                                @Override
                                                public void onError(Exception e) {
                                                    Toast.makeText(profile.this, "" + e, Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                        else{
                                            imageView.setImageResource(R.drawable.add_image);
                                        }
                                    }
                                }
                            });
                    builder.setView(view);
                    final AlertDialog dialog=builder.create();
                    dialog.show();
                    WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                    layoutParams.copyFrom(dialog.getWindow().getAttributes());
                    layoutParams.width = width;
                    layoutParams.height = height/2;
                    dialog.getWindow().setAttributes(layoutParams);
                    imageButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                }
            });
            profileHostelImage4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View holder) {
                    View view=getLayoutInflater().inflate(R.layout.imageviewer , null);
                    final AlertDialog.Builder builder=new AlertDialog.Builder(profile.this);
                    final ImageView imageView=(ImageView)view.findViewById(R.id.imagerViewer);
                    final ImageButton imageButton=(ImageButton)view.findViewById(R.id.closeImageViewer);
                    db.collection("Hostels").document("+91"+sessionData.get(SessionManager.Key_Phone_no))
                            .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                @Override
                                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                    if(value.exists()){
                                        if(value.get("hostelImage4").toString().trim().length()>0) {
                                            Picasso.get().load(value.get("hostelImage4").toString()).noFade()
                                                    .resize(imageView.getWidth() , imageView.getHeight()).into(imageView, new Callback() {
                                                @Override
                                                public void onSuccess() {

                                                }

                                                @Override
                                                public void onError(Exception e) {
                                                    Toast.makeText(profile.this, "" + e, Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                        else{
                                            imageView.setImageResource(R.drawable.add_image);
                                        }
                                    }
                                }
                            });
                    builder.setView(view);
                    final AlertDialog dialog=builder.create();
                    dialog.show();
                    WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                    layoutParams.copyFrom(dialog.getWindow().getAttributes());
                    layoutParams.width = width;
                    layoutParams.height = height/2;
                    dialog.getWindow().setAttributes(layoutParams);
                    imageButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                }
            });
            profileMou.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View holder) {
                    View view=getLayoutInflater().inflate(R.layout.imageviewer , null);
                    final AlertDialog.Builder builder=new AlertDialog.Builder(profile.this);
                    final ImageView imageView=(ImageView)view.findViewById(R.id.imagerViewer);
                    final ImageButton imageButton=(ImageButton)view.findViewById(R.id.closeImageViewer);
                    db.collection("Hostels").document("+91"+sessionData.get(SessionManager.Key_Phone_no))
                            .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                @Override
                                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                    if(value.exists()){
                                        if(value.get("hostelMou").toString().trim().length()>0) {
                                            Picasso.get().load(value.get("hostelMou").toString()).noFade()
                                                    .resize(imageView.getWidth() , imageView.getHeight()).into(imageView, new Callback() {
                                                @Override
                                                public void onSuccess() {

                                                }

                                                @Override
                                                public void onError(Exception e) {
                                                    Toast.makeText(profile.this, "" + e, Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                        else{
                                            imageView.setImageResource(R.drawable.add_image);
                                        }
                                    }
                                }
                            });
                    builder.setView(view);
                    final AlertDialog dialog=builder.create();
                    dialog.show();
                    WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                    layoutParams.copyFrom(dialog.getWindow().getAttributes());
                    layoutParams.width = width;
                    layoutParams.height = height/2;
                    dialog.getWindow().setAttributes(layoutParams);
                    imageButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                }
            });
        }
        }
    private void fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    Latitude=location.getLatitude();
                    Longitude=location.getLongitude();
                    Toast.makeText(getApplicationContext(), Latitude + "" + Longitude, Toast.LENGTH_SHORT).show();
                    data.put("lat", Latitude);
                    data.put("lot", Longitude);
                    SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.profileMap);
                    assert supportMapFragment != null;
                    supportMapFragment.getMapAsync(profile.this);
                }
            }
        });
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {

        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setZoomGesturesEnabled(true);
        LatLng latLng = new LatLng(Latitude , Longitude);
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("I am here!");
        CameraPosition cameraPosition=new CameraPosition.Builder().target(latLng).zoom(17).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        googleMap.addMarker(markerOptions);

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchLocation();
                }
                break;
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intentData) {
        super.onActivityResult(requestCode, resultCode, intentData);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode==RESULT_OK) {
            CropImage.ActivityResult result = CropImage.getActivityResult(intentData);
            if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(profile.this, "" + result.getError(), Toast.LENGTH_SHORT).show();
            }
            else{
                Uri imageUri = result.getUri();
                String name="";
                if(PROFILE_PICTURE==1) {
                    name = "profile" +"+91"+sessionData.get(SessionManager.Key_Phone_no)+"."+getExtension(imageUri);
                }
                if(HOSTELIMAGE1==1){
                    name = "hostelImage1" + "+91"+sessionData.get(SessionManager.Key_Phone_no)+"."+getExtension(imageUri);
                }
                if(HOSTELIMAGE2==1){
                    name="hostelImage2"+"+91"+sessionData.get(SessionManager.Key_Phone_no)+"."+getExtension(imageUri);
                }
                if(HOSTELIMAGE3==1){
                    name="hostelImage3"+"+91"+sessionData.get(SessionManager.Key_Phone_no)+"."+getExtension(imageUri);
                }
                if(HOSTELIMAGE4==1){
                    name="hostelImage4"+"+91"+sessionData.get(SessionManager.Key_Phone_no)+"."+getExtension(imageUri);
                }
                if(MOU==1){
                    name="mou"+"+91"+sessionData.get(SessionManager.Key_Phone_no)+"."+getExtension(imageUri);
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
                            if(PROFILE_PICTURE==1) {
                                data.put("profilePicture", task.getResult().toString());
                                Picasso.get().load(task.getResult().toString()).noFade().fit().into(profileImage, new Callback() {
                                    @Override
                                    public void onSuccess() {

                                    }

                                    @Override
                                    public void onError(Exception e) {
                                        Toast.makeText(profile.this , ""+e , Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                            if(HOSTELIMAGE1==1){
                                data.put("hostelImage1", task.getResult().toString());
                                Picasso.get().load(task.getResult().toString()).noFade().fit().into(profileHostelImage1, new Callback() {
                                    @Override
                                    public void onSuccess() {

                                    }

                                    @Override
                                    public void onError(Exception e) {
                                        Toast.makeText(profile.this , ""+e , Toast.LENGTH_LONG).show();
                                    }
                                });
                                profileHostelImage1.setPadding(0 , 0 , 0 , 0);
                            }
                            if(HOSTELIMAGE2==1){
                                data.put("hostelImage2", task.getResult().toString());
                                Picasso.get().load(task.getResult().toString()).noFade().fit().into(profileHostelImage2, new Callback() {
                                    @Override
                                    public void onSuccess() {

                                    }

                                    @Override
                                    public void onError(Exception e) {
                                        Toast.makeText(profile.this , ""+e , Toast.LENGTH_LONG).show();
                                    }
                                });
                                profileHostelImage2.setPadding(0 , 0 , 0 , 0);

                            }
                            if(HOSTELIMAGE3==1){
                                data.put("hostelImage3", task.getResult().toString());
                                Picasso.get().load(task.getResult().toString()).noFade().fit().into(profileHostelImage3, new Callback() {
                                    @Override
                                    public void onSuccess() {

                                    }

                                    @Override
                                    public void onError(Exception e) {
                                        Toast.makeText(profile.this , ""+e , Toast.LENGTH_LONG).show();
                                    }
                                });
                                profileHostelImage3.setPadding(0 , 0 , 0 , 0);

                            }
                            if(HOSTELIMAGE4==1){
                                data.put("hostelImage4", task.getResult().toString());
                                Picasso.get().load(task.getResult().toString()).noFade().fit().into(profileHostelImage4, new Callback() {
                                    @Override
                                    public void onSuccess() {

                                    }

                                    @Override
                                    public void onError(Exception e) {
                                        Toast.makeText(profile.this , ""+e , Toast.LENGTH_LONG).show();
                                    }
                                });
                                profileHostelImage4.setPadding(0 , 0 , 0 , 0);

                            }
                            if(MOU==1){
                                data.put("hostelMou" , task.getResult().toString());
                            }
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