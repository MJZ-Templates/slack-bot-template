package goorm.dev.server.slack.slack.app;

import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import goorm.dev.server.slack.slack.app.dto.SlackMessageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * This class actually sends messages to slack.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService {

    private final MethodsClient slack;

    public void sendMessage(String channel, String text) {
        ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                .channel(channel)
                .text(text)
                .build();
        try {
            slack.chatPostMessage(request);
        } catch (IOException | SlackApiException e) {
            log.error("Slack message send failed: {}", e.getMessage(), e);
        }
    }

    public void sendMessage(SlackMessageRequest dto) {
        sendMessage(dto.channel(), dto.message());
    }
}
