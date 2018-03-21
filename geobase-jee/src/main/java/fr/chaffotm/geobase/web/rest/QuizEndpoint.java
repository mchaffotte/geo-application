package fr.chaffotm.geobase.web.rest;

import fr.chaffotm.geobase.service.QuizService;
import fr.chaffotm.geobase.web.domain.Quiz;
import fr.chaffotm.geobase.web.domain.QuizAnswers;
import fr.chaffotm.geobase.web.domain.QuizResult;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

@Path("quizzes")
public class QuizEndpoint implements ResourceEndpoint {

    private final QuizService quizService;

    // Used by CDI
    public QuizEndpoint() {
        this(null);
    }

    @Inject
    public QuizEndpoint(final QuizService quizService) {
        this.quizService = quizService;
    }

    @POST
    public Response create() {
        final long quizId = quizService.create();
        return Response.created(UriBuilder.fromResource(QuizEndpoint.class).path(String.valueOf(quizId)).build()).build();
    }

    @GET
    @Path("{id}")
    public Quiz get(@PathParam("id") final long id) {
        return quizService.get(id);
    }

    @PUT
    @Path("{id}")
    public QuizResult answer(@PathParam("id") final long id, @NotNull final QuizAnswers answers) {
        return quizService.answer(id, answers);
    }

}
