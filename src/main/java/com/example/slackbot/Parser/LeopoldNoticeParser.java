package com.example.slackbot.Parser;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LeopoldNoticeParser implements Parser {

    @Override
    public List<String> parse(String target) {
        List<String> notices = parseAllNotice(target);

        return notices;
    }

    private List<String> parseAllNotice(String target) {
        List<String> notices = new ArrayList<>();

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
