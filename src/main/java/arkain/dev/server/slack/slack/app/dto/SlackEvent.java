package arkain.dev.server.slack.slack.app.dto;

/**
 * Dto for slack event. This is used for incoming messages from slack
 */
public record SlackEvent(
        String user,
        String type,
        String text,
        String channel
) {
}
