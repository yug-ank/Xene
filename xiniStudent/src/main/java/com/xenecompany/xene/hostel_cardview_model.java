package com.xenecompany.xene;

public class hostel_cardview_model {
    private String banner_image;
    private String name;
    private String address;
    private float rating;
    private String itemID;
    public hostel_cardview_model() {
    }

    public hostel_cardview_model(String banner_image, String name, String address, float rating, String itemID) {
        this.banner_image = banner_image;
        this.name = name;
        this.address = address;
        this.rating = rating;
        this.itemID = itemID;
    }

    public String getBanner_image() {
        return banner_image;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public float getRating() {
        return rating;
    }

    public String getItemID() {
        return itemID;
    }

    public void setBanner_image(String banner_image) {
        this.banner_image = banner_image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }
}
