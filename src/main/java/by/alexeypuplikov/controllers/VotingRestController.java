package by.alexeypuplikov.controllers;

import by.alexeypuplikov.models.Voting;
import by.alexeypuplikov.models.VotingOption;
import by.alexeypuplikov.repositories.VoteRepository;
import by.alexeypuplikov.repositories.VotingOptionRepository;
import by.alexeypuplikov.repositories.VotingRepository;
import by.alexeypuplikov.utils.ValidateVoting;
import by.alexeypuplikov.utils.ValidateVotingOption;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
        ValidateVoting.validateAddTopic(votingRepository, voting.getTopic());
        return new ResponseEntity<>(votingRepository.save(voting), HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{topic}/addVotingOption")
    public ResponseEntity<?> addVotingOption(@PathVariable String topic, @RequestBody VotingOption votingOption) {
        Voting voting = votingRepository.findByTopic(topic);
        ValidateVotingOption.validateVotingOption(votingOptionRepository, votingOption.getOptionText(), voting);
        votingOption.setVoting(voting);
        return new ResponseEntity<>(votingOptionRepository.save(votingOption), HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.POST, params = {"topic", "changeState"})
    public ResponseEntity<?> changeVotingState(@RequestParam(value = "topic") String topic, @RequestParam(value = "changeState") String changeState, HttpServletRequest request) {
        Voting voting = votingRepository.findByTopic(topic);
        ValidateVoting.validateLaunchVoting(voting, topic, changeState);
        if (changeState.equals("launch")) {
            voting.setLink(String.valueOf(this.getURLValue(request)) + "/" + voting.getTopic());
            voting.setLaunched(true);
        }
        if (changeState.equals("close")) {
            voting.setLink(null);
            voting.setLaunched(false);
        }
        return new ResponseEntity<>(votingRepository.save(voting), HttpStatus.OK);
    }

    @RequestMapping(value = "/", produces = "application/json")
    public StringBuffer getURLValue(HttpServletRequest request) {
        return request.getRequestURL();
    }
}
