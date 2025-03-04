# Slack API Bot 템플릿

## 1. 템플릿 소개

해당 템플릿은 Slack API Bot 개발을 위한 템플릿입니다.

## 2. 기술 스택

- **Java21**
- **Spring Boot 3.4.3**
- **Spring AI**
  - **OpenAI API** (사용자가 원하는 방향으로 커스텀 가능)
  - AiService를 추가하여 다른 LLM API를 활용가능합니다.
- **Bolt (Slack 연동)**

## 3. 환경 설정

### 1) Slack 설정

Slack API Bot을 개발하기 전에 환경 설정이 필요합니다.

- 설정은 [Slack API 사이트](https://api.slack.com/apps/)에서 진행할 수 있습니다.
- 보다 자세한 내용은 [해당 가이드](https://tech.goorm.io/ko/%EC%8A%AC%EB%9E%99%EB%B4%87-%EB%A7%8C%EB%93%A4%EA%B8%B0-%EC%96%B4%EB%A0%B5%EC%A7%80-%EC%95%8A%EC%95%84%EC%9A%94/)를 참고하세요.
- 설정을 통해 **SIGNING_SECRET, SLACK_BOT_TOKEN** 을 얻을 수 있습니다.
- 환경 변수 등록 방법:

1️⃣ export를 이용한 환경 변수 등록

```sh
export SIGNING_SECRET="your_signing_secret"
export SLACK_BOT_TOKEN="your_bot_token"
```

2️⃣ application.yml을 이용한 환경 변수 등록

```
slack:
  signing-secret: ${SLACK_SIGNING_SECRET}
  bot-token: ${SLACK_BOT_TOKEN}
```

환경변수는 절대로 외부에 노출되면 안됩니다. 만약 application.yml에 slack api를 작성한다면 외부 공유를 하지 말아주세요.

### 2) OpenAI API 설정

LLM 기능을 사용하기 위해 **OPENAI_API_KEY**가 필요합니다.

- [OpenAI API 사이트](https://openai.com/index/openai-api/)에서 **API Key**를 발급받을 수 있습니다.
- 발급받은 Key는 **application.yml**에 작성하거나 환경 변수로 등록해야 합니다.

환경 변수 등록 방법:

1️⃣ export를 이용한 환경 변수 등록

```sh
export OPENAI_API_KEY="your_openai_api_key"
```

2️⃣ application.yml을 이용한 환경 변수 등록

```
spring:
  ai:
    openai:
      api-key: ${OPENAI_API_KEY}
```

마찬가지로 OPENAI_API_KEY는 절대로 외부에 노출되면 안됩니다. 만약 application.yml에 open ai api를 작성한다면 외부 공유를 하지 말아주세요.

### 3) Shell 명령어 사용하기

Slack API 문서를 참고하여 **Channel ID, Slack Bot ID**를 알아내야 합니다.

- 해당 템플릿은 필요한 환경 변수 값을 쉽게 찾을 수 있도록 **sh 명령어**를 제공합니다.
- **SLACK_BOT_TOKEN** 을 이용해 실행할 수 있습니다.

#### Channel ID 조회

```sh
./sh/get_channels.sh
```

터미널에서 실행한 후 `SLACK_BOT_TOKEN`을 입력하면 됩니다.

#### SlackBot ID 조회

```sh
./sh/get_slackbot_id.sh
```

터미널에서 실행한 후 `SLACK_BOT_TOKEN`을 입력하면 됩니다.

### 4) OpenAI API 모델 변경하기

템플릿은 기본적으로 gpt-4o-mini 모델을 이용합니다. 모델을 변경하고 싶다면 application.yml에 있는 model을 변경해야 합니다.

```
chat:
  options:
    model: ${OPENAI_MODEL:gpt-4o-mini}
```

[OpenAI 모델 문서](https://platform.openai.com/docs/models)를 참고해 모델을 변경할 수 있습니다.

## 4. 커스텀하기

### 1) 커스텀 커맨드 추가

- `slack/app/command` 경로의 `TemplateCommand`를 복사하여 새로운 기능을 추가할 수 있습니다.

```java
@Service
@RequiredArgsConstructor
public class TemplateCommand implements CommandService {
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
```

- 예제 `MangoCommand`를 참고하면, OpenAI를 활용해 사용자의 질문에 답변하는 기능을 구현할 수 있습니다.
  - MangoCommand는 LLM을 이용해 사용자가 질문한 내용에 대해 답변해줍니다.
- 새로운 커맨드를 추가하려면 [Slack API 사이트](https://api.slack.com/apps/)에서 **Slash Commands**를 추가해야 합니다.

### 2) OpenAI 프롬프트 커스텀

- `src/main/resources/templates/prompt.txt` 파일을 수정하여 **프롬프트 엔지니어링**이 가능합니다.
- 원하는 내용을 `.txt` 파일에 작성하고 서버를 재시작하면 자동으로 반영됩니다.
