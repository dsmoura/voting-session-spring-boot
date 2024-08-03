package com.votingpoll;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
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
		mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
			.andExpect(jsonPath("$._links.votingsession").exists());
	}

	@Test
	public void shouldCreateVotingSession() throws Exception {
		mockMvc.perform(post("/votingsession").content(
				"{\"name\": \"Test Voting Session 01\", \"minutesDuration\":\"5\"}"))
				.andExpect(status().isCreated())
				.andExpect(header().string("Location", containsString("votingsession/")));
	}

	@Test
	public void shouldRetrieveVotingSession() throws Exception {
		MvcResult mvcResult = mockMvc.perform(post("/votingsession")
				.content("{\"name\": \"Test Voting Session 01\", \"minutesDuration\":\"5\"}"))
				.andExpect(status().isCreated()).andReturn();

		String location = mvcResult.getResponse().getHeader("Location");
		mockMvc.perform(get(location)).andExpect(status().isOk())
					.andExpect(jsonPath("$.name").value("Test Voting Session 01"))
					.andExpect(jsonPath("$.minutesDuration").value("5"))
					.andExpect(jsonPath("$.createdDate").exists());
	}

	@Test
	public void shouldFindVotingSession() throws Exception {
		mockMvc.perform(post("/votingsession").content(
				"{ \"name\": \"Session 07\", \"minutesDuration\":\"5\"}"))
				.andExpect(status().isCreated());

		mockMvc.perform(get("/votingsession/search/findByName?name={name}", "Session 07"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$._embedded.votingsession[0].name").value("Session 07"));
	}

	@Test
	public void shouldUpdateVotingSession() throws Exception {
		MvcResult mvcResult = mockMvc.perform(post("/votingsession").content(
				"{\"name\": \"My Session 03\", \"minutesDuration\":\"33\"}"))
				.andExpect(status().isCreated()).andReturn();

		String location = mvcResult.getResponse().getHeader("Location");

		mockMvc.perform(put(location).content(
				"{\"name\": \"My Session 03\", \"minutesDuration\":\"35\"}"))
				.andExpect(status().isNoContent());

		mockMvc.perform(get(location)).andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("My Session 03"))
				.andExpect(jsonPath("$.minutesDuration").value("35"));
	}

	@Test
	public void shouldUpdateVotingSessionYesTotal() throws Exception {
		MvcResult mvcResult = mockMvc.perform(post("/votingsession").content(
				"{\"name\": \"My Voting Session 101\"}"))
				.andExpect(status().isCreated()).andReturn();

		String location = mvcResult.getResponse().getHeader("Location");

		mockMvc.perform(patch(location).content("{\"yesVotesTotal\": \"1\"}"))
				.andExpect(status().isNoContent());

		mockMvc.perform(get(location)).andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("My Voting Session 101"))
				.andExpect(jsonPath("$.yesVotesTotal").value("1"));
	}

	@Test
	public void shouldDeleteVotingSession() throws Exception {

		MvcResult mvcResult = mockMvc.perform(post("/votingsession").content(
				"{ \"name\": \"Session 77\", \"minutesDuration\":\"7\"}"))
				.andExpect(status().isCreated()).andReturn();

		String location = mvcResult.getResponse().getHeader("Location");
		mockMvc.perform(delete(location)).andExpect(status().isNoContent());
		mockMvc.perform(get(location)).andExpect(status().isNotFound());
	}
}
