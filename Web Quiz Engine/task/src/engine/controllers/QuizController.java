package engine.controllers;

import engine.QuizResult;
import engine.enteties.Answer;
import engine.enteties.Quiz;
import engine.enteties.SolvedQuiz;
import engine.repo.QuizRepo;
import engine.repo.SolvedQuizRepo;
import engine.repo.UserRepo;
import engine.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Arrays;

@RestController
public class QuizController {

    @Autowired
    QuizRepo quizRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    SolvedQuizRepo solvedQuizRepo;
    @Autowired
    QuizService quizService;


    @PostMapping("/api/quiz")
    public String postAnswer(@RequestParam int answer) {
        if (answer == 0 || answer == 1 || answer == 3) {
            return "{\"success\":false,\"feedback\":\"Wrong answer! Please, try again.\"}";
        }
        return "{\"success\":true,\"feedback\":\"Congratulations, you're right!\"}";
    }

    @GetMapping("/api/quizzes/{id}")
    public Quiz getQuizById(@PathVariable Long id, HttpServletResponse response) {

        Quiz quiz = quizRepo.findQuizById(id);

        if (!quizRepo.existsById(id)) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }

        return quiz;
    }

    @GetMapping("/api/quizzes")
    public ResponseEntity<Page<Quiz>> getQuizzes(@AuthenticationPrincipal UserDetails details
    ) {
        Page<Quiz> result = quizRepo.findAll(PageRequest.of(0, 10));

        return new ResponseEntity<>(result, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/api/quizzes/completed")
    public ResponseEntity<Page<SolvedQuiz>> getCompletedQuizzes(@AuthenticationPrincipal UserDetails details
    ) {
//        Page<SolvedQuiz> result = solvedQuizRepo.findAll(PageRequest.of(0, 10));
        Page<SolvedQuiz> result = solvedQuizRepo.findAll(PageRequest.of(
                0, 10, Sort.by(Sort.Direction.DESC, "completedAt")));

        return new ResponseEntity<>(result, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/api/quizzes")
    public Quiz createQuiz(@AuthenticationPrincipal UserDetails details, @Valid @RequestBody Quiz quiz, HttpServletResponse response) {

        if (quiz.getTitle().equals("")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else if (quiz.getText().equals("")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
//        else if (quiz.getAnswer().length < 2) {
//            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//        }

        Quiz newQuiz = new Quiz(quiz.getId(), quiz.getTitle(), quiz.getText(), quiz.getOptions(), quiz.getAnswer(), quiz.getEmail());
        System.out.println(newQuiz);

        if (newQuiz.getAnswer() == null) {
            newQuiz.setAnswer(new int[]{});
        }

        newQuiz.setEmail(details.getUsername());

        System.out.println(newQuiz);
        return quizRepo.save(newQuiz);
    }

    @GetMapping("/logout")
    public String getLogoutPage(HttpServletRequest request, HttpServletResponse response){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null){
            new SecurityContextLogoutHandler().logout(request, response, authentication);

        }

        return "redirect:/login";
    }



    @PostMapping(path = "/api/quizzes/{id}/solve")
    public QuizResult solveQuiz(@PathVariable Long id,
                                @RequestBody Answer answer, @AuthenticationPrincipal UserDetails details,
                                HttpServletRequest request, HttpServletResponse response) {

        Quiz quiz = quizRepo.findQuizById(id);

        System.out.println(details.getUsername());

        if (answer.getAnswer() == null) {
            answer.setAnswer(new int[]{});
        }

        Arrays.sort(quiz.getAnswer());
        Arrays.sort(answer.getAnswer());

        if (Arrays.equals(quiz.getAnswer(), answer.getAnswer())) {
                SolvedQuiz solvedQuiz = new SolvedQuiz(quiz.getId(), LocalDateTime.now().toString(), quiz.getEmail());
                solvedQuizRepo.save(solvedQuiz);
                return QuizResult.SUCCESSFUL;
        }

        return QuizResult.FAILED;
    }

    @DeleteMapping("/api/quizzes/{id}")
    public void deleteQuiz(@PathVariable Long id, @AuthenticationPrincipal UserDetails details, HttpServletResponse response) {
        Quiz quiz = quizRepo.findQuizById(id);

        if (!quizRepo.existsById(id)) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else if (!quiz.getEmail().equals(details.getUsername())) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else {
            quizRepo.delete(quiz);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }

    }

}
