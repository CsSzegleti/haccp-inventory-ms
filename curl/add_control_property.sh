curl -v -X POST http://devtenant1:8080/api/haccp/storage/7f000101-80f0-16a0-8180-f08773eb0000/ccp/7f000101-80f0-16a0-8180-f08920010001 --header "Authorization: Bearer "$access_token --header 'Content-Type: application/json' --data-raw '{
    "value": -1
}'