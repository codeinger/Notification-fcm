package com.codeinger.notification_fcm.network;

import com.codeinger.notification_fcm.model.NotificationReq;
import com.codeinger.notification_fcm.model.NotificationResponce;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface NotificationRequest {

    @Headers({"Content-Type:application/json","Authorization:key=AAAAVq3DyWU:APA91bEvEzmYNen83_WdPpBmIs5b-mrxSeICnhw4Ww-DpE1weQefo_IHw8dArQU3H_HiyUhqPdQOKT6CGECwId8ewOc2TuhZKjy1d2SBBHW1JAgKjc8tNmNdPi6hEX-xCILlR7GV8XZS"})
    @POST("send")
    Call<NotificationResponce> sent(@Body NotificationReq req);

}
