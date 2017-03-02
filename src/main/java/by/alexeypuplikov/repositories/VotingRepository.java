package by.alexeypuplikov.repositories;

import by.alexeypuplikov.models.Voting;
import org.springframework.data.repository.CrudRepository;

public interface VotingRepository extends CrudRepository<Voting, Long> {
}
