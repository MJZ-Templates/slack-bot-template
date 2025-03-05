package arkain.dev.server.slack.slack.ui;

import arkain.dev.server.slack.slack.app.EventDispatcher;
import arkain.dev.server.slack.slack.app.MessageService;
import arkain.dev.server.slack.slack.app.dto.SlackMessageRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * All messages will be received by this class.
 */
@RestController
@RequestMapping("/api/slack")
@RequiredArgsConstructor
public class SlackController {

    private final MessageService messageService;
    private final EventDispatcher eventDispatcher;

    @PostMapping("/events")
    public ResponseEntity<String> handleSlackRequest(HttpServletRequest request) {
        return ResponseEntity.ok(eventDispatcher.dispatch(request));
    }

    // send message to specific channels or users
    @PostMapping("/message")
    public ResponseEntity<String> sendMessage(@RequestBody SlackMessageRequest dto) {
        messageService.sendMessage(dto);
        return ResponseEntity.ok("Message sent to " + dto.channel());
    }
}