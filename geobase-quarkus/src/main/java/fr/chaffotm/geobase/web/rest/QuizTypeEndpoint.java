package fr.chaffotm.geobase.web.rest;

import fr.chaffotm.geobase.service.QuizService;
import fr.chaffotm.quizzify.resource.QuizType;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.List;

@Path("quiz-types")
public class QuizTypeEndpoint implements ResourceEndpoint {

    private final QuizService quizService;

    public QuizTypeEndpoint(final QuizService quizService) {
        this.quizService = quizService;
    }

    @GET
    public List<QuizType> get() {
        return quizService.getQuizTypes();
    }

}
