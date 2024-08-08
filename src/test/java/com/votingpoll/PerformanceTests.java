package com.votingpoll;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.votingpoll.repository.MemberVoteRepository;
import com.votingpoll.repository.VotingSessionRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PerformanceTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private VotingSessionRepository votingSessionRepository;
	
	@Autowired
	private MemberVoteRepository memberVoteRepository;
	
	Logger logger = LoggerFactory.getLogger(PerformanceTests.class);

	@BeforeEach
	public void deleteAllBeforeTests() throws Exception {
		votingSessionRepository.deleteAll();
		memberVoteRepository.deleteAll();
	}

	@Test
	public void shouldVoteManyTimesOnSession() throws Exception {
		String id = "6000";
		
		int loadTimesYes = 1001;
		int loadTimesNo = 1000;
		
		logger.info("Voting on the session " + id);
		
		mockMvc.perform(post("/sessions")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":" + id + ", \"name\":\"name6000\"}"))
				.andExpect(status().isOk());
		

		logger.info("Simulating " + loadTimesYes + " YES votes on the session " + id + ": START");
		
		for (int i = 0; i < loadTimesYes; i++) {
			mockMvc.perform(post("/vote")
					.param("votingSessionId", id)
					.param("memberId", "2000000"+i)
					.param("vote", "YES"))
					.andExpect(status().isCreated());
		}
		
		logger.info("Simulating " + loadTimesYes + " YES votes on the session " + id + ": FINISH");
		logger.info("Simulating " + loadTimesNo + " NO votes on the session " + id + ": START");
		
		for (int i = 0; i < loadTimesNo; i++) {
			mockMvc.perform(post("/vote")
					.param("votingSessionId", id)
					.param("memberId", "10000000"+i)
					.param("vote", "NO"))
					.andExpect(status().isCreated());
		}
		
		logger.info("Simulating " + loadTimesNo + " NO votes on the session " + id + ": FINISH");
		
		mockMvc.perform(get("/sessions")
				.param("id", id))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.yesTotalVotes").value(loadTimesYes))
				.andExpect(jsonPath("$.noTotalVotes").value(loadTimesNo));
	}
}
