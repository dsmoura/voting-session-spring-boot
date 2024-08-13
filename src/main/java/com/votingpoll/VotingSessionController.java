package com.votingpoll;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.votingpoll.dto.MemberVote;
import com.votingpoll.dto.VotingSession;
import com.votingpoll.repository.MemberVoteRepository;
import com.votingpoll.repository.VotingSessionRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class VotingSessionController {
	
	public final static Integer DEFAULT_MINUTES_DURATION = 1;
	private static final String NO_VOTE = "NO";
	private static final String YES_VOTE = "YES";
	
	@Autowired
	private VotingSessionRepository votingSessionRepository;
	
	@Autowired
	private MemberVoteRepository memberVoteRepository;

	@Operation(summary = "Just to say hello.")
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Application running OK, thanks!")})
	@GetMapping("/")
	String hello() {
		return "Hello. It's the Voting Session Application here.";
	}

	@Operation(summary = "Create a Voting Session informind ID and Name. Start a Voting Session informing an existing ID and duration in minutes. One minute duration default.")
	@ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Voting Session created informing ID and Name. Voting Session starts when informing Duration in Minutes for a Voting Session already created."),
							@ApiResponse(responseCode = "400", description = "Voting Session already started, sorry but we can update its information anymore.")})
	@PostMapping("/v1/sessions")
	@ResponseStatus(HttpStatus.CREATED)
	VotingSession saveVotingSession(@RequestBody VotingSession votingSession) {
		Optional<VotingSession> vs = votingSessionRepository.findById(votingSession.getId());
		
		boolean shallCreateNewVotingSession = !vs.isPresent();
		if (shallCreateNewVotingSession) {
			if (votingSession.getId() <= 0) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Voting Session ID");
			}
			if (votingSession.getName().length() < 5) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Voting Session Name must have 5 characters at least.");
			}
			return votingSessionRepository.save(new VotingSession(votingSession.getId(),
																	votingSession.getName()));	//save new voting session
		}
		
		boolean hasVotingSessionAlreadyStarted = vs.get().getMinutes() != null;
		if (hasVotingSessionAlreadyStarted) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sorry. Voting session already started previously.");
		}
		
		boolean shallSetDefaultDuration = votingSession.getMinutes() == null;
		if (shallSetDefaultDuration) {
			vs.get().setMinutes(DEFAULT_MINUTES_DURATION);
		} else {
			vs.get().setMinutes(votingSession.getMinutes());
		}
		vs.get().setStartDate(new Date());
		vs.get().setYesTotalVotes((long) 0);
		vs.get().setNoTotalVotes((long) 0);
		return votingSessionRepository.save(vs.get());	//start voting session
	}
	
	@Operation(summary = "Vote on an existing session.")
	@ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Vote with success."),
							@ApiResponse(responseCode = "404", description = "Member has already voted on this session, or is trying to vote something different than YES or NO.")})
	@PostMapping("/v1/vote")
	ResponseEntity<MemberVote> memberVote(@RequestParam Long votingSessionId,
													@RequestParam Long memberId,
													@RequestParam String voteYesOrNo) {
		Optional<VotingSession> vs = votingSessionRepository.findById(votingSessionId);
		if (vs.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sorry. Voting session doesn't exist.");
		}
		if (vs.get().getMinutes() == null || vs.get().getMinutes().equals(0)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sorry. Voting session hasn't started yet.");
		}
		
		List<MemberVote> memberVotes = memberVoteRepository.findByVotingSessionIdAndMemberId(votingSessionId, memberId);
		boolean hasMemberAlreadyVotedOnVotingSession = memberVotes.size() > 0;
		if (hasMemberAlreadyVotedOnVotingSession) {
			return new ResponseEntity<MemberVote>(HttpStatus.BAD_REQUEST);
		}
		boolean isValidVoteYesOrNo = voteYesOrNo.equals(YES_VOTE) || voteYesOrNo.equals(NO_VOTE);
		if (!isValidVoteYesOrNo) {
			return new ResponseEntity<MemberVote>(HttpStatus.BAD_REQUEST);
		}
		memberVoteRepository.save(new MemberVote(votingSessionId, memberId, voteYesOrNo));	//vote!
		
		return ResponseEntity.created(ServletUriComponentsBuilder
			    						.fromCurrentRequestUri()
			    							.path("/{id}")
			    							.buildAndExpand(votingSessionId).toUri())
			    							.build();
	}
	
	@Operation(summary = "Information data of an existing Voting Session.")
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Brings all information of a Voting Session, couting total votes."),
							@ApiResponse(responseCode = "400", description = "Sorry, Voting Session ID doesn't exist.")})
	@GetMapping("/v1/sessions/{id}")
	ResponseEntity<VotingSession> countSessionTotalVotes(@PathVariable Long id) {
		if (! votingSessionRepository.existsById(id)) {
			return new ResponseEntity<VotingSession>(HttpStatus.BAD_REQUEST);
		}
		VotingSession votingSession = votingSessionRepository.findById(id).get();
		Long totalYes = memberVoteRepository.countByVotingSessionIdAndVote(id, YES_VOTE);
		Long totalNo = memberVoteRepository.countByVotingSessionIdAndVote(id, NO_VOTE);
		votingSession.setYesTotalVotes(totalYes);
		votingSession.setNoTotalVotes(totalNo);
		
		return ResponseEntity.ok(votingSession);
	}

}
