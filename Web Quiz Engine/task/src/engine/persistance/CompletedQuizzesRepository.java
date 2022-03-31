package engine.persistance;

import engine.businesslayer.CompletedQuizzes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CompletedQuizzesRepository extends PagingAndSortingRepository<CompletedQuizzes,Long> {

    @Query(value = "SELECT * FROM COMPLETEDQUIZESS WHERE USER_ID = ?", nativeQuery = true)
    public Page<CompletedQuizzes> getCompletedQuizzesOfThisUser(String username, Pageable pageable);
}
