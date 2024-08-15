package com.votingpoll.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.votingpoll.utils.CpfUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class UserController {
	public final static String ABLE_TO_VOTE = "ABLE_TO_VOTE";
	public final static String UNABLE_TO_VOTE = "UNABLE_TO_VOTE";
	
	@Operation(summary = "Validate if a CPF is valid.")
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "It returns status ABLE_TO_VOTE if it's even number, otherwise UNABLE_TO_VOTE."),
							@ApiResponse(responseCode = "400", description = "Invalid CPF.")})
	@GetMapping("/v1/users/{cpf}")
	ResponseEntity<String> validateUserToVote(@PathVariable String cpf) {
		if (!CpfUtils.isValidCPF(cpf)) {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		boolean isCpfEvenNumber = (int) cpf.toCharArray()[10] % 2 == 0;
		if (isCpfEvenNumber) {
			return ResponseEntity.ok("{\"status\":\"" + ABLE_TO_VOTE + "\"}");
		} else {
			return ResponseEntity.ok("{\"status\":\"" + UNABLE_TO_VOTE + "\"}");
		}
	}
}
