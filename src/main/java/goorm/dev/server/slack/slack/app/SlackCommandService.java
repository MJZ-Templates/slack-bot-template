package goorm.dev.server.slack.slack.app;

import goorm.dev.server.slack.slack.app.dto.SlackCommandRequest;
import goorm.dev.server.slack.slack.app.util.SlackCommandConverter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class processes the command from the Slack app.
 * Just add class that implements TemplateCommand and Spring will automatically inject new TemplateService.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SlackCommandService {

    private final SlackCommandConverter converter;
    private final MessageService messageService;
    private final List<CommandService> commandServices;

    public String processCommand(HttpServletRequest request) {
        SlackCommandRequest dto = converter.convert(request);
        CommandService commandService = getCommandService(dto);

        log.info("SlackCommandService.processCommand: {}", dto);
        String result = commandService.processCommand(dto);
        messageService.sendMessage(dto.channelId(), result);
        return result;
    }

    private CommandService getCommandService(SlackCommandRequest dto) {
        return commandServices.stream()
                .filter(commandService -> commandService.supports(dto))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 명령어입니다."));
    }
}

