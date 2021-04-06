package com.xenecompany.xene;

public class chat_object {
    String text ,time ,sender;
    Boolean seen;

    public chat_object(String text ,String time ,String sender ,Boolean seen){
        this.text = text;
        this.sender = sender;
        this.time = time;
        this.seen = seen;
    }

    String getText(){ return text;}
    String getTime(){ return time;}
    String getSender(){ return  sender;}
    Boolean getSeen(){ return seen;}
}
