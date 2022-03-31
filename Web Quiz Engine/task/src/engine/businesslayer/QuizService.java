package engine.businesslayer;

import engine.persistance.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    @Autowired
    QuizRepository quizRepository;
    @Autowired
    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }


    public Page<Quiz> getAllQuizzes(Integer pageNo, Integer pageSize,String sortBy) {
        Pageable of = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Quiz> pagedResult = quizRepository.findAll(of);

        return pagedResult;

    }

    public Quiz findQuizById(Long id) throws ResponseStatusException {
        Optional<Quiz> byId = quizRepository.findById(id);
        if (byId.isPresent()){
            return byId.get();
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
    public Quiz saveQuiz(Quiz quizToSave) {
        return  quizRepository.save(quizToSave);
    }



    public List<Quiz> findAllQuizzes() {
        return (List<Quiz>) quizRepository.findAll();
    }

    public void deleteQuizById(Long id) {
        quizRepository.deleteById(id);
    }




}
