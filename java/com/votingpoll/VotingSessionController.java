package com.votingpoll;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VotingSessionController {
	
	@Autowired
	private VotingSessionRepository votingSessionRepository;
	
	@GetMapping("/")
	String hello() {
		System.out.println("GET hello");
		return "Hellow";
	}
	
	@GetMapping("/voting-sessions")
	List<VotingSession> listAll() {
		System.out.println("GET VotingSessionController");
		return votingSessionRepository.findAll();
	}

}
