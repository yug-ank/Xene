package com.xenecompany.xene;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Loginpage extends Activity {

    private EditText userNumber;
    private Button sendOtpButton;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);
        userNumber=(EditText)findViewById(R.id.userNumber);
        sendOtpButton=(Button)findViewById(R.id.sendotpbutton);
        userNumber.addTextChangedListener(loginTextWatcher);
    }

    @Override
    protected void onStart() {
        super.onStart();
        SessionManager sessionManager= new SessionManager(Loginpage.this);
        if(sessionManager.checkLogin()){
            Intent intent = new Intent(Loginpage.this , HomePage.class);
            intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TASK|intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }
    private TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(userNumber.getText().toString().trim().length()<10){
                    sendOtpButton.setEnabled(false);
                    sendOtpButton.setBackgroundResource(R.drawable.sign_up_button_notactive);
                }
                else{
                    sendOtpButton.setEnabled(true);
                    sendOtpButton.setBackgroundResource(R.drawable.sign_up_button);
                }
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };
    public void sendOtp(View view){
        String phoneNo=userNumber.getText().toString();
        Intent intent = new Intent( getApplicationContext() , otp_page.class);
        intent.putExtra("phoneNo" , phoneNo);
        startActivity(intent);
    }

}