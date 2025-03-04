package goorm.dev.server.slack.slack.app.dto;

public record SlackEvent(
        String user,
        String type,
        String text,
        String channel
) {
}
