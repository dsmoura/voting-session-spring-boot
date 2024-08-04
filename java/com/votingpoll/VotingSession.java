package com.votingpoll;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("votingsession")
public class VotingSession {

	@Id
	private Integer id;

	private String topicName;
	private Date openSessionDate;
	private Integer minutesDuration;
	
	public VotingSession(Integer id, String topicName) {
		this.id = id;
		this.topicName = topicName;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTopicName() {
		return topicName;
	}
	public void setTopicName(String topicName) {
		this.topicName = topicName;
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
				this.topicName + " " + 
				this.openSessionDate + " " +
				this.minutesDuration;
	}
}
