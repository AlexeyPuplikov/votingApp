package by.alexeypuplikov.utils;

import by.alexeypuplikov.models.Voting;
import by.alexeypuplikov.repositories.VotingRepository;

import java.util.List;

public class ValidateVoting {

    public static boolean validateVoting(VotingRepository votingRepository, String topic) {
        List<Voting> votingList = (List<Voting>) votingRepository.findAll();
        if (topic != null) {
            for (Voting voting : votingList) {
                if (topic.equals(voting.getTopic())) {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }
}
