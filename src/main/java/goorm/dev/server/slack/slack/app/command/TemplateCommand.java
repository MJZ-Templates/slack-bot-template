package goorm.dev.server.slack.slack.app.command;

import goorm.dev.server.slack.slack.app.CommandService;
import goorm.dev.server.slack.slack.app.dto.SlackCommandRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TemplateCommand  implements CommandService {

    @Override
    public boolean supports(SlackCommandRequest dto) {
        // 내가 설정한 command와 일치하는지 확인
        return dto.command().equals("/{my_command}");
    }

    @Override
    public String processCommand(SlackCommandRequest dto) {
        // command에 따라 다른 로직을 수행
        return null;
    }
}
