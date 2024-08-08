package com.votingpoll;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.votingpoll.dto.MemberVote;
import com.votingpoll.dto.VotingSession;
import com.votingpoll.repository.MemberVoteRepository;
import com.votingpoll.repository.VotingSessionRepository;

@RestController
public class ServicesController {
	
	public final static Integer DEFAULT_MINUTES_DURATION = 1;

	@Autowired
	private VotingSessionRepository votingSessionRepository;
	
	@Autowired
	private MemberVoteRepository memberVoteRepository;

	@GetMapping("/")
	String hello() {
		return "Hellow";
	}

	@PostMapping("/sessions")
	VotingSession newVotingSession(@RequestBody VotingSession votingSession) {
		return votingSessionRepository.save(votingSession);
	}

	@PutMapping("/sessions")
	VotingSession startVotingSession(@RequestParam Long id,
										@RequestParam(required = false) Integer minutes) {
		return votingSessionRepository.findById(id).map(votingSession -> {
			votingSession.setMinutes(minutes==null ? DEFAULT_MINUTES_DURATION : minutes);
			votingSession.setStartDate(new Date());
			return votingSessionRepository.save(votingSession);
		}).orElseGet(() -> {
			return null;
		});
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
		
		return new ResponseEntity<MemberVote>(HttpStatus.CREATED);
	}
	
	@GetMapping("/sessions")
	ResponseEntity<VotingSession> countSessionTotalVotes(@RequestParam Long id) {
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
