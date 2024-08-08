package com.votingpoll;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface VotingSessionRepository extends MongoRepository<VotingSession, Long> {

}