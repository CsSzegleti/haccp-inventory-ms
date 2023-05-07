export access_token=$(
    curl --insecure -X POST https://localhost:8543/realms/haccp/protocol/openid-connect/token \
    --user backend-service:secret \
    -H 'content-type: application/x-www-form-urlencoded' \
    -d 'username=user&password=user&grant_type=password' | jq --raw-output '.access_token' \
 )
