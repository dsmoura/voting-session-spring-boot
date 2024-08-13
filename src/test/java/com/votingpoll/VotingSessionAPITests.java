package com.votingpoll;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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

import java.time.Duration;

@SpringBootTest
@AutoConfigureMockMvc
public class VotingSessionAPITests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private VotingSessionRepository votingSessionRepository;
	
	@Autowired
	private MemberVoteRepository memberVoteRepository;
	
	Logger logger = LoggerFactory.getLogger(VotingSessionAPITests.class);

	@BeforeEach
	public void deleteAllBeforeTests() throws Exception {
		votingSessionRepository.deleteAll();
		memberVoteRepository.deleteAll();
	}

	@Test
	public void shouldReturnRepositoryIndex() throws Exception {
		mockMvc.perform(get("/"))
		.andExpect(status().isOk());
	}

	@Test
	public void shouldCreateNewVotingSession() throws Exception {
		mockMvc.perform(post("/v1/sessions")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":1, \"name\":\"name1\"}"))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name").value("name1"));
	}
	
	@Test
	public void shouldNotCreateNewVotingSessionWithZeroOrNegativeID() throws Exception {
		mockMvc.perform(post("/v1/sessions")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":0, \"name\":\"name0\"}"))
				.andExpect(status().isBadRequest());
		
		mockMvc.perform(post("/v1/sessions")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":-1, \"name\":\"name-1\"}"))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void shouldNotCreateNewVotingSessionWithShortName() throws Exception {
		mockMvc.perform(post("/v1/sessions")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":112233, \"name\":\"n1\"}"))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void shouldNotCreateNewVotingSessionWithNoName() throws Exception {
		mockMvc.perform(post("/v1/sessions")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":1"))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void shoudOpenVotingSession() throws Exception {
		mockMvc.perform(post("/v1/sessions")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":2, \"name\":\"name2\"}"))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name").value("name2"));
		
		mockMvc.perform(post("/v1/sessions")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":2, \"minutes\":\"30\"}"))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value("2"))
				.andExpect(jsonPath("$.minutes").value("30"))
				.andExpect(jsonPath("$.startDate").exists())
				.andExpect(jsonPath("$.yesTotalVotes").value("0"))
				.andExpect(jsonPath("$.yesTotalVotes").value("0"));
	}
	
	@Test
	public void shoudNotOpenVotingSessionTwice() throws Exception {
		mockMvc.perform(post("/v1/sessions")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":333, \"name\":\"name333\"}"))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name").value("name333"));
		
		mockMvc.perform(post("/v1/sessions")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":333, \"minutes\":\"303\"}"))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value("333"))
				.andExpect(jsonPath("$.minutes").value("303"));
		
		mockMvc.perform(post("/v1/sessions")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":333, \"minutes\":\"304\"}"))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void shoudOpenVotingSessionWithOneMinuteDefaultDurations() throws Exception {
		final int defaultDuration = 1;
		
		mockMvc.perform(post("/v1/sessions")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":3, \"name\":\"name3\"}"))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name").value("name3"));
		
		mockMvc.perform(post("/v1/sessions")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":3}"))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value("3"))
				.andExpect(jsonPath("$.minutes").value(defaultDuration));
	}
	
	@Test
	public void shouldVoteOnSession() throws Exception {
		mockMvc.perform(post("/v1/sessions")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":7, \"name\":\"name7\"}"))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name").value("name7"));
		
		mockMvc.perform(post("/v1/sessions")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":7, \"minutes\":\"60\"}"))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value("7"))
				.andExpect(jsonPath("$.minutes").value("60"));
		
		mockMvc.perform(post("/v1/vote")
				.param("votingSessionId", "7")
				.param("memberId", "10")
				.param("voteYesOrNo", "YES"))
				.andExpect(status().isCreated());
		
		mockMvc.perform(post("/v1/vote")
				.param("votingSessionId", "7")
				.param("memberId", "12")
				.param("voteYesOrNo", "NO"))
				.andExpect(status().isCreated());
	}
	
	@Test
	@Disabled("This test depends on time. It takes a minute at least. Remove this tag if you wish to test it completely.")
	public void shouldNotVoteOnNotStartedSession() throws Exception {
		mockMvc.perform(post("/v1/sessions")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":444, \"name\":\"name444\"}"))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name").value("name444"));
		
		mockMvc.perform(post("/v1/sessions")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":444, \"minutes\":\"1\"}"))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value("444"))
				.andExpect(jsonPath("$.minutes").value("1"));

		Thread.sleep(Duration.ofSeconds(61));
		
		mockMvc.perform(post("/v1/vote")
				.param("votingSessionId", "444")
				.param("memberId", "12")
				.param("voteYesOrNo", "NO"))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void shouldNotVoteOnClosedSession() throws Exception {
		mockMvc.perform(post("/v1/sessions")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":555, \"name\":\"name555\"}"))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name").value("name555"));
		
		mockMvc.perform(post("/v1/vote")
				.param("votingSessionId", "555")
				.param("memberId", "125")
				.param("voteYesOrNo", "YES"))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void shouldNotVoteTwiceOnSameSession() throws Exception {
		mockMvc.perform(post("/v1/sessions")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":8, \"name\":\"name8\"}"))
				.andExpect(status().isCreated());
		
		mockMvc.perform(post("/v1/sessions")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":8, \"minutes\":\"45\"}"))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value("8"))
				.andExpect(jsonPath("$.minutes").value("45"));
		
		mockMvc.perform(post("/v1/vote")
				.param("votingSessionId", "8")
				.param("memberId", "20")
				.param("voteYesOrNo", "YES"))
				.andExpect(status().isCreated());
		
		mockMvc.perform(post("/v1/vote")
				.param("votingSessionId", "8")
				.param("memberId", "20")
				.param("voteYesOrNo", "NO"))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void shouldCountTotalVotesOnSession() throws Exception {
		String id = "55";
		mockMvc.perform(post("/v1/sessions")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":" + id + ", \"name\":\"name55\"}"))
				.andExpect(status().isCreated());
		
		mockMvc.perform(post("/v1/sessions")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":" + id + ", \"minutes\":\"30\"}"))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(id))
				.andExpect(jsonPath("$.minutes").value("30"));
		
		mockMvc.perform(post("/v1/vote")
				.param("votingSessionId", id)
				.param("memberId", "20")
				.param("voteYesOrNo", "YES"))
				.andExpect(status().isCreated());
		
		mockMvc.perform(post("/v1/vote")
				.param("votingSessionId", id)
				.param("memberId", "21")
				.param("voteYesOrNo", "YES"))
				.andExpect(status().isCreated());
		
		mockMvc.perform(post("/v1/vote")
				.param("votingSessionId", id)
				.param("memberId", "22")
				.param("voteYesOrNo", "NO"))
				.andExpect(status().isCreated());
		
		mockMvc.perform(get("/v1/sessions/" + id))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.yesTotalVotes").value("2"))
				.andExpect(jsonPath("$.noTotalVotes").value("1"));
	}
	
	@Test
	public void shouldReturnAbleToVoteWithValidCPF() throws Exception {
		String cpf = "22026073074";
		mockMvc.perform(get("/v1/users/" + cpf))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value("ABLE_TO_VOTE"));
	}
	
	@Test
	public void shouldReturnUnableToVoteWithOddNumberValidCPF() throws Exception {
		String cpf = "28305251837";
		mockMvc.perform(get("/v1/users/" + cpf))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value("UNABLE_TO_VOTE"));
	}
	
	@Test
	public void shouldReturnUnableToVoteWithInvalidCPF() throws Exception {
		String cpf = "49062342088";
		mockMvc.perform(get("/v1/users/" + cpf))
				.andExpect(status().isNotFound());
	}
}