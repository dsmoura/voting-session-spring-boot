package com.votingpoll;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface VotingSessionRepository extends MongoRepository<VotingSession, String> {

	@Query("{name:'?0'}")
	VotingSession findByName(String name);

	@Query(value = "{category:'?0'}", fields = "{'name' : 1, 'quantity' : 1}")
	List<VotingSession> findAll(String category);

	public long count();

}