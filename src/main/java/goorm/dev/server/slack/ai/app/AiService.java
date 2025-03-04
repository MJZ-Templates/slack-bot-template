package goorm.dev.server.slack.ai.app;

// Implements AiService to add additional LLM models
public interface AiService {
    String getResponse(String message);
}
