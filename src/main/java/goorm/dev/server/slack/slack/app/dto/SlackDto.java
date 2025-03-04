package goorm.dev.server.slack.slack.app.dto;

/**
 * Dto for common slack message. All the messages from slack will be converted to this dto
 */
public record SlackDto(
        String token,
        SlackEvent event,
        String type
) {
}
