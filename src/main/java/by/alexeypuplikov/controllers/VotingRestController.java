package by.alexeypuplikov.controllers;

import by.alexeypuplikov.models.Voting;
import by.alexeypuplikov.repositories.VoteRepository;
import by.alexeypuplikov.repositories.VotingOptionRepository;
import by.alexeypuplikov.repositories.VotingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/voting")
public class VotingRestController {
    private final VotingRepository votingRepository;
    private final VotingOptionRepository votingOptionRepository;
    private final VoteRepository voteRepository;

    @Autowired
    public VotingRestController(VotingRepository votingRepository, VotingOptionRepository votingOptionRepository, VoteRepository voteRepository) {
        this.votingRepository = votingRepository;
        this.votingOptionRepository = votingOptionRepository;
        this.voteRepository = voteRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Collection<Voting>> getAllVoting() {
        return new ResponseEntity<>((Collection<Voting>) votingRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> addVoting(@RequestBody Voting voting) {
        return new ResponseEntity<>(votingRepository.save(voting), HttpStatus.CREATED);
    }
}
