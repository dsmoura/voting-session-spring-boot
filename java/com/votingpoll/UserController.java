package com.votingpoll;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.votingpoll.utils.CpfValidator;

@RestController
public class UserController {
	
	public final static Integer DEFAULT_MINUTES_DURATION = 1;
	public final static String ABLE_TO_VOTE = "ABLE_TO_VOTE";
	public final static String UNABLE_TO_VOTE = "UNABLE_TO_VOTE";

	@GetMapping("/users/{cpf}")
	ResponseEntity<String> validateUserToVote(@PathVariable String cpf) {
		if (!CpfValidator.isValidCPF(cpf)) {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		
		if ((int) cpf.toCharArray()[10] % 2 == 0) {
			return ResponseEntity.ok("{\"status\":\"" + ABLE_TO_VOTE + "\"}");
		} else {
			return ResponseEntity.ok("{\"status\":\"" + UNABLE_TO_VOTE + "\"}");
		}
		
	}
}
