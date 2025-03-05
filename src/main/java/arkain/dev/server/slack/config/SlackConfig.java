package arkain.dev.server.slack.config;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SlackConfig {

    @Value("${slack.bot-token}")
    private String slackBotToken;

    @Bean
    public MethodsClient methodsClient() {
        return Slack.getInstance().methods(slackBotToken);
    }
}