package com.xenecompany.xinihostel;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public static final String Key_Phone_no="phone_no";
    public static final String Key_Latitude="lat";
    public static  final String Key_Longtitude="lot";
    public SessionManager(Context context){
            sharedPreferences = context.getSharedPreferences("userLoginSession" , Context.MODE_PRIVATE);
            editor=sharedPreferences.edit();
    }
    public void createLoginSession(String Phone_no){
            editor.putString(Key_Phone_no , Phone_no);
            editor.commit();
    }
    public void enterLocation(String lat , String lon){
        editor.putString(Key_Latitude , lat);
        editor.putString(Key_Longtitude , lon);
        editor.commit();
    }
    public HashMap<String , String> getUserDetailFromSession(){
        HashMap<String , String> userdata=new HashMap<String , String>();
        userdata.put(Key_Phone_no , sharedPreferences.getString(Key_Phone_no , null));
        return userdata;
    }
    public HashMap<String , String> getUserLocationFromSession(){
        HashMap<String , String> userdata=new HashMap<String , String>();
        userdata.put(Key_Latitude , sharedPreferences.getString(Key_Latitude , null));
        userdata.put(Key_Longtitude , sharedPreferences.getString(Key_Longtitude , null));
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
        editor.commit();
    }
}
