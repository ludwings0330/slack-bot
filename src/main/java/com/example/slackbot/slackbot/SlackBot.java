package com.example.slackbot.slackbot;

import com.example.slackbot.LeopoldApi.LeopoldApiCaller;
import com.example.slackbot.LeopoldApi.LeopoldApiDto;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SlackBot {
    private final LeopoldApiCaller leopoldApiCaller;

    public SlackBot(LeopoldApiCaller leopoldApiCaller) throws IOException {
        this.leopoldApiCaller = leopoldApiCaller;

        var result = leopoldApiCaller.getLeopoldNotice();

        System.out.println("result = " + result);
    }
}
