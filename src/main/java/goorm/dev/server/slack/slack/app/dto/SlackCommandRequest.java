package goorm.dev.server.slack.slack.app.dto;

public record SlackCommandRequest(
        String token,
        String channelId,
        String channelName,
        String userId,
        String userName,
        String command,
        String text
) {
}
