package com.xenecompany.xene;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail extends AsyncTask<Message , String , String> {
    String name;
    String contact;
    String issue;
    ProgressDialog progressDialog;
    Context context;
    public SendEmail(Context context , String name, String contact, String issue) {
        this.name = name;
        this.contact = contact;
        this.issue = issue;
        this.context=context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog= ProgressDialog.show(context, "Please wait" , "sending your issue to our support" , true  , false);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progressDialog.dismiss();
        Toast.makeText(context, "Issue send" , Toast.LENGTH_SHORT).show();
    }

    @Override
    protected String doInBackground(Message... messages) {
        Properties props=new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.port", "465");
        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("spprtxncompany@gmail.com", "Vikrant@4914");
            }
        });
        try {
            Message message=new MimeMessage(session);
            message.setFrom(new InternetAddress("yuganksharma012@gmail.com"));
            message.setRecipient(Message.RecipientType.TO ,
                    new InternetAddress("xinicompany@gmail.com"));
            message.setSubject("Issue from student side");
            message.setText("name:"+name+"\nContact:"+contact+"\nIssue\n"+issue);
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return null;
    }
}