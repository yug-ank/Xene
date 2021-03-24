package com.xenecompany.xinihostel;

public class ChatObject {
    private String chatroomId , userNo, profilePicture , userName;
    public ChatObject(String chatroomId , String userNo){
        this.chatroomId = chatroomId;
        this.userNo = userNo;
    }

    public String getChatroomId() { return chatroomId; }

    public String getUserNo(){
        return userNo;
    }

    public String getProfilePicture() { return profilePicture; }

    public String getUserName() { return userName; }

    public void setUserName(String userName){ this.userName = userName; }

    public void setProfilePicture(String profilePicture){ this.profilePicture = profilePicture; }
}
