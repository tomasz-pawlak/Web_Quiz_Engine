package engine.repo;

import engine.enteties.SolvedQuiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolvedQuizRepo extends PagingAndSortingRepository<SolvedQuiz, Long> {

    Page<SolvedQuiz> findAllByCompletedBy(Pageable pageable, String user);

}
