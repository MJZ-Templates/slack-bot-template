package arkain.dev.server.slack.slack.app.dto;

/**
 * Dto for slack message request. This is used for sending messages to slack.
 * This is for custom api request.
 */
public record SlackMessageRequest(String channel, String message) {
}
