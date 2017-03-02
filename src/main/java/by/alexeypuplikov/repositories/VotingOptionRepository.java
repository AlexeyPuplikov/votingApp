package by.alexeypuplikov.repositories;

import by.alexeypuplikov.models.VotingOption;
import org.springframework.data.repository.CrudRepository;

public interface VotingOptionRepository extends CrudRepository<VotingOption, Long> {
}
