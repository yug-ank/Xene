package com.xenecompany.xene;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.executor.TaskExecutor;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class otp_page extends AppCompatActivity {

    PinView otpEntered;
    String verificationCodeBySystem;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_page);
        otpEntered=(PinView)findViewById(R.id.otpinput);
        progressBar=(ProgressBar)findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);
        mAuth= FirebaseAuth.getInstance();
        String phoneNo=getIntent().getStringExtra("phoneNo");
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
                    Toast.makeText(otp_page.this , "successful" , Toast.LENGTH_SHORT).show();
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