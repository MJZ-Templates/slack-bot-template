package goorm.dev.server.slack.slack.app.command;

import goorm.dev.server.slack.ai.app.AiService;
import goorm.dev.server.slack.slack.app.CommandService;
import goorm.dev.server.slack.slack.app.dto.SlackCommandRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MangoCommand implements CommandService {

    private final AiService aiService;

    @Override
    public boolean supports(SlackCommandRequest dto) {
        return dto.command().equals("/mango");
    }

    @Override
    public String processCommand(SlackCommandRequest dto) {
        return aiService.getResponse(dto.text());
    }
}
