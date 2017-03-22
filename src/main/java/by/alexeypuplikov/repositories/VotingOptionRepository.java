package by.alexeypuplikov.repositories;

import by.alexeypuplikov.models.Voting;
import by.alexeypuplikov.models.VotingOption;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VotingOptionRepository extends CrudRepository<VotingOption, Long> {
    VotingOption findByVotingAndOptionText(Voting voting, String optionText);
    List<VotingOption> findByVoting(Voting voting);
}