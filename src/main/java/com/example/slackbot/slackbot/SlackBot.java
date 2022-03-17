package com.example.slackbot.slackbot;

import com.example.slackbot.LeopoldApi.LeopoldApiCaller;
import com.example.slackbot.Parser.Parser;
import com.example.slackbot.slackApi.SlackApiCaller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;

@Component
@Slf4j
@RequiredArgsConstructor
public class SlackBot {
    private final LeopoldApiCaller leopoldApiCaller;
    private final SlackApiCaller slackApiCaller;
    private final Parser parser;
    private final HashSet<String> prevNotices = new HashSet<>();

    // 매일 오전,오후 9시에 실행한다.
    @Scheduled(cron = "0 0 9,21 * * *")
    public void postLeopoldNotices() {
        log.info("레오폴드 공지사항 점검 실행");
        HashSet<String> newNotices = getNewNotices();

        if (hasUpdate(newNotices.size())) {
            log.info("신규 공지사항 발견");
            String message = createMessage(newNotices);

            saveNewNotices(newNotices);
            slackApiCaller.postMessage(message.toString());
        }
    }

    private String createMessage(HashSet<String> newNotices) {
        StringBuilder message = new StringBuilder();

        message.append("[신규 공지] ")
                .append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        for (var notice :
                newNotices) {
            message.append(System.lineSeparator())
                    .append(notice);
        }

        return message.toString();
    }

    private HashSet<String> getNewNotices() {
        String leopoldNoticeHtml = leopoldApiCaller.getLeopoldNotice();
        HashSet<String> parsedNotices = parser.parse(leopoldNoticeHtml);

        for (String notice :
                prevNotices) {
            parsedNotices.remove(notice);
        }

        return parsedNotices;
    }

    private void saveNewNotices(HashSet<String> findNotices) {
        log.info("신규 공지사항 저장");
        prevNotices.addAll(findNotices);
    }

    private boolean hasUpdate(int noticeSize) {
        return noticeSize > 0;
    }
}
