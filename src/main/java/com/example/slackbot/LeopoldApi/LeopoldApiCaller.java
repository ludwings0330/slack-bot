package com.example.slackbot.LeopoldApi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;

import java.io.IOException;

@Component
@Slf4j
public class LeopoldApiCaller {
    private final LeopoldApi leopoldApi;

    public LeopoldApiCaller(@Value("${api.leopold.base-url}") String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .build();

        leopoldApi = retrofit.create(LeopoldApi.class);
    }

    public String getLeopoldNotice() {
        try {
            var call = leopoldApi.getLeopoldNotice();

            return call.execute().body().string();

        } catch (IOException e) {
            log.error("Get Leopold Notice Exception : " + e.getMessage());
            throw new RuntimeException("Get Leopold Notice Exception : " + e.getMessage());
        }
    }
}
