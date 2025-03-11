#!/bin/bash

# Prompt the user to enter their Slack API token securely (input is hidden from the terminal)
if [ -z "$SLACK_BOT_TOKEN" ]; then
  read -sp "Enter your Slack Bot Token: " SLACK_TOKEN
else
  SLACK_TOKEN="$SLACK_BOT_TOKEN"
  echo "Using token from environment variable"
fi
echo ""

# Call the Slack API to retrieve the Bot ID and format the JSON output
RESPONSE=$(curl -s -w "\n%{http_code}" -X POST "https://slack.com/api/auth.test" \
     -H "Authorization: Bearer $SLACK_TOKEN" \
     -H "Content-Type: application/json")

HTTP_CODE=$(echo "$RESPONSE" | tail -n1)
BODY=$(echo "$RESPONSE" | sed '$ d')

if [[ $HTTP_CODE -ne 200 ]]; then
    echo "❌ API call failed: HTTP CODE $HTTP_CODE"
    exit 1
fi

if [[ $(echo "$BODY" | jq -r '.ok') != "true" ]]; then
    echo "❌ API call failed: $(echo "$BODY" | jq -r '.error')"
    exit 1
fi

BOT_ID=$(echo "$BODY" | jq -r '.user_id')

# Output the result
if [[ "$BOT_ID" == "null" || -z "$BOT_ID" ]]; then
    echo "❌ Failed to retrieve Bot ID. Please check your token."
else
    echo "✅ Slack Bot ID: $BOT_ID"
fi
