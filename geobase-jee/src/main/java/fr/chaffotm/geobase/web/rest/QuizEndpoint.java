package fr.chaffotm.geobase.web.rest;

import fr.chaffotm.geobase.service.QuizService;
import fr.chaffotm.geoquiz.resource.Quiz;
import fr.chaffotm.geoquiz.resource.QuizConfiguration;
import fr.chaffotm.geoquiz.resource.QuizAnswer;
import fr.chaffotm.geoquiz.resource.QuizResult;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

@Path("quizzes")
public class QuizEndpoint implements ResourceEndpoint {

    @Context
    private UriInfo uriInfo;

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
    public Response create(@Valid QuizConfiguration configuration) {
        final long quizId = quizService.create(configuration);
        return Response.created(UriBuilder.fromResource(QuizEndpoint.class).path(String.valueOf(quizId)).build()).build();
    }

    @GET
    @Path("{id}")
    public Quiz get(@PathParam("id") final long id) {
        return quizService.get(id, uriInfo);
    }

    @PUT
    @Path("{id}")
    public QuizResult answer(@PathParam("id") final long id, @Valid @NotNull final QuizAnswer quizAnswer) {
        return quizService.answer(id, quizAnswer);
    }

}
