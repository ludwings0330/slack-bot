package com.example.slackbot.slackApi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

public class SlackApiDto {
    @Data
    public static class SlackPostMessageResponse {
        Boolean ok;
        String channel;
        String ts;

        Message message;

        public boolean isOk() {
            if (ok == null) {
                return false;
            }

            return ok;
        }
    }

    @Data
    public static class Message {
        @JsonProperty("bot_id")
        String botId;
        String type;
        String text;
        String user;
        String ts;
        String team;

//        @JsonProperty("bot_profile")
//        BotProfile botProfile;
    }

    @Data
    public static class SlackPostMessageRequest implements Serializable {
        String channel;
        String text;
    }
}
