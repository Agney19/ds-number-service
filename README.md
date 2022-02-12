# HSE. Distributed Systems. Achievement #2

### What is it
The Java Spring Web service which takes one number N and returns N+1 if:
1. N is received for the first time;
2. There wasn't N+1 received earlier.

If any of the aforementioned conditions is not satisfied, an exception with a descriptive message will be thrown.

### How to build it
Navigate to the project root and execute (Gradle 11 is required):
- gradle build

### How to run it
To run jar directly:

System properties to be set on start up
- spring.datasource.url - url to PostgreSQL DB;
- spring.datasource.username - DB user name;
- spring.datasource.password - DB user password;
- num-service.max-value - max value that can be passed to the service. 
  This value must be >=0 and < Integer.MAX_VALUE.

Example command:
- java -Dspring.datasource.url=jdbc:postgresql://localhost:5432/postgres -Dspring.datasource.username=test -Dspring.datasource.password=test -Dnum-service.max-value=100 -jar ./ds-number-service.jar

When running as a docker image, the following environment variables will be passed as aforementioned properties: DB_URL, DB_USER, DB_PASSWORD, SERVICE_MAX_NUMBER.

### How to send a request
- POST http://localhost:8080/num-service/job
- Request Body: { value : NUMBER }; 
  Must be 0 <= value <= num-service.max-value
- Response Body: { value : NUMBER }



![ds-num-service-diagrams drawio](https://user-images.githubusercontent.com/44546177/153476372-d819ef85-b7f1-4d2e-b84e-792acba8df8a.png)
