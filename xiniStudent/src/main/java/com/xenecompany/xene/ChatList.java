package com.xenecompany.xene;

public class ChatList {
    private String chatroomId , hostelNo , profilePicture , hostelName;
    public ChatList(String chatroomId , String hostelNo){
        this.chatroomId = chatroomId;
        this.hostelNo = hostelNo;
    }

    public String getChatroomId() { return chatroomId; }

    public String getHostelNo(){
        return hostelNo;
    }

    public String getProfilePicture() { return profilePicture; }

    public String getHostelName() { return hostelName; }

    public void setHostelName(String hostelName){ this.hostelName = hostelName; }

    public void setProfilePicture(String profilePicture){ this.profilePicture = profilePicture; }
}
