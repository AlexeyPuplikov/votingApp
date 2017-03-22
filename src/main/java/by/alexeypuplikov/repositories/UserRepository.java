package by.alexeypuplikov.repositories;

import by.alexeypuplikov.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByLogin(String login);
}
