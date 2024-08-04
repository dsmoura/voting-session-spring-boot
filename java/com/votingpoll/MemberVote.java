package com.votingpoll;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("membervote")
public class MemberVote {

	private Integer votingSessionId;
	private Long memberId;
	private Integer vote;
	
	public MemberVote(Integer votingSessionId, Long memberId, Integer vote) {
		this.votingSessionId = votingSessionId;
		this.memberId = memberId;
		this.vote = vote;
	}
	
	public Integer getVotingSessionId() {
		return votingSessionId;
	}
	public void setVotingSessionId(Integer votingSessionId) {
		this.votingSessionId = votingSessionId;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public Integer getVote() {
		return vote;
	}
	public void setVote(Integer vote) {
		this.vote = vote;
	}

	public String toString() {
		return this.votingSessionId + " " +
				this.memberId + " " + 
				this.vote;
	}
}
