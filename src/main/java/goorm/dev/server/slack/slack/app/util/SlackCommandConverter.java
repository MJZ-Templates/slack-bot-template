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

        // 요청의 form 데이터를 직접 읽어서 key-value 형태로 저장
        request.getParameterMap().forEach((key, values) -> {
            if (values.length > 0) {
                params.put(key, values[0]);
            }
        });

        // Slash Command 요청에는 'text', 'user_id', 'channel_id'가 포함됨
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
