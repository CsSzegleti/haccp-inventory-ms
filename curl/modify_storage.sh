curl -v -X PUT http://devtenant1:8080/api/haccp/storage --header "Authorization: Bearer "$access_token --header 'Content-Type: application/json' --data-raw '{
  "id":"7f000101-8060-1463-8180-609692420000",
  "name":"Template storage",
  "description":"Sample description",
  "createdDate":"2022-04-25T11:58:41.219571Z"
}'