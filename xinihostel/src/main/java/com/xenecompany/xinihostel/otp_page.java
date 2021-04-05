package com.xenecompany.xinihostel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class otp_page extends Activity {

    private TextView resend;
    private PinView otpEntered;
    private  String verificationCodeBySystem;
    private ProgressBar progressBar;
    private String phoneNo;
    private String userId;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_page);
        otpEntered=(PinView)findViewById(R.id.otpinput);
        progressBar=(ProgressBar)findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);
        resend=(TextView)findViewById(R.id.resend);
        mAuth= FirebaseAuth.getInstance();
        phoneNo=getIntent().getStringExtra("phoneNo");
        userId="+91"+phoneNo;
        sendVerificationCodeToUser(phoneNo);

    }

    private void sendVerificationCodeToUser(String phoneNo) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91"+phoneNo,
                60,
                TimeUnit.SECONDS,
                otp_page.this,
                mCallbacks
        );
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationCodeBySystem=s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                String code=phoneAuthCredential.getSmsCode();
                if(code!=null){
                    otpEntered.setText(code);
                    verifyCode(code);
                }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(otp_page.this , e.getMessage() , Toast.LENGTH_SHORT).show();
        }
    };
    private void verifyCode(String verificationCode){
            progressBar.setVisibility(View.VISIBLE);
            PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verificationCodeBySystem , verificationCode);
            signInByPhoneNo(credential);
    }
    private void signInByPhoneNo(PhoneAuthCredential credential){
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(otp_page.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    SessionManager sessionManager=new SessionManager(otp_page.this);
                    sessionManager.createLoginSession(phoneNo);

                    final FirebaseFirestore db=FirebaseFirestore.getInstance();
                    final String token= FirebaseInstanceId.getInstance().getToken();
                    db.collection("Hostels").document(userId).addSnapshotListener(
                            new EventListener<DocumentSnapshot>() {
                                @Override
                                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                    if(value.exists()){
                                        sessionManager.enterLocation(value.get("lat").toString() , value.get("lot").toString());
                                        Toast.makeText(otp_page.this , "Login sucessful" , Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(otp_page.this , HomePage.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else{
                                        Map<String , Object> data=new HashMap<>();
                                        data.put("name" , "");
                                        data.put("phoneNo" , Long.parseLong("91"+phoneNo));
                                        data.put("email" , "");
                                        data.put("hostelName" , "");
                                        data.put("hostelAddress" , "");
                                        data.put("hostelMou" , "");
                                        data.put("hostelFacilities" , Arrays.asList());
                                        data.put("hostelImage1" , "");
                                        data.put("hostelImage2" , "");
                                        data.put("hostelImage3" , "");
                                        data.put("hostelImage4" , "");
                                        data.put("lat" , "");
                                        data.put("lot" , "");
                                        data.put("profilePicture" , "");
                                        data.put("profileVerified"  , false);
                                        data.put("price" , 0);
                                        data.put("description" , "");
                                        data.put("rating" , 0);
                                        data.put("token" , token);
                                        data.put("wishlist" , Arrays.asList());
                                        data.put("requested" , Arrays.asList());
                                        data.put("accepted" , Arrays.asList());
                                        db.collection("Hostels").document(userId).set(data , SetOptions.merge())
                                                .addOnSuccessListener(
                                                        new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                Toast.makeText(otp_page.this , "Account created" , Toast.LENGTH_SHORT).show();
                                                                Intent intent=new Intent(otp_page.this , profile.class);
                                                                intent.putExtra("from" , "otp");
                                                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                                startActivity(intent);
                                                                finish();
                                                            }
                                                        }
                                                ).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(otp_page.this , ""+e , Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                }
                            }
                    );

                }
                else{
                    Toast.makeText(otp_page.this , task.getException().getMessage() , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void verifyOtp(View view){
        String code=otpEntered.getText().toString();
        if(code.length()<6){
            Toast.makeText(this , "Invalid Otp" , Toast.LENGTH_SHORT).show();
        }
        else{
            verifyCode(code);
        }
    }
}