package fr.chaffotm.geobase.endpoint;

import fr.chaffotm.quizzify.resource.AnswerType;
import fr.chaffotm.quizzify.resource.FilterType;
import fr.chaffotm.quizzify.resource.QuizType;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

import static fr.chaffotm.geobase.assertion.ResponseAssert.assertThat;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static javax.ws.rs.core.Response.Status.OK;

@QuarkusTest
public class QuizTypeEndpointIT {

    @TestHTTPResource("/geobase")
    private URI baseURL;

    private Client client;

    @BeforeEach
    public void setUp() {
        client = TestConfiguration.buildClient();
    }

    @AfterEach
    public void tearDown() {
        client.close();
    }

    private QuizType build(final String questionType, final FilterType filter, final AnswerType... answerTypes) {
        final QuizType type = new QuizType();
        type.setQuestionType(questionType);
        type.setAnswerTypes(List.of(answerTypes));
        type.setFilter(filter);
        return type;
    }

    @Test
    public void getQuizTypes_should_return_all_possible_types() {
        final WebTarget webTarget = client.target(baseURL).path("api/quiz-types");
        final FilterType filter = new FilterType();
        filter.setLabel("Region");
        filter.setName("region.id");
        filter.setValues(List.of());

        final Response response = webTarget.request(APPLICATION_JSON_TYPE).get();

        assertThat(response)
                .hasStatus(OK)
                .withBodyList(QuizType.class)
                .containsExactlyInAnyOrder(
                        build("CAPITAL", filter, AnswerType.ANSWER, AnswerType.MULTIPLE_CHOICE),
                        build("TOTAL_AREA", filter, AnswerType.MULTIPLE_CHOICE),
                        build("LAND_AREA", filter, AnswerType.MULTIPLE_CHOICE),
                        build("WATER_AREA", filter, AnswerType.MULTIPLE_CHOICE),
                        build("FLAG", filter, AnswerType.ANSWER, AnswerType.MULTIPLE_CHOICE),
                        build("SILHOUETTE", filter, AnswerType.ANSWER, AnswerType.MULTIPLE_CHOICE)
                );
    }

}
