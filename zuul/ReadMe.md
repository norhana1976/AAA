start: 
get a valid token: curl tcj:password@localhost:5050/oauth/token -d grant_type=password -d username=123123123  -d password=password

to test:
curl -X GET http://localhost:9999/dummy/createAccount -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjMxMjMxMjMiLCJleHAiOjE1ODIwMTE3NDgsImp0aSI6IjhiNGM0ODhkLWUwYTAtNDUxOS1iYmY5LTE1OTc1MjhhYWM4ZSIsImNsaWVudF9pZCI6InRjaiIsInNjb3BlIjpbInJlYWQiXX0.lsDUamu8Aoq61Wn1Q34SM4OftP1argmmcbv-i_F-kHw"
