package arkain.dev.server.slack.slack.app;

import arkain.dev.server.slack.slack.app.dto.SlackDto;
import arkain.dev.server.slack.slack.app.util.SlackRequestConverter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


/**
 * This class handles API requests that are not slash commands.
 * If a regular message is sent instead of a slash command, SlackEventService will be invoked.
 */
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
        messageService.sendMessage(dto.event().channel(), "EventService called"); // Modify this code to customize its functionality.

        return "OK";
    }
}
