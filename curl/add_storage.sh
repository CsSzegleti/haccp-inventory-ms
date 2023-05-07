curl -v -X POST http://devtenant1:8080/api/haccp/storage --header "Authorization: Bearer "$access_token --header 'Content-Type: application/json' --data-raw '{
    "name": "Template storage",
    "description": "Sample description"
}'