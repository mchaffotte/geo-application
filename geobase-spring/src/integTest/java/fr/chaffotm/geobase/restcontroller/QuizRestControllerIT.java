package fr.chaffotm.geobase.restcontroller;

import fr.chaffotm.geobase.assertion.ResponseEntityAssert;
import fr.chaffotm.geobase.interceptor.JsonInterceptor;
import fr.chaffotm.geobase.interceptor.LoggingInterceptor;
import fr.chaffotm.geobase.web.exception.BadRequestBody;
import fr.chaffotm.geoquiz.builder.QuizAnswerBuilder;
import fr.chaffotm.geoquiz.resource.*;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.URI;
import java.util.List;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class QuizRestControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    private Condition<Question> withoutChoices;

    private Condition<Question> fourChoices;

    @BeforeEach
    public void setUp() {
        restTemplate.getRestTemplate().setInterceptors(
                List.of(new LoggingInterceptor(), new JsonInterceptor())
        );
        withoutChoices = new Condition<>(hasChoices(0), "Empty choices");
        fourChoices = new Condition<>(hasChoices(4), "4 choices");
    }

    private Predicate<Question> hasChoices(final int numberOfChoices) {
        return question -> question.getChoices().size() == numberOfChoices;
    }

    @Test
    @DisplayName("answerQuiz should check user answers")
    public void answerQuizShouldCheckUserAnswers() {
        assertThatQuizIsCreatedAndAnswerWithEmptySolution(null, fourChoices);
    }

    @Test
    @DisplayName("answerQuiz should generate a quiz without choices")
    public void answerQuizShouldGenerateAQuizWithoutChoices() {
        final QuizConfiguration configuration = new QuizConfiguration();
        configuration.setAnswerType(AnswerType.ANSWER);
        configuration.setQuestionType(QuestionType.CAPITAL);

        assertThatQuizIsCreatedAndAnswerWithEmptySolution(configuration, withoutChoices);
    }

    @Test
    @DisplayName("answerQuiz should check user answers using capitals")
    public void answerQuizShouldCheckUserAnswersUsingCapitals() {
        final QuizConfiguration configuration = new QuizConfiguration();
        configuration.setAnswerType(AnswerType.MULTIPLE_CHOICE);
        configuration.setQuestionType(QuestionType.CAPITAL);

        assertThatQuizIsCreatedAndAnswerWithEmptySolution(configuration, fourChoices);
    }

    @Test
    @DisplayName("answerQuiz should check user answers using flags")
    public void answerQuizShouldCheckUserAnswersUsingFlags() {
        final QuizConfiguration configuration = new QuizConfiguration();
        configuration.setAnswerType(AnswerType.MULTIPLE_CHOICE);
        configuration.setQuestionType(QuestionType.FLAG);

        assertThatQuizIsCreatedAndAnswerWithEmptySolution(configuration, fourChoices);
    }

    @Test
    @DisplayName("answerQuiz should check user answers using silhouettes")
    public void answerQuizShouldCheckUserAnswersUsingSilhouettes() {
        final QuizConfiguration configuration = new QuizConfiguration();
        configuration.setAnswerType(AnswerType.MULTIPLE_CHOICE);
        configuration.setQuestionType(QuestionType.SILHOUETTE);

        assertThatQuizIsCreatedAndAnswerWithEmptySolution(configuration, fourChoices);
    }

    @Test
    @DisplayName("answerQuiz should check user answers using land area")
    public void answerQuizShouldCheckUserAnswersUsingLandArea() {
        final QuizConfiguration configuration = new QuizConfiguration();
        configuration.setAnswerType(AnswerType.MULTIPLE_CHOICE);
        configuration.setQuestionType(QuestionType.LAND_AREA);

        assertThatQuizIsCreatedAndAnswerWithEmptySolution(configuration, fourChoices);
    }

    @Test
    @DisplayName("answerQuiz should check user answers using water area")
    public void answerQuizShouldCheckUserAnswersUsingWaterArea() {
        final QuizConfiguration configuration = new QuizConfiguration();
        configuration.setAnswerType(AnswerType.MULTIPLE_CHOICE);
        configuration.setQuestionType(QuestionType.WATER_AREA);

        assertThatQuizIsCreatedAndAnswerWithEmptySolution(configuration, fourChoices);
    }

    @Test
    @DisplayName("answerQuiz should check user answers using total area")
    public void answerQuizShouldCheckUserAnswersUsingTotalArea() {
        final QuizConfiguration configuration = new QuizConfiguration();
        configuration.setAnswerType(AnswerType.MULTIPLE_CHOICE);
        configuration.setQuestionType(QuestionType.TOTAL_AREA);

        assertThatQuizIsCreatedAndAnswerWithEmptySolution(configuration, fourChoices);
    }

    @Test
    @DisplayName("answerQuiz should not generate a quiz due to a misconfiguration using water area")
    public void answerQuizShouldNotGenerateAQuizDueToAMisconfigurationUsingWaterArea() {
        final QuizConfiguration configuration = new QuizConfiguration();
        configuration.setAnswerType(AnswerType.ANSWER);
        configuration.setQuestionType(QuestionType.WATER_AREA);

        final ResponseEntity<Void> response = restTemplate.postForEntity("/api/quizzes", configuration, Void.class);

        ResponseEntityAssert.assertThat(response)
                .hasStatus(BAD_REQUEST)
                .hasNoBody();
    }

    private void assertThatQuizIsCreatedAndAnswerWithEmptySolution(final QuizConfiguration configuration, final Condition<Question> condition) {
        final ResponseEntity<String> response = restTemplate.postForEntity("/api/quizzes", configuration, String.class);

        ResponseEntityAssert.assertThat(response)
                .hasStatus(CREATED)
                .hasNoBody();
        final URI location = response.getHeaders().getLocation();

        final ResponseEntity<Quiz> response1 = restTemplate.getForEntity(location, Quiz.class);
        assertThat(response1.getStatusCode()).isEqualTo(OK);
        final Quiz quiz = response1.getBody();
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

        final ResponseEntity<QuizResult> response2 = restTemplate.exchange("/api/quizzes/" + quiz.getId(), HttpMethod.PUT, new HttpEntity<>(quizAnswer), QuizResult.class);

        ResponseEntityAssert.assertThat(response2)
                .hasStatus(OK)
                .hasBody(expected);
    }

    @Test
    @DisplayName("answerQuiz should not recognize null body")
    public void answerQuizShouldNotRecognizeNullBody() {
        final BadRequestBody errorBody = new BadRequestBody();
        errorBody.addMessage("Required request body is missing");

        final ResponseEntity<BadRequestBody> response = restTemplate.exchange("/api/quizzes/45", HttpMethod.PUT, null, BadRequestBody.class);

        ResponseEntityAssert.assertThat(response)
                .hasStatus(BAD_REQUEST)
                .hasBody(errorBody);
    }

    @Test
    @DisplayName("answerQuiz should not validate null body")
    public void answerQuizShouldNotValidateNullBody() {
        final HttpEntity<QuizAnswer> entity = new HttpEntity<>((QuizAnswer) null);
        final BadRequestBody errorBody = new BadRequestBody();
        errorBody.addMessage("Required request body is missing");

        final ResponseEntity<BadRequestBody> response = restTemplate.exchange("/api/quizzes/54", HttpMethod.PUT, entity, BadRequestBody.class);

        ResponseEntityAssert.assertThat(response)
                .hasStatus(BAD_REQUEST)
                .hasBody(errorBody);
    }

    @Test
    @DisplayName("answerQuiz should not validate empty body")
    public void answerQuizShouldNotValidateEmptyBody() {
        final HttpEntity<String> entity = new HttpEntity<>("");
        final BadRequestBody errorBody = new BadRequestBody();
        errorBody.addMessage("Required request body is missing");

        final ResponseEntity<BadRequestBody> response = restTemplate.exchange("/api/quizzes/78", HttpMethod.PUT, entity, BadRequestBody.class);

        ResponseEntityAssert.assertThat(response)
                .hasStatus(BAD_REQUEST)
                .hasBody(errorBody);
    }

    @Test
    @DisplayName("answerQuiz should not validate null list of question answers")
    public void answerQuizShouldNotValidateNullListOfQuestionAnswers() {
        final QuizAnswer quizResponse = new QuizAnswer();
        quizResponse.setQuestionAnswers(null);
        final HttpEntity<QuizAnswer> entity = new HttpEntity<>(quizResponse);
        final BadRequestBody errorBody = new BadRequestBody();
        errorBody.addMessage("questionAnswers must not be null");

        final ResponseEntity<BadRequestBody> response = restTemplate.exchange("/api/quizzes/54", HttpMethod.PUT, entity, BadRequestBody.class);

        ResponseEntityAssert.assertThat(response)
                .hasStatus(BAD_REQUEST)
                .hasBody(errorBody);
    }

    @Test
    @DisplayName("answerQuiz should not validate null element in list of question answers")
    public void answerQuizShouldNotValidateNullElementInListOfQuestionAnswers() {
        final QuizAnswer quizAnswer = new QuizAnswerBuilder()
                .questionAnswer(null)
                .questionAnswer("")
                .questionAnswer(null)
                .getQuizAnswer();
        final HttpEntity<QuizAnswer> entity = new HttpEntity<>(quizAnswer);
        final BadRequestBody errorBody = new BadRequestBody();
        errorBody.addMessage("questionAnswers[0].answers[0] must not be null");
        errorBody.addMessage("questionAnswers[2].answers[0] must not be null");

        final ResponseEntity<BadRequestBody> response = restTemplate.exchange("/api/quizzes/54", HttpMethod.PUT, entity, BadRequestBody.class);

        ResponseEntityAssert.assertThat(response)
                .hasStatus(BAD_REQUEST)
                .hasBody(errorBody);
    }

    @Test
    @DisplayName("answerQuiz should not validate null element in list of answers")
    public void answerQuizShouldNotValidateNullElementInListOfAnswers() {
        final QuizAnswer quizAnswer = new QuizAnswerBuilder()
                .questionAnswer(null, "", null)
                .getQuizAnswer();
        final HttpEntity<QuizAnswer> entity = new HttpEntity<>(quizAnswer);
        final BadRequestBody errorBody = new BadRequestBody();
        errorBody.addMessage("questionAnswers[0].answers[0] must not be null");
        errorBody.addMessage("questionAnswers[0].answers[2] must not be null");

        final ResponseEntity<BadRequestBody> response = restTemplate.exchange("/api/quizzes/54", HttpMethod.PUT, entity, BadRequestBody.class);

        ResponseEntityAssert.assertThat(response)
                .hasStatus(BAD_REQUEST)
                .hasBody(errorBody);
    }

}
