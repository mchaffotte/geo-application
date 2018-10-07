package fr.chaffotm.geobase;

import fr.chaffotm.geobase.web.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class QuizIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setUp() {
        restTemplate.getRestTemplate().setInterceptors(
                Collections.singletonList((request, body, execution) -> {
                    request.getHeaders()
                            .setContentType(MediaType.APPLICATION_JSON);
                    request.getHeaders()
                            .setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                    return execution.execute(request, body);
                }));
    }

    @Test
    public void answerQuiz_should_check_user_answers() {
        assertThatQuizIsCreatedAndAnswerWithEmptySolution(null);
    }

    @Test
    public void answerQuiz_should_check_user_answers_using_capitals() {
        final QuizConfiguration configuration = new QuizConfiguration();
        configuration.setMultipleChoice(true);
        configuration.setQuestionType(QuestionType.CAPITAL);

        assertThatQuizIsCreatedAndAnswerWithEmptySolution(configuration);
    }

    @Test
    public void answerQuiz_should_check_user_answers_using_flags() {
        final QuizConfiguration configuration = new QuizConfiguration();
        configuration.setMultipleChoice(true);
        configuration.setQuestionType(QuestionType.FLAG);

        assertThatQuizIsCreatedAndAnswerWithEmptySolution(configuration);
    }

    @Test
    public void answerQuiz_should_check_user_answers_using_silhouettes() {
        final QuizConfiguration configuration = new QuizConfiguration();
        configuration.setMultipleChoice(true);
        configuration.setQuestionType(QuestionType.SILHOUETTE);

        assertThatQuizIsCreatedAndAnswerWithEmptySolution(configuration);
    }

    @Test
    public void answerQuiz_should_check_user_answers_using_land_area() {
        final QuizConfiguration configuration = new QuizConfiguration();
        configuration.setMultipleChoice(true);
        configuration.setQuestionType(QuestionType.LAND_AREA);

        assertThatQuizIsCreatedAndAnswerWithEmptySolution(configuration);
    }

    @Test
    public void answerQuiz_should_check_user_answers_using_water_area() {
        final QuizConfiguration configuration = new QuizConfiguration();
        configuration.setMultipleChoice(true);
        configuration.setQuestionType(QuestionType.WATER_AREA);

        assertThatQuizIsCreatedAndAnswerWithEmptySolution(configuration);
    }

    @Test
    public void answerQuiz_should_check_user_answers_using_total_area() {
        final QuizConfiguration configuration = new QuizConfiguration();
        configuration.setMultipleChoice(true);
        configuration.setQuestionType(QuestionType.TOTAL_AREA);

        assertThatQuizIsCreatedAndAnswerWithEmptySolution(configuration);
    }

    @Test
    public void answerQuiz_should_not_generate_a_quiz_due_to_a_misconfiguration_with_using_water_area() {
        final QuizConfiguration configuration = new QuizConfiguration();
        configuration.setMultipleChoice(false);
        configuration.setQuestionType(QuestionType.WATER_AREA);

        final ResponseEntity<String> response = restTemplate.postForEntity("/api/quizzes", configuration, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNull();
    }

    private void assertThatQuizIsCreatedAndAnswerWithEmptySolution(QuizConfiguration configuration) {
        final ResponseEntity<String> response = restTemplate.postForEntity("/api/quizzes", configuration, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        final URI location = response.getHeaders().getLocation();

        final ResponseEntity<Quiz> response1 = restTemplate.getForEntity(location, Quiz.class);
        assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.OK);
        final Quiz quiz = response1.getBody();
        assertThat(quiz).isNotNull();
        assertThat(quiz.getQuestions()).hasSize(10);

        final QuizAnswers answers = new QuizAnswers();
        answers.setAnswers(Arrays.asList("", "", "", "", "", "", "", "", "", ""));
        final ResponseEntity<QuizResult> response2 = restTemplate.exchange("/api/quizzes/" + quiz.getId(), HttpMethod.PUT, new HttpEntity<>(answers), QuizResult.class);
        assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.OK);

        final QuizResult expected = new QuizResult();
        expected.setNbOfCorrectAnswers(0);
        expected.setNbOfQuestions(10);
        final QuizResult result = response2.getBody();
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void answerQuiz_should_not_recognize_null_body() {
        final ResponseEntity<Quiz> response = restTemplate.exchange("/api/quizzes/45", HttpMethod.PUT, null, Quiz.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void answerQuiz_should_not_validate_null_body() {
        HttpEntity<Quiz> entity = new HttpEntity<>((Quiz) null);
        final ResponseEntity<Quiz> response = restTemplate.exchange("/api/quizzes/45", HttpMethod.PUT, entity, Quiz.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void answerQuiz_should_not_validate_empty_body() {
        HttpEntity<String> entity = new HttpEntity<>("");
        final ResponseEntity<Quiz> response = restTemplate.exchange("/api/quizzes/45", HttpMethod.PUT, entity, Quiz.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

}
