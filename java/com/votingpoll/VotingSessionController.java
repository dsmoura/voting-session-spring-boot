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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.votingpoll.dto.MemberVote;
import com.votingpoll.dto.VotingSession;
import com.votingpoll.repository.MemberVoteRepository;
import com.votingpoll.repository.VotingSessionRepository;

@RestController
public class VotingSessionController {
	
	public final static Integer DEFAULT_MINUTES_DURATION = 1;

	@Autowired
	private VotingSessionRepository votingSessionRepository;
	
	@Autowired
	private MemberVoteRepository memberVoteRepository;

	@GetMapping("/")
	String hello() {
		return "Hello. It's the Voting Session Application here.";
	}

	@PostMapping("/sessions")
	@ResponseStatus(HttpStatus.CREATED)
	VotingSession saveVotingSession(@RequestBody VotingSession votingSession) {
		Optional<VotingSession> vs = votingSessionRepository.findById(votingSession.getId());
		if (vs.isPresent()) {
			if (vs.get().getMinutes() == null) {
				if (votingSession.getMinutes() == null) {
					votingSession.setMinutes(DEFAULT_MINUTES_DURATION);
				}
				votingSession.setStartDate(new Date());
				return votingSessionRepository.save(votingSession);
			}
		}
		return votingSessionRepository.save(votingSession);
	}
	
	@PostMapping("/vote")
	ResponseEntity<MemberVote> memberVote(@RequestParam Long votingSessionId,
													@RequestParam Long memberId,
													@RequestParam String vote) {
		List<MemberVote> memberVotes = memberVoteRepository.findByVotingSessionIdAndMemberId(votingSessionId, memberId);
		if (memberVotes.size() > 0) {
			return new ResponseEntity<MemberVote>(HttpStatus.NOT_ACCEPTABLE);
		}
		if (!vote.equals("NO") && !vote.equals("YES")) {
			return new ResponseEntity<MemberVote>(HttpStatus.BAD_REQUEST);
		}
		memberVoteRepository.save(new MemberVote(votingSessionId, memberId, vote));
		
		return ResponseEntity.created(ServletUriComponentsBuilder
			    						.fromCurrentRequestUri()
			    							.path("/{id}")
			    							.buildAndExpand(votingSessionId).toUri())
			    							.build();
	}
	
	@GetMapping("/sessions/{id}")
	ResponseEntity<VotingSession> countSessionTotalVotes(@PathVariable Long id) {
		VotingSession votingSession = votingSessionRepository.findById(id).get();
		if (votingSession == null) {
			return new ResponseEntity<VotingSession>(HttpStatus.BAD_REQUEST);
		}
		Long totalYes = memberVoteRepository.countByVotingSessionIdAndVote(id, "YES");
		Long totalNo = memberVoteRepository.countByVotingSessionIdAndVote(id, "NO");
		votingSession.setYesTotalVotes(totalYes);
		votingSession.setNoTotalVotes(totalNo);
		
		return ResponseEntity.ok(votingSession);
	}

}
