package com.example.slackbot.LeopoldApi;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface LeopoldApi {

    @GET("Shop/Board.php?BoTable=Notice")
    Call<ResponseBody> getLeopoldNotice();
}
