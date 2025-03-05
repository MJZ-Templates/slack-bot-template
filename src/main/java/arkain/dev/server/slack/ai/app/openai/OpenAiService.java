package arkain.dev.server.slack.ai.app.openai;

import arkain.dev.server.slack.ai.app.AiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class OpenAiService implements AiService {

    private final ChatModel chatModel;

    @Value("classpath:/templates/prompt.txt")
    private Resource examplePrompt;

    @Override
    public String getResponse(String message) {
        PromptTemplate promptTemplate = new PromptTemplate(examplePrompt);

        // can add prompt parameters with Map.of("key", "value")
        Prompt prompt = promptTemplate.create(Map.of("message", message));
        ChatResponse response = chatModel.call(prompt);

        return response.getResult().getOutput().getContent();
    }
}
