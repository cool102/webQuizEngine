package engine.businesslayer;

import engine.persistance.CompletedQuizzesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class CompletedQuizzesService {
    @Autowired
    CompletedQuizzesRepository completedQuizzesRepository;


    public Page<CompletedQuizzes> getAllCompletedQuizzes(Integer pageNo, Integer pageSize, String sortBy, Authentication auth) {
        String username = auth.getName();
        Pageable of = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        Page<CompletedQuizzes> pagedResult = completedQuizzesRepository.getCompletedQuizzesOfThisUser(username,of);

        return pagedResult;

    }

    public CompletedQuizzes saveCompletedQuizzes(CompletedQuizzes completedQuizzToSave) {
        return completedQuizzesRepository.save(completedQuizzToSave);
    }
}
