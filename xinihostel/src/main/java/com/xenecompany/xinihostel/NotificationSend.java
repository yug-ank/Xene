package com.xenecompany.xinihostel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationSend {
    private NotificationAPIService notificationAPIService;

    public NotificationSend(String userToken, String title, String message) {
       NotificationData notificationData=new NotificationData(title , message);
       NotificationSender notificationSender=new NotificationSender(notificationData , userToken);
       notificationAPIService = NotificationClient.getClient("https://fcm.googleapis.com/").create(NotificationAPIService.class);
       notificationAPIService.sendNotifcation(notificationSender).enqueue(new Callback<NotificationResponse>() {
           @Override
           public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
               if(response.code()==200){
                   if(response.body().success!=1){

                   }
               }
           }

           @Override
           public void onFailure(Call<NotificationResponse> call, Throwable t) {

           }
       });
    }
}
