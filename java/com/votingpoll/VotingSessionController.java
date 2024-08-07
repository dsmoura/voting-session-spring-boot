package com.votingpoll;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VotingSessionController {

	@Autowired
	private VotingSessionRepository votingSessionRepository;

	@GetMapping("/")
	String hello() {
		return "Hellow";
	}

	@GetMapping("/voting-sessions")
	Optional<VotingSession> findVotingSession(@RequestParam Integer id) {
		return votingSessionRepository.findById(id);
	}

	@PostMapping("/voting-sessions")
	VotingSession newVotingSession(@RequestBody VotingSession votingSession) {
		return votingSessionRepository.save(votingSession);
	}

}
