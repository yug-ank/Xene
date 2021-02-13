package com.xenecompany.xene;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.appcompat.app.AppCompatActivity;

public class contact_us extends AppCompatActivity {
    EditText name;
    EditText contact;
    EditText issue;
    Button send;
    String emailregex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String phoneregex = "\\d{10}";
    Pattern emailPattern=Pattern.compile(emailregex);
    Pattern phonePattern=Pattern.compile(phoneregex);
    Matcher emailMatcher;
    Matcher phoneMatcher;
    boolean nameBoolean=false , contactBoolean=false , issueBoolean=false;
    TextWatcher nameTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(name.getText().toString().trim().length()>0){
                nameBoolean=true;
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };
    TextWatcher contactTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(contact.getText().toString().trim().length()>0){
                contactBoolean=true;
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };
    TextWatcher issueTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(issue.getText().toString().trim().length()>0){
                    issueBoolean=true;
            }
        }
        @Override
        public void afterTextChanged(Editable editable) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        name=(EditText)findViewById(R.id.contactUsPersonName);
        contact=(EditText)findViewById(R.id.contactUsContact);
        issue=(EditText)findViewById(R.id.contactUsIssue);
        send=(Button)findViewById(R.id.contactUsButton);
        emailMatcher=emailPattern.matcher(contact.getText().toString().trim());
        phoneMatcher=phonePattern.matcher(contact.getText().toString().trim());
        name.addTextChangedListener(nameTextWatcher);
        contact.addTextChangedListener(contactTextWatcher);
        issue.addTextChangedListener(issueTextWatcher);
        if(nameBoolean && contactBoolean && issueBoolean){
            Log.i("rectify" , "ffasas");
            if(emailMatcher.matches() || phoneMatcher.matches()){
                send.setEnabled(true);
                send.setBackgroundResource(R.drawable.sign_up_button);
            }
        }
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameBoolean=false;
                contactBoolean=false;
                issueBoolean=false;
                String personname=name.getText().toString().trim();
                String personcontact=contact.getText().toString().trim();
                String personissue=issue.getText().toString().trim();
                SendEmail sendEmail=new SendEmail(contact_us.this, personname , personcontact , personissue);
                sendEmail.execute();
            }
        });

    }
}