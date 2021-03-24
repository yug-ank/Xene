package com.xenecompany.xinihostel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface NotificationAPIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAWgQ6dwc:APA91bFFZb3QjcsnFiSJex7F7b_Espbuy7IWg60AshQwOgKsPvsmaVDsAjbWQymW4E-olgVFDHpu9V758itxYNmGUmfOvQ-DnYVero13XDGdKQ_okpXZKvWxIY34DfUWMjYx-RBcSSF9" // Your server key refer to video for finding your server key
            }
    )

    @POST("https://fcm.googleapis.com/fcm/send")
    Call<NotificationResponse> sendNotifcation(@Body NotificationSender body);
}