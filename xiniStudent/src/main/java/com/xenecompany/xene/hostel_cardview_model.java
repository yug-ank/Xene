package com.xenecompany.xene;

public class hostel_cardview_model {
    private String hostelImage1;
    private String hostelName;
    private String hostelAddress;
    private float rating;
    private String itemID;
    private  String token;

    public hostel_cardview_model(String hostelImage1, String hostelName, String hostelAddress, float rating, String itemID, String token) {
        this.hostelImage1 = hostelImage1;
        this.hostelName = hostelName;
        this.hostelAddress = hostelAddress;
        this.rating = rating;
        this.itemID = itemID;
        this.token = token;
    }

    public hostel_cardview_model() {
    }

    public String getHostelImage1() {
        return hostelImage1;
    }

    public String getHostelName() {
        return hostelName;
    }

    public String getHostelAddress() {
        return hostelAddress;
    }

    public float getRating() {
        return rating;
    }

    public String getItemID() {
        return itemID;
    }

    public String getToken() {
        return token;
    }

    public void setHostelImage1(String hostelImage1) {
        this.hostelImage1 = hostelImage1;
    }

    public void setHostelName(String hostelName) {
        this.hostelName = hostelName;
    }

    public void setHostelAddress(String hostelAddress) {
        this.hostelAddress = hostelAddress;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
