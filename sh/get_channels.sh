#!/bin/bash

# Prompt the user to enter their Slack API token securely
read -sp "Enter your Slack Bot Token: " SLACK_TOKEN
echo ""

# Call the Slack API to retrieve the list of channels and format the JSON output
curl -s -X GET "https://slack.com/api/conversations.list" \
     -H "Authorization: Bearer $SLACK_TOKEN" \
     -H "Content-Type: application/json" | jq .
