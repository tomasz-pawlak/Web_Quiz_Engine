package engine.repo;

import engine.enteties.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

//public interface UserRepo extends CrudRepository<User, Long> {
public interface UserRepo extends CrudRepository<User, Long> {

    User findUserById(Long id);

    boolean existsUserByEmail(String email);

    User findUserByEmail(String email);

}
