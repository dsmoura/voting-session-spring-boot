package com.votingpoll;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class VotingSession {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String name;
	
	private Date createdDate = new Date();
	
	private Integer minutesDuration;
	
	private Integer yesVotesTotal = 0;
	
	private Integer noVotesTotal = 0;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Integer getMinutesDuration() {
		return minutesDuration;
	}
	public void setMinutesDuration(Integer minutesDuration) {
		this.minutesDuration = minutesDuration;
	}
	public Integer getYesVotesTotal() {
		return yesVotesTotal;
	}
	public void setYesVotesTotal(Integer yesVotesTotal) {
		this.yesVotesTotal = yesVotesTotal;
	}
	public Integer getNoVotesTotal() {
		return noVotesTotal;
	}
	public void setNoVotesTotal(Integer noVotesTotal) {
		this.noVotesTotal = noVotesTotal;
	}
}
