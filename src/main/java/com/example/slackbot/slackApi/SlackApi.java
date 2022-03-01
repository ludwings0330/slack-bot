package com.example.slackbot.slackApi;

import org.springframework.beans.factory.annotation.Value;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public interface SlackApi {

    @Headers({
            "Content-type: application/json"
    })
    @POST("api/chat.postMessage")
    Call<SlackApiDto.SlackPostMessageResponse> postMessage(@Body SlackApiDto.SlackPostMessageRequest request, @Header("Authorization") String authHeader);

}
