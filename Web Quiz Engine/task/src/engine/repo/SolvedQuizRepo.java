package engine.repo;

import engine.enteties.SolvedQuiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SolvedQuizRepo extends PagingAndSortingRepository<SolvedQuiz, Long> {

    Page<SolvedQuiz> findAll(Pageable pageable);


}
