package by.alexeypuplikov.utils;

import by.alexeypuplikov.exception.VotingException;
import by.alexeypuplikov.models.Voting;
import by.alexeypuplikov.models.VotingOption;
import by.alexeypuplikov.repositories.VotingOptionRepository;

public class ValidateVotingOption {
    public static void validateVotingOption(VotingOptionRepository votingOptionRepository, String optionText, Voting voting) {
        if (optionText.isEmpty()) {
            throw new VotingException("Option text is empty.");
        }
        VotingOption votingOption = votingOptionRepository.findByVotingAndOptionText(voting, optionText);
        if (votingOption != null) {
            throw new VotingException(String.format("Option \"%s\" is already exist!", optionText));
        }
    }
}
