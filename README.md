# httpClientTest

특정 URL에 HTTP GET 요청을 하여 응답을 받은 내용에서 HTTP, HTTPS URL을 모두 추출하여 중복은 제거하고,
길이가 긴 상위 10개를 "길이 URL" 형식으로 내림차순으로 프린트한다.

풀이
https://mkyong.com/java/how-to-send-http-request-getpost-in-java/
apache http client 사용해서 HTTP CLIENT 구축

중복을 제거하기 위해 HashSet에 저장하였고, 
길이를 내림차순으로 하기위해 TreeMap을 사용하였다.
