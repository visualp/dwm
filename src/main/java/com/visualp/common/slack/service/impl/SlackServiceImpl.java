package com.visualp.common.slack.service.impl;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.webhook.Payload;
import com.github.seratch.jslack.api.webhook.WebhookResponse;
import com.visualp.common.slack.service.SlackService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;

/**
 * 1. 프로젝트명 : dwm
 * 2. 패키지명   : com.visualp.common.slack.service.impl
 * 3. 작성일     : 2021. 01. 06. 오후 12:20
 * 4. 작성자     : 고병만
 * 5. 이메일     : scormrte@gmail.com
 * [refference] : https://dev.to/silviobuss/send-slack-messages-with-java-in-5-minutes-2lio
 */
@Service
public class SlackServiceImpl implements SlackService {
    @Value("${slack.webhook}")
    private String urlSlackWebHook;

    @Value("${slack.channel}")
    private String channel;

    @Value("${slack.username}")
    private String username;

    @Override
    public void sendMessage(String message) {
        StringBuilder sb = new StringBuilder();
        sb.append(message);
        process(sb.toString());
    }

    public void process(String message) {
        Payload payload = Payload.builder()
                .channel(channel)
                .username(username)
                .iconEmoji(":rocket:")
                .text(message)
                .build();

        try {
            WebhookResponse webhookResponse = Slack.getInstance().send(urlSlackWebHook,payload);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
