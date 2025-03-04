package goorm.dev.server.slack.slack.ui;

import goorm.dev.server.slack.slack.app.*;
import groovy.util.logging.Slf4j;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventDispatcher {

    private final SlackCommandService slackCommandService;
    private final SlackEventService slackEventService;

    public String dispatch(HttpServletRequest request) {
        if (request.getContentType().contains("application/x-www-form-urlencoded")) {
            return slackCommandService.processCommand(request);
        } else {
            return slackEventService.processEvent(request);
        }
    }
}
