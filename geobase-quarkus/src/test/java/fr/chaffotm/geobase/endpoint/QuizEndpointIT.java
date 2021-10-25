package fr.chaffotm.geobase.endpoint;

import fr.chaffotm.geobase.assertion.ResponseAssert;
import fr.chaffotm.geobase.web.exception.BadRequestBody;
import fr.chaffotm.quizzify.builder.QuizAnswerBuilder;
import fr.chaffotm.quizzify.resource.*;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.function.Predicate;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static javax.ws.rs.core.Response.Status.*;
import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
public class QuizEndpointIT {

    @TestHTTPResource("/geobase")
    private URI baseURL;

    private Condition<Question> withoutChoices;

    private Condition<Question> fourChoices;

    private Client client;

    @BeforeEach
    public void setUp() {
        client = TestConfiguration.buildClient();
        withoutChoices = new Condition<>(hasChoices(0), "Empty choices");
        fourChoices = new Condition<>(hasChoices(4), "4 choices");
    }

    @AfterEach
    public void tearDown() {
        client.close();
    }

    private Predicate<Question> hasChoices(final int numberOfChoices) {
        return question -> question.getChoices().size() == numberOfChoices;
    }

    @Test
    public void answerQuiz_should_check_user_answers() {
        assertThatQuizIsCreatedAndAnswerWithEmptySolution(null, fourChoices);
    }

    @Test
    public void answerQuiz_should_generate_a_quiz_without_choices() {
        final QuizConfiguration configuration = new QuizConfiguration();
        configuration.setAnswerType(AnswerType.ANSWER);
        configuration.setQuestionType("CAPITAL");

        assertThatQuizIsCreatedAndAnswerWithEmptySolution(configuration, withoutChoices);
    }

    @Test
    public void answerQuiz_should_check_user_answers_using_capitals() {
        final QuizConfiguration configuration = new QuizConfiguration();
        configuration.setAnswerType(AnswerType.MULTIPLE_CHOICE);
        configuration.setQuestionType("CAPITAL");

        assertThatQuizIsCreatedAndAnswerWithEmptySolution(configuration, fourChoices);
    }

    @Test
    public void answerQuiz_should_check_user_answers_using_flags() {
        final QuizConfiguration configuration = new QuizConfiguration();
        configuration.setAnswerType(AnswerType.MULTIPLE_CHOICE);
        configuration.setQuestionType("FLAG");

        assertThatQuizIsCreatedAndAnswerWithEmptySolution(configuration, fourChoices);
    }

    @Test
    public void answerQuiz_should_check_user_answers_using_silhouettes() {
        final QuizConfiguration configuration = new QuizConfiguration();
        configuration.setAnswerType(AnswerType.MULTIPLE_CHOICE);
        configuration.setQuestionType("SILHOUETTE");

        assertThatQuizIsCreatedAndAnswerWithEmptySolution(configuration, fourChoices);
    }

    @Test
    public void answerQuiz_should_check_user_answers_using_land_area() {
        final QuizConfiguration configuration = new QuizConfiguration();
        configuration.setAnswerType(AnswerType.MULTIPLE_CHOICE);
        configuration.setQuestionType("LAND_AREA");

        assertThatQuizIsCreatedAndAnswerWithEmptySolution(configuration, fourChoices);
    }

    @Test
    public void answerQuiz_should_check_user_answers_using_water_area() {
        final QuizConfiguration configuration = new QuizConfiguration();
        configuration.setAnswerType(AnswerType.MULTIPLE_CHOICE);
        configuration.setQuestionType("WATER_AREA");

        assertThatQuizIsCreatedAndAnswerWithEmptySolution(configuration, fourChoices);
    }

    @Test
    public void answerQuiz_should_check_user_answers_using_total_area() {
        final QuizConfiguration configuration = new QuizConfiguration();
        configuration.setAnswerType(AnswerType.MULTIPLE_CHOICE);
        configuration.setQuestionType("TOTAL_AREA");

        assertThatQuizIsCreatedAndAnswerWithEmptySolution(configuration, fourChoices);
    }

    @Test
    public void answerQuiz_should_not_generate_a_quiz_due_to_a_misconfiguration_using_water_area() {
        final QuizConfiguration configuration = new QuizConfiguration();
        configuration.setAnswerType(AnswerType.ANSWER);
        configuration.setQuestionType("WATER_AREA");
        WebTarget webTarget = client.target(baseURL).path("api/quizzes");
        final BadRequestBody errorBody = new BadRequestBody();
        errorBody.addMessage("Quiz supports only multiple choice");

        Response response = webTarget.request(APPLICATION_JSON_TYPE).post(Entity.json(configuration));

        ResponseAssert.assertThat(response)
                .hasStatus(BAD_REQUEST)
                .hasBody(errorBody);
    }

    private void assertThatQuizIsCreatedAndAnswerWithEmptySolution(final QuizConfiguration configuration, final Condition<Question> condition) {
        WebTarget webTarget = client.target(baseURL).path("api/quizzes");

        Response response = webTarget.request(APPLICATION_JSON_TYPE).post(Entity.json(configuration));

        ResponseAssert.assertThat(response)
                .hasStatus(CREATED)
                .hasNoBody();

        final String location = response.getHeaderString("location");
        final Response response1 = client.target(location).request(APPLICATION_JSON_TYPE).get();
        ResponseAssert.assertThat(response1).hasStatus(OK);
        final Quiz quiz = response1.readEntity(Quiz.class);
        assertThat(quiz.getQuestions())
                .hasSize(10)
                .are(condition);

        final QuizAnswer quizAnswer = new QuizAnswerBuilder()
                .questionAnswer("")
                .questionAnswer("")
                .questionAnswer("")
                .questionAnswer("")
                .questionAnswer("")
                .questionAnswer("")
                .questionAnswer("")
                .questionAnswer("")
                .questionAnswer("")
                .questionAnswer("")
                .getQuizAnswer();
        final QuizResult expected = new QuizResult();
        expected.setNbOfCorrectAnswers(0);
        expected.setNbOfQuestions(10);

        final Response response2 = webTarget.path(String.valueOf(quiz.getId())).request(APPLICATION_JSON_TYPE).put(Entity.json(quizAnswer));

        ResponseAssert.assertThat(response2)
                .hasStatus(OK)
                .withBody(QuizResult.class)
                .isEqualTo(expected);
    }

    @Test
    public void answerQuiz_should_not_recognize_null_body() {
        WebTarget webTarget = client.target(baseURL).path("api/quizzes");
        final BadRequestBody errorBody = new BadRequestBody();
        errorBody.addMessage("<list element> must not be null");

        final Response response = webTarget.path("45").request(APPLICATION_JSON_TYPE).put(null);

        ResponseAssert.assertThat(response)
                .hasStatus(UNSUPPORTED_MEDIA_TYPE)
                .hasNoBody();
    }

    @Test
    public void answerQuiz_should_not_validate_null_body() {
        WebTarget webTarget = client.target(baseURL).path("api/quizzes");
        final BadRequestBody errorBody = new BadRequestBody();
        errorBody.addMessage("quizAnswer must not be null");

        final Response response = webTarget.path("45").request(APPLICATION_JSON_TYPE).put(Entity.json(null));

        ResponseAssert.assertThat(response)
                .hasStatus(BAD_REQUEST)
                .hasBody(errorBody);
    }

    @Test
    public void answerQuiz_should_not_validate_empty_body() {
        WebTarget webTarget = client.target(baseURL).path("api/quizzes");
        final BadRequestBody errorBody = new BadRequestBody();
        errorBody.addMessage("quizAnswer must not be null");

        final Response response = webTarget.path("45").request(APPLICATION_JSON_TYPE).put(Entity.json(""));

        ResponseAssert.assertThat(response)
                .hasStatus(BAD_REQUEST)
                .hasBody(errorBody);
    }

    @Test
    public void answerQuiz_should_not_validate_null_list_of_question_answers() {
        final QuizAnswer quizAnswer = new QuizAnswer();
        quizAnswer.setQuestionAnswers(null);
        WebTarget webTarget = client.target(baseURL).path("api/quizzes");
        final BadRequestBody errorBody = new BadRequestBody();
        errorBody.addMessage("questionAnswers must not be null");

        final Response response = webTarget.path("45").request(APPLICATION_JSON_TYPE).put(Entity.json(quizAnswer));

        ResponseAssert.assertThat(response)
                .hasStatus(BAD_REQUEST)
                .hasBody(errorBody);
    }

    @Test
    public void answerQuiz_should_not_validate_null_element_in_list_of_question_answers() {
        final QuizAnswer quizAnswer = new QuizAnswerBuilder()
                .questionAnswer(null)
                .questionAnswer("")
                .questionAnswer(null)
                .getQuizAnswer();
        WebTarget webTarget = client.target(baseURL).path("api/quizzes");
        final BadRequestBody errorBody = new BadRequestBody();
        errorBody.addMessage("<list element> must not be null");

        final Response response = webTarget.path("45").request(APPLICATION_JSON_TYPE).put(Entity.json(quizAnswer));

        ResponseAssert.assertThat(response)
                .hasStatus(BAD_REQUEST)
                .hasBody(errorBody);
    }

    @Test
    public void answerQuiz_should_not_validate_null_element_in_list_of_answers() {
        final QuizAnswer quizAnswer = new QuizAnswerBuilder()
                .questionAnswer(null, "", null)
                .getQuizAnswer();
        WebTarget webTarget = client.target(baseURL).path("api/quizzes");
        final BadRequestBody errorBody = new BadRequestBody();
        errorBody.addMessage("<list element> must not be null");

        final Response response = webTarget.path("45").request(APPLICATION_JSON_TYPE).put(Entity.json(quizAnswer));

        ResponseAssert.assertThat(response)
                .hasStatus(BAD_REQUEST)
                .hasBody(errorBody);
    }

}
