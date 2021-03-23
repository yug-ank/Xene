package com.xenecompany.xene;

public class chat_object {
    String text ,time ,sender;

    public chat_object(String text ,String time ,String sender){
        this.text = text;
        this.sender = sender;
        this.time = time;
    }

    String getText(){ return text;}
    String getTime(){ return time;}
    String getSender(){ return  sender;}
}
