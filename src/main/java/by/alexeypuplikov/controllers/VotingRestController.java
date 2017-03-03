package by.alexeypuplikov.controllers;

import by.alexeypuplikov.exception.DuplicateTopicException;
import by.alexeypuplikov.exception.LaunchVotingException;
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
import org.springframework.web.bind.annotation.*;

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
        if (ValidateVoting.validateVoting(votingRepository, voting.getTopic())) { //change exception text
            return new ResponseEntity<>(votingRepository.save(voting), HttpStatus.CREATED);
        } else {
            throw new DuplicateTopicException(voting.getTopic());
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{topic}/addVotingOption")
    public ResponseEntity<?> addVotingOption(@PathVariable String topic, @RequestBody VotingOption votingOption) {
        Voting voting = votingRepository.findByTopic(topic);
        if (ValidateVotingOption.validateVotingOption(votingOptionRepository, votingOption.getOptionText(), voting)) {
            votingOption.setVoting(voting);
            return new ResponseEntity<>(votingOptionRepository.save(votingOption), HttpStatus.CREATED);
        }
        throw new DuplicateTopicException(votingOption.getOptionText()); //another exception
    }

    @RequestMapping(method = RequestMethod.POST, params = {"topic", "changeState"})
    public ResponseEntity<?> changeVotingState(@RequestParam(value = "topic") String topic, @RequestParam(value = "changeState") String changeState, HttpServletRequest request) {
        if (topic != null) { //check topic == null
            Voting voting = votingRepository.findByTopic(topic);
            if (changeState.equals("launch") && !voting.isLaunched()) {
                voting.setLink(String.valueOf(this.getURLValue(request)) + "/" + voting.getTopic());
                voting.setLaunched(true);
                return new ResponseEntity<>(votingRepository.save(voting), HttpStatus.OK);
            } else {
                if (changeState.equals("close") && voting.isLaunched()) {
                    voting.setLink(null);
                    voting.setLaunched(false);
                    return new ResponseEntity<>(votingRepository.save(voting), HttpStatus.OK);
                }
                throw new LaunchVotingException("voting is already have this state");
            }
        } else {
            throw new LaunchVotingException("topic can not be null");
        }
    }

    @RequestMapping(value ="/",produces = "application/json")
    public StringBuffer getURLValue(HttpServletRequest request){
        return request.getRequestURL();
    }
}
