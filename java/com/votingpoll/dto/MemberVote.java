package com.votingpoll.dto;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("membervotedoc")
public class MemberVote {

	private Long votingSessionId;
	private Long memberId;
	private String vote;
	
	public MemberVote(Long votingSessionId, Long memberId, String vote) {
		this.votingSessionId = votingSessionId;
		this.memberId = memberId;
		this.vote = vote;
	}
	
	public Long getVotingSessionId() {
		return votingSessionId;
	}
	public void setVotingSessionId(Long votingSessionId) {
		this.votingSessionId = votingSessionId;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public String getVote() {
		return vote;
	}
	public void setVote(String vote) {
		this.vote = vote;
	}

	public String toString() {
		return this.votingSessionId + " " +
				this.memberId + " " + 
				this.vote;
	}
}
