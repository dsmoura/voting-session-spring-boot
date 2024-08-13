package com.votingpoll.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.votingpoll.dto.VotingSession;

public interface VotingSessionRepository extends MongoRepository<VotingSession, Long> {

}