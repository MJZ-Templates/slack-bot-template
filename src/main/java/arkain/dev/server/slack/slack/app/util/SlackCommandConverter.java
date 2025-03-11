package arkain.dev.server.slack.slack.app.util;

import arkain.dev.server.slack.slack.app.dto.SlackCommandRequest;
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
            if (values != null && values.length > 0) {
                params.put(key, values[0]);
            }
        });

        return new SlackCommandRequest(
                params.getOrDefault("token", ""),
                params.getOrDefault("channel_id", ""),
                params.getOrDefault("channel_name", ""),
                params.getOrDefault("user_id", ""),
                params.getOrDefault("user_name", ""),
                params.getOrDefault("command", ""),
                params.getOrDefault("text", "")
        );
    }
}
