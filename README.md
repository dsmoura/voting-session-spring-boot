# Voting Session Spring Boot RESTFul API Application
Be welcome :-)

### Please take a look at an example running on AWS EC2
<http://18.230.24.159:8080/swagger>
<http://ec2-18-230-24-159.sa-east-1.compute.amazonaws.com:8080/swagger>

### Swagger OpenAPI Documentation
Please check out the API Documentation on {URL}:{PORT}/swagger.

### NoSQL MongoDB
To run the application example, it's needed a Mongo DB instance running locally.

### MongoDB Schema
Adopting One-to-Squillions Schema
Voting Session -> Many Votes
According to the reference <https://www.mongodb.com/blog/post/6-rules-of-thumb-for-mongodb-schema-design>

### Test-first development
All code was developed on TDD approach with Spring MVC Test framework - MockMvc.
<https://docs.spring.io/spring-framework/reference/testing/spring-mvc-test-framework.html>

### Versioning
All APIS started already on v1 version, through URI Path.

### Performance tests
Running on a local computer (Intel i7, 8GiB RAM) with local MongoDB database.

Load Tests run -> 1001 tests on around 2.5s + 1000 tests in a row on around 2s.
2024-08-08T20:27:39.351-03:00  INFO 16077 --- [           main] com.votingpoll.PerformanceTests          : Voting on the session 6000
2024-08-08T20:27:39.357-03:00  INFO 16077 --- [           main] com.votingpoll.PerformanceTests          : Simulating 1001 YES votes on the session 6000: START
2024-08-08T20:27:41.801-03:00  INFO 16077 --- [           main] com.votingpoll.PerformanceTests          : Simulating 1001 YES votes on the session 6000: FINISH
2024-08-08T20:27:41.802-03:00  INFO 16077 --- [           main] com.votingpoll.PerformanceTests          : Simulating 1000 NO votes on the session 6000: START
2024-08-08T20:27:43.687-03:00  INFO 16077 --- [           main] com.votingpoll.PerformanceTests          : Simulating 1000 NO votes on the session 6000: FINISH

Load Tests run -> 9999 tests on around 24s + 9998 tests in a row on around 49s.
2024-08-08T20:16:35.121-03:00  INFO 15096 --- [           main] c.v.VotingSessionAPIIntegrationTests     : Simulating 9999 YES votes on the session 6000: START
2024-08-08T20:16:59.894-03:00  INFO 15096 --- [           main] c.v.VotingSessionAPIIntegrationTests     : Simulating 9999 YES votes on the session 6000: FINISH
2024-08-08T20:16:59.895-03:00  INFO 15096 --- [           main] c.v.VotingSessionAPIIntegrationTests     : Simulating 9998 NO votes on the session 6000: START
2024-08-08T20:17:48.864-03:00  INFO 15096 --- [           main] c.v.VotingSessionAPIIntegrationTests     : Simulating 9998 NO votes on the session 6000: FINISH

Random generated valid CPFs to test.
User API will deny vote on sessions for odd number CPFs.
"22026073074" ABLE_TO_VOTE (even number)
"28305251837" UNABLE_TO_VOTE (odd number)