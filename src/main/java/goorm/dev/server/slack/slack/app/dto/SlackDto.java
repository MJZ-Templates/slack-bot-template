package goorm.dev.server.slack.slack.app.dto;

public record SlackDto(
        String token,
        SlackEvent event,
        String type
) {
}
