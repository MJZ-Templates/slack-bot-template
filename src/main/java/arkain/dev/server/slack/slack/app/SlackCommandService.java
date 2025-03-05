package arkain.dev.server.slack.slack.app;

import arkain.dev.server.slack.slack.app.dto.SlackCommandRequest;
import arkain.dev.server.slack.slack.app.util.SlackCommandConverter;
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
//        messageService.sendMessage(dto.channelId(), result); // If you send a message to messageService.send, the message will be delivered to all users in the channel.
        return result; // If you return the message(result), the message only delivered to called user.
    }

    private CommandService getCommandService(SlackCommandRequest dto) {
        return commandServices.stream()
                .filter(commandService -> commandService.supports(dto))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Not supported command"));
    }
}

