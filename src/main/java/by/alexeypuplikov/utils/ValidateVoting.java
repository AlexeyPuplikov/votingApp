package by.alexeypuplikov.utils;

import by.alexeypuplikov.exception.VotingException;
import by.alexeypuplikov.models.Voting;
import by.alexeypuplikov.repositories.VotingRepository;

public class ValidateVoting {
    private final static String LAUNCH_STATE = "launch";
    private final static String CLOSE_STATE = "close";

    public static void validateAddTopic(VotingRepository votingRepository, String topic) {
        if (topic.isEmpty()) {
            throw new VotingException("Topic is empty.");
        }
        Voting voting = votingRepository.findByTopic(topic);
        if (voting != null) {
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
        if ((!voting.isLaunched() && changeState.equals(LAUNCH_STATE)) || (voting.isLaunched() && changeState.equals(CLOSE_STATE))) {
            throw new VotingException("Voting is already have this state");
        }
    }
}
