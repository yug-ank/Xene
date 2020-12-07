package com.xenecompany.xene;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
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

    }
}