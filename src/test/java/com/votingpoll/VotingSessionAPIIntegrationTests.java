package com.votingpoll;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class VotingSessionAPIIntegrationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private VotingSessionRepository votingSessionRepository;

	@BeforeEach
	public void deleteAllBeforeTests() throws Exception {
		votingSessionRepository.deleteAll();
	}

	@Test
	public void shouldReturnRepositoryIndex() throws Exception {
		mockMvc.perform(get("/"))
		.andExpect(status().isOk());
	}

	@Test
	public void shouldCreateNewVotingSession() throws Exception {
		mockMvc.perform(post("/voting-sessions")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":\"1\", \"name\":\"name1\"}"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("name1"));
	}
}
