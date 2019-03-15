package fr.chaffotm.geobase.web.rest;

import fr.chaffotm.geobase.service.QuizService;
import fr.chaffotm.geoquiz.resource.QuizType;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.List;

@Path("quiz-types")
public class QuizTypeEndpoint implements ResourceEndpoint {

    private final QuizService quizService;

    // Used by CDI
    public QuizTypeEndpoint() {
        this(null);
    }

    @Inject
    public QuizTypeEndpoint(final QuizService quizService) {
        this.quizService = quizService;
    }

    @GET
    public List<QuizType> get() {
        return quizService.getQuizTypes();
    }

}
