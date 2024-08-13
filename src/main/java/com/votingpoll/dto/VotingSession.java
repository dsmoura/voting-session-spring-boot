package com.votingpoll.dto;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("votingsessiondoc")
public class VotingSession {

	@Id
	private Long id;

	private String name;
	private Date startDate;
	private Integer minutes;
	
	@Transient
	private Long yesTotalVotes;
	@Transient
	private Long noTotalVotes;
	
	public VotingSession(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Integer getMinutes() {
		return minutes;
	}
	public void setMinutes(Integer minutes) {
		this.minutes = minutes;
	}
	
	public Long getYesTotalVotes() {
		return yesTotalVotes;
	}
	public void setYesTotalVotes(Long yesTotalVotes) {
		this.yesTotalVotes = yesTotalVotes;
	}
	public Long getNoTotalVotes() {
		return noTotalVotes;
	}
	public void setNoTotalVotes(Long noTotalVotes) {
		this.noTotalVotes = noTotalVotes;
	}

	public String toString() {
		return this.id + " " +
				this.name + " " + 
				this.startDate + " " +
				this.minutes;
	}
}
