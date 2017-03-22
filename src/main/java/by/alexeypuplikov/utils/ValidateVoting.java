package by.alexeypuplikov.utils;

import by.alexeypuplikov.exception.VotingException;
import by.alexeypuplikov.models.Voting;
import by.alexeypuplikov.models.VotingOption;
import by.alexeypuplikov.repositories.UserRepository;
import by.alexeypuplikov.repositories.VoteRepository;
import by.alexeypuplikov.repositories.VotingOptionRepository;
import by.alexeypuplikov.repositories.VotingRepository;

public class ValidateVoting {
    private final static String LAUNCH_STATE = "launch";
    private final static String CLOSE_STATE = "close";

    public static void validateAddTopic(VotingRepository votingRepository, String topic) {
        if (topic.isEmpty()) {
            throw new VotingException("Topic is empty.");
        }
        if (votingRepository.findByTopic(topic) != null) {
            throw new VotingException(String.format("Topic \"%s\" is already exist!", topic));
        }
    }

    public static void validateLaunchVoting(Voting voting, String topic, String changeState) {
        if (topic.isEmpty()) {
            throw new VotingException("Topic is empty.");
        }
        if (!changeState.equals(LAUNCH_STATE) && !changeState.equals(CLOSE_STATE)) {
            throw new VotingException(String.format("Parameter \"%s\" is incorrect!", changeState));
        }
        if ((voting.isLaunched() && changeState.equals(LAUNCH_STATE)) || (!voting.isLaunched() && changeState.equals(CLOSE_STATE))) {
            throw new VotingException("Voting is already have this state.");
        }
    }

    public static void validateVotingOption(VotingOptionRepository votingOptionRepository, String optionText, Voting voting) {
        if (optionText.isEmpty()) {
            throw new VotingException("Option text is empty.");
        }
        VotingOption votingOption = votingOptionRepository.findByVotingAndOptionText(voting, optionText);
        if (votingOption != null) {
            throw new VotingException(String.format("Option \"%s\" is already exist!", optionText));
        }
    }

    public static void validateRegistrationVote(VotingOptionRepository votingOptionRepository, String optionText, Voting voting, String login, UserRepository userRepository, VoteRepository voteRepository) {
        if (!voting.isLaunched()) {
            throw new VotingException("This voting is unavailable.");
        }
        if (optionText.isEmpty()) {
            throw new VotingException("Make a choice!");
        }
        if (votingOptionRepository.findByVotingAndOptionText(voting, optionText) == null) {
            throw new VotingException("This option is unavailable.");
        }
        if (voteRepository.findByVotingAndUser(voting, userRepository.findByLogin(login)) != null) {
            throw new VotingException("You have already made your choice.");
        }
        if (login.isEmpty() || userRepository.findByLogin(login) == null) {
            throw new VotingException("Login to vote!");
        }
    }
}
