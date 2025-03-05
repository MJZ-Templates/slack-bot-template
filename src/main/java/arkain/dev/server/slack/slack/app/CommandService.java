package arkain.dev.server.slack.slack.app;

import arkain.dev.server.slack.slack.app.dto.SlackCommandRequest;

public interface CommandService {
    boolean supports(SlackCommandRequest dto);

    String processCommand(SlackCommandRequest dto);
}
