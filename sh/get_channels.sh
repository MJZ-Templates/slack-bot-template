#!/bin/bash

# 사용자 입력을 통해 Slack API 토큰을 받음
read -sp "Enter your Slack Bot Token: " SLACK_TOKEN
echo ""

# Slack API 호출 (채널 목록 조회) + JSON 포맷팅
curl -s -X GET "https://slack.com/api/conversations.list" \
     -H "Authorization: Bearer $SLACK_TOKEN" \
     -H "Content-Type: application/json" | jq .

