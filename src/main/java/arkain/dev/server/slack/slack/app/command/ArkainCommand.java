package arkain.dev.server.slack.slack.app.command;

import arkain.dev.server.slack.ai.app.AiService;
import arkain.dev.server.slack.slack.app.CommandService;
import arkain.dev.server.slack.slack.app.dto.SlackCommandRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArkainCommand implements CommandService {

    private static final String SLASH_COMMAND = "/arkain";

    private final AiService aiService;

    @Override
    public boolean supports(SlackCommandRequest dto) {
        return SLASH_COMMAND.equals(dto.command());
    }

    @Override
    public String processCommand(SlackCommandRequest dto) {
        String text = dto.text();
        if (text == null || text.trim().isEmpty()) {
            return "Please enter a question.";
        }
        return aiService.getResponse(text);
    }
}
