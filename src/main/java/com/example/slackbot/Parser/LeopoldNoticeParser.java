package com.example.slackbot.Parser;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Component
public class LeopoldNoticeParser implements Parser {

    @Override
    public HashSet<String> parse(String target) {
        return parseAllNotice(target);
    }

    private HashSet<String> parseAllNotice(String target) {
        HashSet<String> notices = new HashSet<>();

        while (hasNext(target)) {
            int startIndex = target.indexOf("[공지]");
            target = target.substring(startIndex);
            int endIndex = target.indexOf("</b>");

            notices.add(target.substring(0, endIndex));
            target = target.substring(endIndex);
        }

        return notices;
    }

    private boolean hasNext(String target) {
        return target.contains("[공지]");
    }
}
