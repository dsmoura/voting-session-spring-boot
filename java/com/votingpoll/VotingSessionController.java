package com.votingpoll;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VotingSessionController {
	
	public final static Integer DEFAULT_MINUTES_DURATION = 1;

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

	@PutMapping("/voting-sessions")
	VotingSession startVotingSession(@RequestParam Integer id,
										@RequestParam(required = false) Integer minutes) {
		return votingSessionRepository.findById(id).map(votingSession -> {
			votingSession.setMinutes(minutes==null ? DEFAULT_MINUTES_DURATION : minutes);
			votingSession.setStartDate(new Date());
			return votingSessionRepository.save(votingSession);
		}).orElseGet(() -> {
			return null;
		});
	}

}
