package goorm.dev.server.slack.slack.app;

import goorm.dev.server.slack.slack.app.dto.SlackDto;
import goorm.dev.server.slack.slack.app.util.SlackRequestConverter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class SlackEventService {

    @Value("${slack.bot.name}")
    private String botName;

    private final SlackRequestConverter converter;
    private final MessageService messageService;

    public String processEvent(HttpServletRequest request) {
        SlackDto dto = converter.convert(request);
        if (dto.event().user().equals(botName)) {
            return "Ignoring bot message.";
        }

        log.info("SlackEventService.processEvent: {}", dto);
        messageService.sendMessage(dto.event().channel(), "EventService 호출됨");

        return "OK";
    }
}
