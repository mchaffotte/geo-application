package fr.chaffotm.geobase.web;

import fr.chaffotm.geobase.service.QuizService;
import fr.chaffotm.geobase.web.domain.QuizType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/quiz-types")
public class QuizTypeRestController {

    private final QuizService quizService;

    public QuizTypeRestController(final QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping
    public List<QuizType> get() {
        return quizService.getQuizTypes();
    }

}
