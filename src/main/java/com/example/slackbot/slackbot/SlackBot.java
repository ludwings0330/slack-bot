package com.example.slackbot.slackbot;

import com.example.slackbot.LeopoldApi.LeopoldApiCaller;
import com.example.slackbot.Parser.Parser;
import com.example.slackbot.slackApi.SlackApiCaller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashSet;

@Component
@Slf4j
public class SlackBot implements Runnable {
    private final LeopoldApiCaller leopoldApiCaller;
    private final SlackApiCaller slackApiCaller;
    private final Parser parser;

    private final int REPEAT_TIME = 1000 * 60 * 60 * 12;

    private HashSet<String> prevNotices = new HashSet<>();

    public SlackBot(LeopoldApiCaller leopoldApiCaller, SlackApiCaller slackApiCaller, Parser parser) throws IOException {
        this.leopoldApiCaller = leopoldApiCaller;
        this.slackApiCaller = slackApiCaller;
        this.parser = parser;

        this.run();
    }

    private void removePreviousNotice(HashSet<String> prevNotices, HashSet<String> currentNotice) {
        for (String notice :
                prevNotices) {
            currentNotice.remove(notice);
        }
    }

    @Override
    public void run() {
        while (true) {
            try {

                String strLeopoldNoticeHtml = leopoldApiCaller.getLeopoldNotice();
                HashSet<String> findNotices = parser.parse(strLeopoldNoticeHtml);

                removePreviousNotice(prevNotices, findNotices);

                slackApiCaller.postLeopoldNotice(findNotices);

                Thread.sleep(REPEAT_TIME);
            } catch (InterruptedException e) {
                log.error("Robot Thread run Exception : " + e.getMessage());
                throw new RuntimeException("Robot Thread run Exception : " + e.getMessage());
            }
        }

    }
}
