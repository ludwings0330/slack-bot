package com.example.slackbot.slackbot;

import com.example.slackbot.LeopoldApi.LeopoldApiCaller;
import com.example.slackbot.Parser.Parser;
import com.example.slackbot.slackApi.SlackApiCaller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDateTime;
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
    }

    private void removePreviousNotice(HashSet<String> prevNotices, HashSet<String> currentNotice) {
        for (String notice :
                prevNotices) {
            currentNotice.remove(notice);
        }
    }

    @Override
    @PostConstruct
    public void run() {
        while (true) {
            try {

                String strLeopoldNoticeHtml = leopoldApiCaller.getLeopoldNotice();
                HashSet<String> findNotices = parser.parse(strLeopoldNoticeHtml);
                String messageInfo = "[업데이트 일자] " + LocalDateTime.now().toString();
                removePreviousNotice(prevNotices, findNotices);

                slackApiCaller.postMessage(messageInfo);
                slackApiCaller.postMessages(findNotices);

                Thread.sleep(REPEAT_TIME);
            } catch (InterruptedException e) {
                log.error("Robot Thread run Exception : " + e.getMessage());
                throw new RuntimeException("Robot Thread run Exception : " + e.getMessage());
            }
        }

    }
}
