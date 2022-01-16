package engine.repo;

import engine.enteties.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {

    boolean existsUserByEmail(String email);

    User findUserByEmail(String email);
}
