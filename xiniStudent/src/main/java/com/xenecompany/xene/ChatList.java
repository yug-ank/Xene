package com.xenecompany.xene;

public class ChatList {
    private String chatroomId , hostelName;
    public ChatList(String chatroomId , String hostelName){
        this.chatroomId = chatroomId;
        this.hostelName = hostelName;
    }

    public String getChatroomId(){
        return chatroomId;
    }

    public String getHostelName(){
        return hostelName;
    }
}
