package com.xenecompany.xene;

public class NotificationData {
    private  String Title;
    private String Message;

    public NotificationData(String title, String message) {
        Title = title;
        Message = message;
    }

    public NotificationData() {
    }

    public String getTitle() {
        return Title;
    }

    public String getMessage() {
        return Message;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
