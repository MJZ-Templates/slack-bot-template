package arkain.dev.server.slack.slack.app;

import groovy.util.logging.Slf4j;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * This class dispatch the requests from the slack.
 * request with slash command will be processed by slackCommandService
 * the other will be processed by slackEventService
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EventDispatcher {

    private final SlackCommandService slackCommandService;
    private final SlackEventService slackEventService;

    public String dispatch(HttpServletRequest request) {
        if (request.getContentType().contains("application/x-www-form-urlencoded")) {
            slackCommandService.processCommand(request);
        } else {
            slackEventService.processEvent(request);
        }

        return "ok";
    }
}
