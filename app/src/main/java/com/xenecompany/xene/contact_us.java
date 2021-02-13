package com.xenecompany.xene;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
    boolean details=false;
    TextWatcher nameTextWatcher , contactTextWatcher , issueTextWatcher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        name=(EditText)findViewById(R.id.contactUsPersonName);
        contact=(EditText)findViewById(R.id.contactUsContact);
        issue=(EditText)findViewById(R.id.contactUsIssue);
        send=(Button)findViewById(R.id.contactUsButton);
        nameTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(name.getText().toString().trim().length()>0){
                    contactTextWatcher = new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            if(contact.getText().toString().trim().length()>0){
                                issueTextWatcher = new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                        if(issue.getText().toString().trim().length()>0){
                                            emailMatcher=emailPattern.matcher(contact.getText().toString().trim());
                                            phoneMatcher=phonePattern.matcher(contact.getText().toString().trim());
                                            if(emailMatcher.matches() || phoneMatcher.matches()) {
                                                send.setEnabled(true);
                                                send.setBackgroundResource(R.drawable.sign_up_button);
                                                }
                                        }
                                    }
                                    @Override
                                    public void afterTextChanged(Editable editable) {
                                    }
                                };
                                issue.addTextChangedListener(issueTextWatcher);

                            }
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {
                        }
                    };
                    contact.addTextChangedListener(contactTextWatcher);

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        };
        name.addTextChangedListener(nameTextWatcher);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                details=false;
                String personname=name.getText().toString().trim();
                String personcontact=contact.getText().toString().trim();
                String personissue=issue.getText().toString().trim();
                SendEmail sendEmail=new SendEmail(contact_us.this, personname , personcontact , personissue);
                sendEmail.execute();
            }
        });

    }
}