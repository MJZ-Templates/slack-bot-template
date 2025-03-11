package arkain.dev.server.slack.slack.app.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import arkain.dev.server.slack.slack.app.dto.SlackDto;
import arkain.dev.server.slack.slack.app.dto.SlackEvent;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SlackRequestConverter {

    private final ObjectMapper objectMapper;

    public SlackDto convert(HttpServletRequest request) {
        try {
            // convert request body to JSON
            String requestBody;
            try (var reader = request.getReader()) {
                requestBody = reader.lines().collect(java.util.stream.Collectors.joining());
            }

            // convert json data to map
            Map<String, Object> jsonMap = objectMapper.readValue(requestBody, Map.class);
            Object eventObj = jsonMap.get("event");
            Map<String, Object> eventMap = (eventObj instanceof Map<?, ?> map) ? (Map<String, Object>) map : null;

            // create dto only if event exists
            if (eventMap != null) {
                SlackEvent event = new SlackEvent(
                        (String) eventMap.getOrDefault("user", ""),
                        (String) eventMap.getOrDefault("type", ""),
                        (String) eventMap.getOrDefault("text", ""),
                        (String) eventMap.getOrDefault("channel", "")
                );

                return new SlackDto(
                        (String) jsonMap.getOrDefault("token", ""),
                        event,
                        (String) jsonMap.getOrDefault("type", "")
                );
            }

            return null; // return null if event does not exist
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse Slack request", e);
        }
    }
}
