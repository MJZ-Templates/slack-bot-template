#!/bin/bash

# Prompt the user to enter their Slack API token securely (input is hidden from the terminal)
read -sp "Enter your Slack Bot Token: " SLACK_TOKEN
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

BOT_ID=$(echo "$BODY" | jq -r '.user_id')

# Output the result
if [[ "$BOT_ID" == "null" || -z "$BOT_ID" ]]; then
    echo "❌ Failed to retrieve Bot ID. Please check your token."
else
    echo "✅ Slack Bot ID: $BOT_ID"