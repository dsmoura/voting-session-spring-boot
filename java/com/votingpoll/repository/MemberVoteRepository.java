package com.votingpoll.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import com.votingpoll.dto.MemberVote;

public interface MemberVoteRepository extends MongoRepository<MemberVote, Long> {

	List<MemberVote> findByVotingSessionIdAndMemberId
						(@Param("votingSessionId") Long votingSessionId,
								@Param("memberId") Long memberId);
	
	Long countByVotingSessionIdAndVote(@Param("votingSessionId") Long votingSessionId,
							@Param("vote") String vote);
}