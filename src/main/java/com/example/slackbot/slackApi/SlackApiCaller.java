package com.example.slackbot.slackApi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.List;

@Component
@Slf4j
public class SlackApiCaller {

    private final SlackApi slackApi;
    private final String channelId;
    private final String authKey;

    public SlackApiCaller(@Value("${api.slack.channel-id}") String channelID,
                          @Value("${api.slack.auth-key}") String authKey,
                          @Value("${api.slack.base-url}") String baseUrl) {
        this.authKey =  "Bearer " + authKey;
        this.channelId = channelID;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.slackApi = retrofit.create(SlackApi.class);
    }

    public void postLeopoldNotice(List<String> notices) {
        for (var notice :
                notices) {
            var request = new SlackApiDto.SlackPostMessageRequest();
            request.setChannel(channelId);
            request.setText(notice);
            try {

                var call = slackApi.postLeopoldNotice(request, authKey);
                var response = call.execute();
                var body = response.body();

                if (!body.isOk()) {
                    throw new RuntimeException("response body null Exception");
                }

            } catch (IOException e) {
                log.error("postLeopoldNotice Exception : " + e.getMessage());
                throw new RuntimeException("postLeopoldNotice Exception : " + e.getMessage());
            }
        }
    }
}