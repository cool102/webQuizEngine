package engine.presentation;

import engine.businesslayer.*;
import engine.persistance.CompletedQuizzesRepository;
import engine.persistance.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static engine.businesslayer.Answer.CORRECT_ANSWER;
import static engine.businesslayer.Answer.WRONG_ANSWER;

@Validated
@RestController
public class QuizController {

    @Autowired
    QuizService quizService;

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    CompletedQuizzesService completedQuizzesService;

    @Autowired
    CompletedQuizzesRepository completedQuizzesRepository;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;


    @GetMapping("/api/quizzes/completed")
    public Page<CompletedQuizzes> getAllCompletedQuizzesFromTable(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int pageSize,
                                                         @RequestParam(defaultValue = "completed_At") String sortBy,
                                                                  Authentication authentication) {
             return completedQuizzesService.getAllCompletedQuizzes(page,pageSize, sortBy, authentication);
    }

    @PostMapping("/api/quizzes/{id}/solve")
    public ResponseEntity<Answer> sendAnswer(@Valid @PathVariable("id") long id, @RequestBody Solution answer, Authentication auth) {


        Quiz quiz = quizService.findQuizById(id);
        if (quiz == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Integer> rightAnswers = quiz.getAnswer();
        List<Integer> inputAnswers = answer.getAnswer();
        Collections.sort(rightAnswers);
        Collections.sort(inputAnswers);
        Set<Integer> rightAnswersSet = new HashSet<>(rightAnswers);
        Set<Integer> inputAnswersSet = new HashSet<>(inputAnswers);
        System.out.println(rightAnswersSet);
        System.out.println(inputAnswersSet);


        if (rightAnswersSet.size() != inputAnswersSet.size()) {


            return new ResponseEntity<Answer>(WRONG_ANSWER,
                    HttpStatus.OK);
        } else if (rightAnswersSet.size() == inputAnswersSet.size() &&
                !rightAnswersSet.addAll(inputAnswersSet)) {
            // сохранить в таблицу CompletedQuizzes user_id, test_id и time
            CompletedQuizzes completedQuizzes = new CompletedQuizzes();
            completedQuizzes.setUserId(auth.getName());
            completedQuizzes.setId(quiz.getId());
            completedQuizzes.setCompletedAt(LocalDateTime.now());
            completedQuizzesRepository.save(completedQuizzes);
            return new ResponseEntity<Answer>(CORRECT_ANSWER,
                    HttpStatus.OK);
        } else {


            return new ResponseEntity<Answer>(WRONG_ANSWER,
                    HttpStatus.OK);
        }
    }

    @DeleteMapping("/api/quizzes/{id}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable String id, Authentication auth) {
        String currentUser = auth.getName();
        System.out.println(currentUser);

        String quizOwner = quizService.findQuizById(Long.valueOf(id)).getQuizOwner();
        if (currentUser.equals(quizOwner)) {
            quizService.deleteQuizById(Long.valueOf(id));
            return ResponseEntity.status(204).build();
        } else {
            return ResponseEntity.status(403).build();
        }

    }

    @PostMapping("/api/register")
    public ResponseEntity<Void> registerNewUser(@Valid @RequestBody User user) {
        if (userService.getUserByEmail(user.getEmail()).isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.saveUserToDb(user);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/api/quizzes")
    public Page<Quiz> getAllQuizzes(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int pageSize,
                                    @RequestParam(defaultValue = "id") String sortBy) {

        Page<Quiz> allQuizzes = quizService.getAllQuizzes(page, pageSize, sortBy);


        return allQuizzes;

    }
    // List<Quiz> content = allQuizzes.getContent();

    // List<QuizDTO> allQuizzesWithOutAnswer = content
    //         .stream()
    //         .map(quiz -> {
    //             return new QuizDTO(quiz.getId(), quiz.getTitle(), quiz.getText(), quiz.getOptions());
    //         })
    //         .collect(Collectors.toList());


    @GetMapping("/api/quizzes/{id}")
    public ResponseEntity<QuizDTO> getQuizByID(@PathVariable long id) {

        Quiz quiz = quizService.findQuizById(id);
        if (quiz == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<QuizDTO>(new QuizDTO(quiz.getId(), quiz.getTitle(), quiz.getText(), quiz.getOptions()), HttpStatus.OK);
    }

    @PostMapping("/api/quizzes")
    public ResponseEntity<QuizDTO> sendNewQuiz(@RequestBody @Valid Quiz quiz, Authentication auth) {
        String currentAuthenticatedUserEmail = auth.getName();
        quiz.setQuizOwner(currentAuthenticatedUserEmail);
        Quiz savedQuiz = quizService.saveQuiz(quiz);

        return new ResponseEntity<>(new QuizDTO(savedQuiz.getId(),
                quiz.getTitle(),
                quiz.getText(),
                quiz.getOptions()), HttpStatus.OK);


    }


}
