package fr.chaffotm.geobase.restcontroller;

import fr.chaffotm.geobase.assertion.ResponseEntityAssert;
import fr.chaffotm.geobase.interceptor.JsonInterceptor;
import fr.chaffotm.geobase.interceptor.LoggingInterceptor;
import fr.chaffotm.geobase.web.exception.BadRequestBody;
import fr.chaffotm.geoquiz.resource.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class QuizRestControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setUp() {
        restTemplate.getRestTemplate().setInterceptors(Arrays.asList(new LoggingInterceptor(), new JsonInterceptor()));
    }

    @Test
    public void answerQuiz_should_check_user_answers() {
        assertThatQuizIsCreatedAndAnswerWithEmptySolution(null);
    }

    @Test
    public void answerQuiz_should_check_user_answers_using_capitals() {
        final QuizConfiguration configuration = new QuizConfiguration();
        configuration.setResponseType(ResponseType.MULTIPLE_CHOICE);
        configuration.setQuestionType(QuestionType.CAPITAL);

        assertThatQuizIsCreatedAndAnswerWithEmptySolution(configuration);
    }

    @Test
    public void answerQuiz_should_check_user_answers_using_flags() {
        final QuizConfiguration configuration = new QuizConfiguration();
        configuration.setResponseType(ResponseType.MULTIPLE_CHOICE);
        configuration.setQuestionType(QuestionType.FLAG);

        assertThatQuizIsCreatedAndAnswerWithEmptySolution(configuration);
    }

    @Test
    public void answerQuiz_should_check_user_answers_using_silhouettes() {
        final QuizConfiguration configuration = new QuizConfiguration();
        configuration.setResponseType(ResponseType.MULTIPLE_CHOICE);
        configuration.setQuestionType(QuestionType.SILHOUETTE);

        assertThatQuizIsCreatedAndAnswerWithEmptySolution(configuration);
    }

    @Test
    public void answerQuiz_should_check_user_answers_using_land_area() {
        final QuizConfiguration configuration = new QuizConfiguration();
        configuration.setResponseType(ResponseType.MULTIPLE_CHOICE);
        configuration.setQuestionType(QuestionType.LAND_AREA);

        assertThatQuizIsCreatedAndAnswerWithEmptySolution(configuration);
    }

    @Test
    public void answerQuiz_should_check_user_answers_using_water_area() {
        final QuizConfiguration configuration = new QuizConfiguration();
        configuration.setResponseType(ResponseType.MULTIPLE_CHOICE);
        configuration.setQuestionType(QuestionType.WATER_AREA);

        assertThatQuizIsCreatedAndAnswerWithEmptySolution(configuration);
    }

    @Test
    public void answerQuiz_should_check_user_answers_using_total_area() {
        final QuizConfiguration configuration = new QuizConfiguration();
        configuration.setResponseType(ResponseType.MULTIPLE_CHOICE);
        configuration.setQuestionType(QuestionType.TOTAL_AREA);

        assertThatQuizIsCreatedAndAnswerWithEmptySolution(configuration);
    }

    @Test
    public void answerQuiz_should_not_generate_a_quiz_due_to_a_misconfiguration_with_using_water_area() {
        final QuizConfiguration configuration = new QuizConfiguration();
        configuration.setResponseType(ResponseType.ANSWER);
        configuration.setQuestionType(QuestionType.WATER_AREA);

        final ResponseEntity<Void> response = restTemplate.postForEntity("/api/quizzes", configuration, Void.class);

        ResponseEntityAssert.assertThat(response)
                .hasStatus(BAD_REQUEST)
                .hasNoBody();
    }

    private void assertThatQuizIsCreatedAndAnswerWithEmptySolution(QuizConfiguration configuration) {
        final ResponseEntity<String> response = restTemplate.postForEntity("/api/quizzes", configuration, String.class);

        ResponseEntityAssert.assertThat(response)
                .hasStatus(CREATED)
                .hasNoBody();
        final URI location = response.getHeaders().getLocation();

        final ResponseEntity<Quiz> response1 = restTemplate.getForEntity(location, Quiz.class);
        assertThat(response1.getStatusCode()).isEqualTo(OK);
        final Quiz quiz = response1.getBody();
        assertThat(quiz).isNotNull();
        assertThat(quiz.getQuestions()).hasSize(10);

        final QuizResponse quizResponse = new QuizResponse();
        quizResponse.setAnswers(Arrays.asList("", "", "", "", "", "", "", "", "", ""));
        final QuizResult expected = new QuizResult();
        expected.setNbOfCorrectAnswers(0);
        expected.setNbOfQuestions(10);

        final ResponseEntity<QuizResult> response2 = restTemplate.exchange("/api/quizzes/" + quiz.getId(), HttpMethod.PUT, new HttpEntity<>(quizResponse), QuizResult.class);

        ResponseEntityAssert.assertThat(response2)
                .hasStatus(OK)
                .hasBody(expected);
    }

    @Test
    public void answerQuiz_should_not_recognize_null_body() {
        final BadRequestBody errorBody = new BadRequestBody();
        errorBody.addMessage("Required request body is missing");

        final ResponseEntity<BadRequestBody> response = restTemplate.exchange("/api/quizzes/45", HttpMethod.PUT, null, BadRequestBody.class);

        ResponseEntityAssert.assertThat(response)
                .hasStatus(BAD_REQUEST)
                .hasBody(errorBody);
    }

    @Test
    public void answerQuiz_should_not_validate_null_body() {
        final HttpEntity<QuizResponse> entity = new HttpEntity<>((QuizResponse) null);
        final BadRequestBody errorBody = new BadRequestBody();
        errorBody.addMessage("Required request body is missing");

        final ResponseEntity<BadRequestBody> response = restTemplate.exchange("/api/quizzes/54", HttpMethod.PUT, entity, BadRequestBody.class);

        ResponseEntityAssert.assertThat(response)
                .hasStatus(BAD_REQUEST)
                .hasBody(errorBody);
    }

    @Test
    public void answerQuiz_should_not_validate_empty_body() {
        final HttpEntity<String> entity = new HttpEntity<>("");
        final BadRequestBody errorBody = new BadRequestBody();
        errorBody.addMessage("Required request body is missing");

        final ResponseEntity<BadRequestBody> response = restTemplate.exchange("/api/quizzes/78", HttpMethod.PUT, entity, BadRequestBody.class);

        ResponseEntityAssert.assertThat(response)
                .hasStatus(BAD_REQUEST)
                .hasBody(errorBody);
    }

    @Test
    public void answerQuiz_should_not_validate_null_list_of_answers() {
        final QuizResponse quizResponse = new QuizResponse();
        quizResponse.setAnswers(null);
        final HttpEntity<QuizResponse> entity = new HttpEntity<>(quizResponse);
        final BadRequestBody errorBody = new BadRequestBody();
        errorBody.addMessage("answers must not be null");

        final ResponseEntity<BadRequestBody> response = restTemplate.exchange("/api/quizzes/54", HttpMethod.PUT, entity, BadRequestBody.class);

        ResponseEntityAssert.assertThat(response)
                .hasStatus(BAD_REQUEST)
                .hasBody(errorBody);
    }

    @Test
    public void answerQuiz_should_not_validate_null_element_in_list_of_answers() {
        final QuizResponse quizResponse = new QuizResponse();
        quizResponse.setAnswers(Arrays.asList(null, "", null));
        final HttpEntity<QuizResponse> entity = new HttpEntity<>(quizResponse);
        final BadRequestBody errorBody = new BadRequestBody();
        errorBody.addMessage("answers[0] must not be null");
        errorBody.addMessage("answers[2] must not be null");

        final ResponseEntity<BadRequestBody> response = restTemplate.exchange("/api/quizzes/54", HttpMethod.PUT, entity, BadRequestBody.class);

        ResponseEntityAssert.assertThat(response)
                .hasStatus(BAD_REQUEST)
                .hasBody(errorBody);
    }

}
