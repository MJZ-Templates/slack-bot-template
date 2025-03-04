package goorm.dev.server.slack.slack.app.command;

import goorm.dev.server.slack.slack.app.CommandService;
import goorm.dev.server.slack.slack.app.dto.SlackCommandRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Just copy and past new command class and change the class name
 * change supports method to check if the slash command matches the one you set
 * and processCommand method will have the actual logic based on the command
 */
@Service
@RequiredArgsConstructor
public class TemplateCommand  implements CommandService {

    private static final String SLASH_COMMAND = "/{my_command}";

    @Override
    public boolean supports(SlackCommandRequest dto) {
        // check if the command matches the one I set
        return dto.command().equals(SLASH_COMMAND);
    }

    @Override
    public String processCommand(SlackCommandRequest dto) {
        // actual logic based on the command
        return null;
    }
}
