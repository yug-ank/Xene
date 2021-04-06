package com.xenecompany.xene;

public class userObject {
    private String chatroomId , hostelNo , profilePicture , hostelName;
    Boolean isOnline;
    public userObject(String chatroomId , String hostelNo){
        this.chatroomId = chatroomId;
        this.hostelNo = hostelNo;
    }

    public String getChatroomId() { return chatroomId; }

    public String getHostelNo(){
        return hostelNo;
    }

    public String getProfilePicture() { return profilePicture; }

    public String getHostelName() { return hostelName; }

    public void setIsOnline(Boolean isOnline){ this.isOnline = isOnline;}

    public Boolean getIsOnline(){ return isOnline;}

    public void setHostelName(String hostelName){ this.hostelName = hostelName; }

    public void setProfilePicture(String profilePicture){ this.profilePicture = profilePicture; }
}
