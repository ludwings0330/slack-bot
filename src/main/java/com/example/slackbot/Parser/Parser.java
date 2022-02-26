package com.example.slackbot.Parser;

import java.util.HashSet;
import java.util.List;

public interface Parser {
    HashSet<String> parse(String target);
}
