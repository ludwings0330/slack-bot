package com.example.slackbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SlackBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(SlackBotApplication.class, args);
    }

}
