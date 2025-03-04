# Slack API Bot Template

## 1. Template Introduction

This template is designed for developing a Slack API Bot.

<br>

## 2. Technology Stack

- **Java 21**
- **Spring Boot 3.4.3**
- **Spring AI**
    - **OpenAI API** (Customizable based on user needs)
    - AiService can be added to utilize other LLM APIs.
- **Bolt (Slack Integration)**

<br>

## 3. Environment Setup

### 1) Slack Configuration

Before developing a Slack API Bot, environment setup is required.

- Configuration can be done on the [Slack API site](https://api.slack.com/apps/).
- For more details, refer to [this guide](https://tech.goorm.io/ko/%EC%8A%AC%EB%9E%99%EB%B4%87-%EB%A7%8C%EB%93%A4%EA%B8%B0-%EC%96%B4%EB%A0%B5%EC%A7%80-%EC%95%8A%EC%95%84%EC%9A%94/).
- You can obtain **SIGNING_SECRET, SLACK_BOT_TOKEN** through the configuration.

<br>

#### Setting Environment Variables:

**1️⃣ Using `export` Command**
Run the following commands in the terminal (inside the project root). Note that these need to be re-entered each time the container restarts.

```sh
export SIGNING_SECRET="your_signing_secret"
export SLACK_BOT_TOKEN="your_bot_token"
```

To make the environment variables persistent, add them to the `bashrc` file:

1. Copy the above commands.
2. Open the bashrc file:

```sh
vi ~/.bashrc
```

3. Press `shift + g` to go to the end of the file.
4. Press `o` to enter insert mode.
5. Paste the copied environment variables.
6. Press `esc` to exit insert mode.
7. Type `:wq` and press enter to save and exit.

<br>

**2️⃣ Registering in `application.yml`**

```yaml
slack:
  signing-secret: ${SLACK_SIGNING_SECRET}
  bot-token: ${SLACK_BOT_TOKEN}
```

**Warning:** Do not expose your environment variables publicly. If writing them in `application.yml`, avoid sharing it externally.

<br>

### 2) OpenAI API Configuration

To use the LLM feature, **OPENAI_API_KEY** is required.

- The **API Key** can be obtained from the [OpenAI API site](https://openai.com/index/openai-api/).
- The key should be registered in `application.yml` or set as an environment variable.

<br>

#### Setting Environment Variables:

**1️⃣ Using `export` Command**

```sh
export OPENAI_API_KEY="your_openai_api_key"
```

To make it persistent, follow the same steps as above to add it to `~/.bashrc`.

<br>

**2️⃣ Registering in `application.yml`**

```yaml
spring:
  ai:
    openai:
      api-key: ${OPENAI_API_KEY}
```

Again, **do not expose your API key publicly.**

<br>

### 3) Using Shell Commands

If **Channel ID** or **Slack Bot ID** is needed, this template provides shell commands to fetch these values easily.

- Requires only **SLACK_BOT_TOKEN** to execute.

<br>

#### Retrieve Channel ID

```sh
./sh/get_channels.sh
```

Enter `SLACK_BOT_TOKEN` when prompted.

<br>

#### Retrieve SlackBot ID

```sh
./sh/get_slackbot_id.sh
```

Enter `SLACK_BOT_TOKEN` when prompted.

<br>

### 4) Changing OpenAI API Model

By default, this template uses `gpt-4o-mini`. To change the model, update `application.yml`:

```yaml
chat:
  options:
    model: ${OPENAI_MODEL:gpt-4o-mini}
```

Refer to [OpenAI model documentation](https://platform.openai.com/docs/models) to change the model.

Examples:
```yaml
chat:
  options:
    model: ${OPENAI_MODEL:gpt-4o-latest}
```

<br>

## 4. Customization

### 1) Adding Custom Commands

- Copy and paste the `TemplateCommand` in `src/main/java/goorm/dev/server/slack/common` to create a new command.
- Ensure that the new command class has a unique name.
- The command should match the **Slash Commands** configured in the Slack API.
- Refer to the `MangoCommand` example, which uses OpenAI to answer user queries.
- To add a new command, visit the [Slack API site](https://api.slack.com/apps/) and configure **Slash Commands**.

```java
@Service
@RequiredArgsConstructor
public class TemplateCommand implements CommandService {

  private static final String SLASH_COMMAND = "/{my_command}";

  @Override
  public boolean supports(SlackCommandRequest dto) {
    return dto.command().equals(SLASH_COMMAND);
  }

  @Override
  public String processCommand(SlackCommandRequest dto) {
    return null;
  }
}
```

Example: Adding a `/goorm` slash command.

1. Visit the Slack API site and create a new Slash Command under:
    - Features > Slash Commands > Create New Command
    - Add `/goorm` as an example.
2. Copy and paste `TemplateCommand` and rename it to `GoormCommand`.

```java
@Service
@RequiredArgsConstructor
public class GoormCommand implements CommandService {

  private static final String SLASH_COMMAND = "/goorm";

  @Override
  public boolean supports(SlackCommandRequest dto) {
    return dto.command().equals(SLASH_COMMAND);
  }

  @Override
  public String processCommand(SlackCommandRequest dto) {
    return "Hello from GoormCommand!";
  }
}
```

6. Implement the desired logic inside `processCommand`.
7. Use OpenAI LLM integration for enhanced responses.

<br>

### 2) Customizing OpenAI Prompts

- Modify `src/main/resources/templates/prompt.txt` to customize prompts.
- Changes will take effect upon server restart.
