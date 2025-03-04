package goorm.dev.server.slack.slack.app.dto;

/**
 * Dto for slash command request
 */
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
