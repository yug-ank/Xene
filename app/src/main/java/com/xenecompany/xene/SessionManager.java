package com.xenecompany.xene;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static final String Logged_In="Logged_In";
    public static final String Key_Phone_no="phone_no";
    public SessionManager(Context context){
            sharedPreferences = context.getSharedPreferences("userLoginSession" , Context.MODE_PRIVATE);
            editor=sharedPreferences.edit();
    }
    public void createLoginSession(String Phone_no){
            editor.putString(Key_Phone_no , Phone_no);
            editor.commit();
    }
    public HashMap<String , String> getUserDetailFromSession(){
        HashMap<String , String> userdata=new HashMap<String , String>();
        userdata.put(Key_Phone_no , sharedPreferences.getString(Key_Phone_no , null));
        return userdata;
    }
    public boolean checkLogin(){
        if(sharedPreferences.getString(Key_Phone_no , null)!=null){
            return true;
        }
        else{
            return false;
        }
    }
    public void logOutUser(){
        editor.clear();
        editor.clear();
    }
}
