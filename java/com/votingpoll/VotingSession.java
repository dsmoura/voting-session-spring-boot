package com.votingpoll;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("votingsessiondoc")
public class VotingSession {

	@Id
	private Integer id;

	private String name;
	private Date openSessionDate;
	private Integer minutesDuration;
	
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
	public Date getOpenSessionDate() {
		return openSessionDate;
	}
	public void setOpenSessionDate(Date openSessionDate) {
		this.openSessionDate = openSessionDate;
	}
	public Integer getMinutesDuration() {
		return minutesDuration;
	}
	public void setMinutesDuration(Integer minutesDuration) {
		this.minutesDuration = minutesDuration;
	}

	public String toString() {
		return this.id + " " +
				this.name + " " + 
				this.openSessionDate + " " +
				this.minutesDuration;
	}
}
