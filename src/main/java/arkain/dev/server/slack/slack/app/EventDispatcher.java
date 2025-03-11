package arkain.dev.server.slack.slack.app;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        log.debug("Received request from Slack: {}", request.getRequestURI());
        try {
            if (request.getContentType().contains("application/x-www-form-urlencoded")) {
                log.debug("Processing command request");
                slackCommandService.processCommand(request);
            } else {
                log.debug("Processing event request");
                slackEventService.processEvent(request);
            }
        } catch (Exception e) {
            log.error("Error processing Slack request", e);
            return "error";
        }
        return "ok";
    }
}
