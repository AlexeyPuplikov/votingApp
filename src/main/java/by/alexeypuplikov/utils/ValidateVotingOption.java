package by.alexeypuplikov.utils;

import by.alexeypuplikov.models.Voting;
import by.alexeypuplikov.models.VotingOption;
import by.alexeypuplikov.repositories.VotingOptionRepository;

import java.util.List;

public class ValidateVotingOption {

    public static boolean validateVotingOption(VotingOptionRepository votingOptionRepository, String optionText, Voting voting) {
        List<VotingOption> votingOptions = votingOptionRepository.findByVoting(voting);
        if (optionText != null) {
            for (VotingOption votingOption : votingOptions) {
                if (optionText.equals(votingOption.getOptionText())) {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }
}
