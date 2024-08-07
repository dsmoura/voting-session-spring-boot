package com.votingpoll;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("votingsessiondoc")
public class VotingSession {

	@Id
	private Integer id;

	private String name;
	private Date startDate;
	private Integer minutes;
	
	public VotingSession(Integer id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
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
	public String toString() {
		return this.id + " " +
				this.name + " " + 
				this.startDate + " " +
				this.minutes;
	}
}
