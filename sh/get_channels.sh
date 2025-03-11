#!/bin/bash

# Prompt the user to enter their Slack API token securely
read -sp "Enter your Slack Bot Token: " SLACK_TOKEN
echo ""

# Clear the token variable after the API call
trap 'SLACK_TOKEN=""; unset SLACK_TOKEN' EXIT

# Call the Slack API to retrieve the list of channels and format the JSON output
curl -s -X GET "https://slack.com/api/conversations.list" \
     -H "Authorization: Bearer $SLACK_TOKEN" \
     -H "Content-Type: application/json" | jq . || { echo "API call failed: Please check if the token is valid."; exit 1; }
