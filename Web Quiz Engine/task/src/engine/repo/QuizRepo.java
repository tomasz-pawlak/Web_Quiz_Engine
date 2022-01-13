package engine.repo;

import engine.enteties.Quiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface QuizRepo extends PagingAndSortingRepository<Quiz, Long> {

    Quiz findQuizById(Long id);

//    List<Quiz> findAll();
    Page<Quiz> findAll(Pageable pageable);

    boolean existsById(Long id);

}
