package com.votingpoll;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "votingsession", path = "votingsession")
public interface VotingSessionRepository extends PagingAndSortingRepository<VotingSession, Long>, CrudRepository<VotingSession,Long> {

	List<VotingSession> findByName(@Param("name") String name);

}
