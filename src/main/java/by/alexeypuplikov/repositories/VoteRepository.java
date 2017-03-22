package by.alexeypuplikov.repositories;

import by.alexeypuplikov.models.User;
import by.alexeypuplikov.models.Vote;
import by.alexeypuplikov.models.Voting;
import by.alexeypuplikov.models.VotingOption;
import org.springframework.data.repository.CrudRepository;

public interface VoteRepository extends CrudRepository<Vote, Long> {
    Vote findByVotingAndUser(Voting voting, User user);
    Long countByVotingAndVotingOption(Voting voting, VotingOption votingOption);
}
