curl -v -X POST http://devtenant1:8080/api/haccp/storage/7f000101-80b1-13a5-8180-b182bf950000/add_item --header "Authorization: Bearer "$access_token --header 'Content-Type: application/json' --data-raw '{
  "menuItemId": 6,
  "quantity": 5
}'