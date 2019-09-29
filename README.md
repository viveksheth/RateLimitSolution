##RateLimitSolution

This is Springboot REST API with Rate limiting enabled. 

#####RateLimting Criteria: 
REST API Endpoint `/getEmployeeData/{ClientId}` is enabaled with rate limiting. Rate limiter keeps track of requests and limits it
such that a requester can only make 100 requests per hour. After the limit has been reached, return a 429 with the text `Too many requests. Please try again after #{n} seconds`.

###Prerequisite
- Java 1.8 or higher
- Apache Maven 3.x or above 

### Endpoints

`/generateClientId` - Uses clientIP and converts in to ClientID 
`/getEmployeeData/{ClientId}` - Checks rate limiting criteria per client and returns sample employee data 

### How to run the application? 

1. Clone repository to your local machine. 
```
https://github.com/viveksheth/RateLimitSolution.git
```
2. Open terminal and navigate to cloned library. Run following command: 
```
mvn clean install 
```
This command complies the project, download dependencies and creates a target `.jar` file to run. 

3. Run command: 
```
java -jar target/RateLimitSolution-0.0.1-SNAPSHOT.jar
```

### Sample Curl command: 

For endpoint - `/generateClientId`
```
curl -X GET http://localhost:8080/generateClientId
```

For endpoint - `/getEmployeeData/{ClientId}`
```
curl -X GET http://localhost:8080/getEmployeeData/1921681104
```

### Test Rate limiter 

`testRateLimiting.sh` - This script generates more than 100 requests for one client Id to hit rate limit criteria. 

Run command: 
```
./testRateLimiting.sh
```
You will see output like this: 
```
Request #98 Response Code: 200
Request #99 Response Code: 200
Request #100 Response Code: 200  
Request #101 Response Code: 429  <-- Request hit rate limit 
```





