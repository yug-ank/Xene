package com.xenecompany.xene;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationSend {
    private NotificationAPIService notificationAPIService;

    public NotificationSend(String userToken, String title, String message) {
        Log.i("rectify" , userToken);
        NotificationData notificationData=new NotificationData(title , message);
       NotificationSender notificationSender=new NotificationSender(notificationData , userToken);
       Log.i("rectify" , userToken);
       notificationAPIService = NotificationClient.getClient("https://fcm.googleapis.com/").create(NotificationAPIService.class);
       notificationAPIService.sendNotifcation(notificationSender).enqueue(new Callback<NotificationResponse>() {
           @Override
           public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
               if(response.code()==200){
                   if(response.body().success!=1){
                            Log.i("rectfiy" , "error");
                   }
               }
           }

           @Override
           public void onFailure(Call<NotificationResponse> call, Throwable t) {
                Log.i("rectify" , "failed");
           }
       });
    }
}
