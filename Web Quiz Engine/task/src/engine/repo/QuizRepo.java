package engine.repo;

import engine.enteties.Quiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepo extends PagingAndSortingRepository<Quiz, Long> {

    Quiz findQuizById(Long id);

    Page<Quiz> findAll(Pageable pageable);

    boolean existsById(Long id);

}
