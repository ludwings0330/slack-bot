package com.example.slackbot.slackbot;

import com.example.slackbot.LeopoldApi.LeopoldApiCaller;
import com.example.slackbot.Parser.Parser;
import com.example.slackbot.slackApi.SlackApiCaller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashSet;

@Component
@Slf4j
public class SlackBot {
    private final LeopoldApiCaller leopoldApiCaller;
    private final SlackApiCaller slackApiCaller;
    private final Parser parser;

    private final int REPEAT_TIME = 1000 * 60 * 60 * 12;

    private final HashSet<String> prevNotices = new HashSet<>();

    public SlackBot(LeopoldApiCaller leopoldApiCaller, SlackApiCaller slackApiCaller, Parser parser) {
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

    @Scheduled(fixedDelay = REPEAT_TIME)
    public void postLeopoldNoticeToSlack() {
        String strLeopoldNoticeHtml = leopoldApiCaller.getLeopoldNotice();
        HashSet<String> findNotices = parser.parse(strLeopoldNoticeHtml);
        String messageInfo = "[업데이트 일자] " + LocalDateTime.now();
        removePreviousNotice(prevNotices, findNotices);
        saveCurrentNotices(findNotices);
        slackApiCaller.postMessage(messageInfo);
        slackApiCaller.postMessages(findNotices);
    }

    private void saveCurrentNotices(HashSet<String> findNotices) {
        prevNotices.addAll(findNotices);
    }
}
