package com.votingpoll;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.votingpoll.repository.MemberVoteRepository;
import com.votingpoll.repository.VotingSessionRepository;
import com.votingpoll.utils.CpfUtils;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.concurrent.TimeUnit;

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
	@Timeout(value=10, unit=TimeUnit.SECONDS)
	public void shouldVoteAThousandTimesOnSessionInTenSeconds() throws Exception {
		String id = "6000";
		
		int totalAttemptVotes = 1000;
		
		logger.info("Voting on the session " + id);
		
		mockMvc.perform(post("/v1/sessions")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":" + id + ", \"name\":\"name6000x\"}"))
				.andExpect(status().isCreated());
		
		mockMvc.perform(post("/v1/sessions")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":" + id + ", \"minutes\":\"45\"}"))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(id))
				.andExpect(jsonPath("$.minutes").value("45"));

		logger.info("Simulating " + totalAttemptVotes + " total attempts of votes. " + id + ": START");
		
		for (int i = 0; i < totalAttemptVotes; i++) {
			try {
				mockMvc.perform(post("/v1/vote")
						.param("votingSessionId", id)
						.param("memberCpf", CpfUtils.generateRandomCPF())
						.param("voteYesOrNo", "YES"));
			} catch (Exception e) {
				totalAttemptVotes--;
			}
		}
		
		logger.info("Sucessful " + totalAttemptVotes + " total votes from random CPFs. " + id + ": FINISH");
	}
}
