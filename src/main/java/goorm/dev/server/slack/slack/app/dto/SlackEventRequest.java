package goorm.dev.server.slack.slack.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SlackEventRequest(String type, String challenge, Event event) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Event(String type, String channel, String text, @JsonProperty("user") String userId) {}
}