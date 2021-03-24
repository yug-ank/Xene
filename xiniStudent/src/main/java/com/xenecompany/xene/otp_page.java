package com.xenecompany.xene;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
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
                    db.collection("Student").document(userId).addSnapshotListener(
                            new EventListener<DocumentSnapshot>() {
                                @Override
                                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                    if(value.exists()){
                                        Toast.makeText(otp_page.this , "Login sucessful" , Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Map<String , Object> data=new HashMap<>();
                                        data.put("name" , "");
                                        data.put("phoneNo" , Long.parseLong("91"+phoneNo));
                                        data.put("email" , "");
                                        data.put("instituteId" ,"");
                                        data.put("instituteName" , "");
                                        data.put("instituteAddress" , "");
                                        data.put("instituteIdCard" , "");
                                        data.put("aadharId" , "");
                                        data.put("aadharNo" , "");
                                        data.put("gaurdianName" , "");
                                        data.put("gaurdianAddress" , "");
                                        data.put("gaurdianContactNo" ,"");
                                        data.put("profilePicture" , "");
                                        data.put("wishlist" , Arrays.asList());
                                        data.put("requested" , Arrays.asList());
                                        data.put("accepted" , Arrays.asList());
                                        data.put("profileCompleted"  , false);
                                        data.put("token" , token);
                                        db.collection("Student").document(userId).set(data , SetOptions.merge())
                                                .addOnSuccessListener(
                                                        new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                Toast.makeText(otp_page.this , "Account created" , Toast.LENGTH_SHORT).show();
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
                    Intent intent=new Intent(otp_page.this , HomePage.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
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