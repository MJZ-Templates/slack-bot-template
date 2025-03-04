package goorm.dev.server.slack.slack.app;

import goorm.dev.server.slack.slack.app.dto.SlackCommandRequest;

public interface CommandService {
    boolean supports(SlackCommandRequest dto);

    String processCommand(SlackCommandRequest dto);
}
