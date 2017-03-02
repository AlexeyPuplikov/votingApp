package by.alexeypuplikov.utils;

import by.alexeypuplikov.exception.DuplicateTopicException;
import by.alexeypuplikov.models.Voting;
import by.alexeypuplikov.repositories.VotingRepository;

import java.util.List;

public class ValidateVoting {

    public static boolean validateVoting(VotingRepository votingRepository, String topic) {
        List<Voting> votingSet = (List<Voting>) votingRepository.findAll();
        for (Voting voting : votingSet) {
            if (topic.equals(voting.getTopic())) {
                return false;
            }
        }
        return true;
    }
}
