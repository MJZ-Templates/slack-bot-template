package arkain.dev.server.slack.slack.ui;

import arkain.dev.server.slack.slack.app.MessageService;
import arkain.dev.server.slack.slack.app.dto.SlackMessageRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/slack")
@RequiredArgsConstructor
public class SlackController {

    private final MessageService messageService;
    private final EventDispatcher eventDispatcher;

    @PostMapping("/events")
    public String handleSlackRequest(HttpServletRequest request) {

        return eventDispatcher.dispatch(request);
    }

    // 특정 채널 또는 사용자에게 메시지 전송
    @PostMapping("/message")
    public ResponseEntity<String> sendMessage(@RequestBody SlackMessageRequest dto) {
        messageService.sendMessage(dto);
        return ResponseEntity.ok("Message sent to " + dto.channel());
    }
}