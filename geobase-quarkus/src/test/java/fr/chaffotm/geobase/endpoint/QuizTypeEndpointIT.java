package fr.chaffotm.geobase.endpoint;

import fr.chaffotm.quizzify.resource.AnswerType;
import fr.chaffotm.quizzify.resource.QuizType;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
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

    private QuizType build(final String questionType, final AnswerType... answerTypes) {
        final QuizType type = new QuizType();
        type.setQuestionType(questionType);
        type.setAnswerTypes(List.of(answerTypes));
        return type;
    }

    @Test
    public void getQuizTypes_should_return_all_possible_types() {
        final Client client = TestConfiguration.buildClient();
        WebTarget webTarget = client.target(baseURL).path("api/quiz-types");
        Response response = webTarget.request(APPLICATION_JSON_TYPE).get();

        assertThat(response)
                .hasStatus(OK)
                .withBodyList(QuizType.class)
                .containsExactlyInAnyOrder(
                        build("CAPITAL", AnswerType.ANSWER, AnswerType.MULTIPLE_CHOICE),
                        build("TOTAL_AREA", AnswerType.MULTIPLE_CHOICE),
                        build("LAND_AREA", AnswerType.MULTIPLE_CHOICE),
                        build("WATER_AREA", AnswerType.MULTIPLE_CHOICE),
                        build("FLAG", AnswerType.ANSWER, AnswerType.MULTIPLE_CHOICE),
                        build("SILHOUETTE", AnswerType.ANSWER, AnswerType.MULTIPLE_CHOICE)
                );
    }

}
