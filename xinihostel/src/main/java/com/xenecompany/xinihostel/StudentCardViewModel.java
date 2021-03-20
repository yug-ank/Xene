package com.xenecompany.xinihostel;

public class StudentCardViewModel {
    private String profilePicture;
    private String name;
    private String instituteName;
    private String itemID;
    public StudentCardViewModel() {
    }

    public StudentCardViewModel(String profilePicture, String name, String instituteName, String itemID) {
        this.profilePicture = profilePicture;
        this.name = name;
        this.instituteName = instituteName;
        this.itemID = itemID;
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
}
