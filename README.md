```
┌─────────────────────────────────────────────────────────┐
                 _              _               _
                | |            (_)             (_)
   __ _   _ __  | | __   __ _   _   _ __        _    ___
  / _` | | '__| | |/ /  / _` | | | | '_ \      | |  / _ \
 | (_| | | |    |   <  | (_| | | | | | | |  _  | | | (_) |
  \__,_| |_|    |_|\_\  \__,_| |_| |_| |_| (_) |_|  \___/


└─────────────────────────────────────────────────────────┘
```

# Slack API Bot Base Package

- This template is designed for developing a Slack API Bot using `Spring Boot` and `Bolt`, `Spring AI`.

## Features

- Easy to create a custom Slack bot.
- Pre-configured `Spring Boot` server.
- Easy integration with `OpenAI API`.

## Folder Structure

```
.
├── gradle
│   └── wrapper
├── sh
└── src
      └── main
            ├── java
            │   └── arkain
            │       └── dev
            │           └── server
            │               └── slack
            │                   ├── ai
            │                   │   └── app
            │                   │       └── openai
            │                   ├── common
            │                   │   └── web
            │                   │       └── ui
            │                   ├── config
            │                   └── slack
            │                       ├── app
            │                       │   ├── command
            │                       │   ├── dto
            │                       │   └── util
            │                       └── ui
            └── resources
                ├── static
                └── templates
```

## Getting Started

### Prerequisites

- **Java 21**
- **Spring Boot 3.4.3**
- **Spring AI**
    - **OpenAI API** (Customizable based on user needs)
    - AiService can be added to utilize other LLM APIs.
- **Bolt (Slack Integration)**

### Installation & Setup

**Check URL and Port**

- Click Running URL and Port in the top left menu bar.
- When you run the project, the server will be available at port 8080.

<br>

**Slack Configuration**

1. Before developing a Slack API Bot, environment setup is required.
2. Configuration can be done on the [Slack API site](https://api.slack.com/apps/).
3. You can get **SIGNING_SECRET, SLACK_BOT_TOKEN** through the configuration.

**Using `export` Command**

Run the following commands in the terminal (inside the project root). Note that these need to be re-entered each time
the container restarts.

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

Or you can Register in application.yml

```yaml
slack:
  signing-secret: ${SLACK_SIGNING_SECRET}
  bot-token: ${SLACK_BOT_TOKEN}
```

**Warning:** Do not expose your environment variables publicly. If writing them in `application.yml`, avoid sharing it
externally.

<br>

**OpenAI API Configuration**

To use the LLM feature, **OPENAI_API_KEY** is required.

- The **API Key** can be obtained from the [OpenAI API site](https://openai.com/index/openai-api/).
- The key should be registered in `application.yml` or set as an environment variable.

You can use `export` command in the terminal.

```sh
export OPENAI_API_KEY="your_openai_api_key"
```

To make it persistent, follow the same steps as above to add it to `~/.bashrc`.

Also, you can register it in `application.yml`. Again, **do not expose your API key publicly.**

```yaml
spring:
  ai:
    openai:
      api-key: ${OPENAI_API_KEY}
```

<br>

**Shell Commands**

- If the Channel ID or Slack Bot ID is needed, the template provides shell commands to fetch these values easily.
- Only the `SLACK_BOT_TOKEN` is required to execute these commands.
  <br>

1. Retrieve Channel ID

```sh
./sh/get_channels.sh
```

If you have set the `SLACK_BOT_TOKEN` as an environment variable, the channels and their IDs will be automatically
provided. If the `SLACK_BOT_TOKEN` is not set as an environment variable, you can enter the `SLACK_BOT_TOKEN` after the
program runs to obtain the channel ID.

2. Retrieve SlackBot ID

```sh
./sh/get_slackbot_id.sh
```

If you have set the `SLACK_BOT_TOKEN` as an environment variable, the `SLACK_BOT_ID` will be automatically provided. If
the `SLACK_BOT_TOKEN` is not set as an environment variable, you can enter the `SLACK_BOT_TOKEN` after the program runs
to obtain the `SLACK_BOT_ID`.

<br>

**Changing OpenAI API Model**

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

**Adding Custom Commands**

- Copy and paste the `TemplateCommand` in `/src/.../slack/app/command/TemplateCommand.java` to create a new command.
- Ensure that the new command class has a unique name.
- The command should match the **Slash Commands** configured in the Slack API.
- Refer to the `ArkainCommand` example, which uses OpenAI to answer user queries.
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
        return "Template command executed. Customize this part.";
    }
}
```

Example: Adding a `/arkain` slash command.

1. Visit the Slack API site and create a new Slash Command under:
    - Features > Slash Commands > Create New Command
    - Add `/arkain` as an example.
2. Copy and paste `TemplateCommand` and rename it to `ArkainCommand`.

```java

@Service
@RequiredArgsConstructor
public class ArkainCommand implements CommandService {

    private static final String SLASH_COMMAND = "/arkain";

    @Override
    public boolean supports(SlackCommandRequest dto) {
        return dto.command().equals(SLASH_COMMAND);
    }

    @Override
    public String processCommand(SlackCommandRequest dto) {
        return "Hello from ArkainCommand!";
    }
}
```

3. Implement the desired logic inside `processCommand`.
4. Use OpenAI LLM integration for enhanced responses.

<br>

**Customizing OpenAI Prompts**

- Modify `src/main/resources/templates/prompt.txt` to customize prompts.
- Changes will take effect upon server restart.

### Contributing

We welcome contributions to enhance this boilerplate! Feel free to submit a pull request or open an issue for
suggestions or bug fixes.

## 🔧 Tip & Guide

1. **Get URL and Port**

    - You can get the default URL/Port and add URL/Port on the top right.
    - Move your mouse pointer over the [Preview] → [Running URL and Port] button in the menu bar (no click needed).

2. **Command feature**

    - You can simply run your script using the shortcut icons on the top right.
    - Move your mouse pointer over the [Run] → [Add run command] button in the menu bar (no click needed).

3. **SSH Configuration**
    - This feature is only available for membership users.
    - You can SSH to the Arkain container from the outside via the [Menu]->[SSH Configuration] in menu bar.

### 💬 Support & Documentation

Visit [https://arkain.io](https://arkain.io) to support and learn more about using Arkain.
To watch some usage guides, visit [https://docs.arkain.io](https://docs.arkain.io)
