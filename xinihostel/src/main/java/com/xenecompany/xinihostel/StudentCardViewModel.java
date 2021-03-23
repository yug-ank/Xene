package com.xenecompany.xinihostel;

public class StudentCardViewModel {
    private String profilePicture;
    private String name;
    private String instituteName;
    private String itemID;
    private String token;

    public StudentCardViewModel(String profilePicture, String name, String instituteName, String itemID, String token) {
        this.profilePicture = profilePicture;
        this.name = name;
        this.instituteName = instituteName;
        this.itemID = itemID;
        this.token = token;
    }

    public StudentCardViewModel() {
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public String getName() {
        return name;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public String getItemID() {
        return itemID;
    }

    public String getToken() {
        return token;
    }
}
