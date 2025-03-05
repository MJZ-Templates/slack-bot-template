#!/bin/bash

# Prompt the user to enter their Slack API token securely (input is hidden from the terminal)
read -sp "Enter your Slack Bot Token: " SLACK_TOKEN
echo ""

# Call the Slack API to retrieve the Bot ID and format the JSON output
BOT_ID=$(curl -s -X POST "https://slack.com/api/auth.test" \
     -H "Authorization: Bearer $SLACK_TOKEN" \
     -H "Content-Type: application/json" | jq -r '.user_id')

# Output the result
if [[ "$BOT_ID" == "null" || -z "$BOT_ID" ]]; then
    echo "❌ Failed to retrieve Bot ID. Please check your token."
else
    echo "✅ Slack Bot ID: $BOT_ID"
fi
