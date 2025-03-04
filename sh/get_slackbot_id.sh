#!/bin/bash

# 사용자 입력을 통해 Slack API 토큰을 받음 (터미널에 노출되지 않도록 처리)
read -sp "Enter your Slack Bot Token: " SLACK_TOKEN
echo ""

# Slack API 호출 (Bot ID 조회) + JSON 포맷팅
BOT_ID=$(curl -s -X POST "https://slack.com/api/auth.test" \
     -H "Authorization: Bearer $SLACK_TOKEN" \
     -H "Content-Type: application/json" | jq -r '.user_id')

# 결과 출력
if [[ "$BOT_ID" == "null" || -z "$BOT_ID" ]]; then
    echo "❌ Failed to retrieve Bot ID. Please check your token."
else
    echo "✅ Slack Bot ID: $BOT_ID"
fi

