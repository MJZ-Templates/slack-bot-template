package goorm.dev.server.slack.slack.app.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import goorm.dev.server.slack.slack.app.dto.SlackDto;
import goorm.dev.server.slack.slack.app.dto.SlackEvent;
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
            // 요청 본문을 JSON으로 변환
            String requestBody;
            try (var reader = request.getReader()) {
                requestBody = reader.lines().reduce("", (acc, line) -> acc + line);
            }

            // JSON 데이터를 Map으로 변환
            Map<String, Object> jsonMap = objectMapper.readValue(requestBody, Map.class);
            Map<String, Object> eventMap = (Map<String, Object>) jsonMap.get("event");

            // 이벤트가 존재하는 경우만 DTO 생성
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

            return null; // 이벤트가 없는 경우 null 반환
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse Slack request", e);
        }
    }
}
