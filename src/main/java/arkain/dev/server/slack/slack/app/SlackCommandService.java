package arkain.dev.server.slack.slack.app;

import arkain.dev.server.slack.slack.app.dto.SlackCommandRequest;
import arkain.dev.server.slack.slack.app.util.SlackCommandConverter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
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

    @Async
    public void processCommand(HttpServletRequest request) {
        SlackCommandRequest dto = converter.convert(request);
        CommandService commandService = getCommandService(dto);

        log.info("SlackCommandService.processCommand: command={}, channel={}, user={}", dto.command(), dto.channelId(), dto.userId());
        String result = commandService.processCommand(dto);
        messageService.sendMessage(dto.channelId(), result); // If you send a message to messageService.sendMessage, the message will be delivered to all users in the channel.
    }

    private CommandService getCommandService(SlackCommandRequest dto) {
        return commandServices.stream()
                .filter(commandService -> commandService.supports(dto))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Not supported command: " + dto.command() + " from channel: " + dto.channelId() + " by user: " + dto.userId()));

    }
}

