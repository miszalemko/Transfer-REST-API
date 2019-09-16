# Transfer-REST-API
Transfer REST API for transfers without using Spring and any heavy frameworks.  
DAO - in-memory-run.  
Testing - Spock.  
JSON parsing - Jackson.  
Also were used lombok and vavr for making life easier:)  
  
Was implemented one endpoint "/transfers" with two methods POST and GET (with path variable for getting single transfer)  
Example of endpoints:  
curl -X POST localhost:8000/transfers -d '{"sourceAccountId" : 1 , "targetAccountId" : 2 , "amount" : "100.00" , "currency" : "EUR" , "reference" : "test"}'  
curl -X GET localhost:8000/transfers/1  

If you are getting next error:  
"400\nUnexpected character (''' (code 39)): expected a valid value (number, String, array, object, 'true', 'false' or 'null')\n at [Source: (sun.net.httpserver.FixedLengthInputStream); line: 1, column: 2]"  
please use this endpoint(possibly it happens because of windows quotes reading problem):  
curl -X POST localhost:8000/transfers -d "{"""sourceAccountId""" : """1""" , """targetAccountId""" : """2""" , """amount""" : """100.00""" , """currency""" : """EUR""" , """reference""" : """test"""}"  
  
Use Runner for starting server.  
addTestData() method used for initialization of data sample only for tests purposes.  
