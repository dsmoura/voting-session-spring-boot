Adopting One-to-Squillions Schema
Voting Session -> Many Votes
According to the reference https://www.mongodb.com/blog/post/6-rules-of-thumb-for-mongodb-schema-design

<< Running on a local computer (Intel i7, 8GiB RAM) with local MongoDB >>

Load Tests runned -> 1001 tests on ~2.5s + 1000 tests in a row on ~2s.
2024-08-08T20:27:39.351-03:00  INFO 16077 --- [           main] com.votingpoll.PerformanceTests          : Voting on the session 6000
2024-08-08T20:27:39.357-03:00  INFO 16077 --- [           main] com.votingpoll.PerformanceTests          : Simulating 1001 YES votes on the session 6000: START
2024-08-08T20:27:41.801-03:00  INFO 16077 --- [           main] com.votingpoll.PerformanceTests          : Simulating 1001 YES votes on the session 6000: FINISH
2024-08-08T20:27:41.802-03:00  INFO 16077 --- [           main] com.votingpoll.PerformanceTests          : Simulating 1000 NO votes on the session 6000: START
2024-08-08T20:27:43.687-03:00  INFO 16077 --- [           main] com.votingpoll.PerformanceTests          : Simulating 1000 NO votes on the session 6000: FINISH

Load Tests runned -> 9999 tests on ~24s + 9998 tests in a row on ~49s.
2024-08-08T20:16:35.121-03:00  INFO 15096 --- [           main] c.v.VotingSessionAPIIntegrationTests     : Simulating 9999 YES votes on the session 6000: START
2024-08-08T20:16:59.894-03:00  INFO 15096 --- [           main] c.v.VotingSessionAPIIntegrationTests     : Simulating 9999 YES votes on the session 6000: FINISH
2024-08-08T20:16:59.895-03:00  INFO 15096 --- [           main] c.v.VotingSessionAPIIntegrationTests     : Simulating 9998 NO votes on the session 6000: START
2024-08-08T20:17:48.864-03:00  INFO 15096 --- [           main] c.v.VotingSessionAPIIntegrationTests     : Simulating 9998 NO votes on the session 6000: FINISH