package by.alexeypuplikov.repositories;

import by.alexeypuplikov.models.Vote;
import org.springframework.data.repository.CrudRepository;

public interface VoteRepository extends CrudRepository<Vote, Long> {
}
