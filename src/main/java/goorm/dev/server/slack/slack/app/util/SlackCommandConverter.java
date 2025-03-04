package goorm.dev.server.slack.slack.app.util;

import goorm.dev.server.slack.slack.app.dto.SlackCommandRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SlackCommandConverter {

    public SlackCommandRequest convert(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>();

        // convert request form data to key-value format
        request.getParameterMap().forEach((key, values) -> {
            if (values.length > 0) {
                params.put(key, values[0]);
            }
        });

        return new SlackCommandRequest(
                (String) params.getOrDefault("token", ""),
                (String) params.getOrDefault("channel_id", ""),
                (String) params.getOrDefault("channel_name", ""),
                (String) params.getOrDefault("user_id", ""),
                (String) params.getOrDefault("user_name", ""),
                (String) params.getOrDefault("command", ""),
                (String) params.getOrDefault("text", "")
        );
    }
}
