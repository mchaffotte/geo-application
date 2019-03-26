package fr.chaffotm.geobase.restcontroller;

import fr.chaffotm.geobase.interceptor.JsonInterceptor;
import fr.chaffotm.geobase.interceptor.LoggingInterceptor;
import fr.chaffotm.geoquiz.resource.AnswerType;
import fr.chaffotm.geoquiz.resource.QuestionType;
import fr.chaffotm.geoquiz.resource.QuizType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.OK;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class QuizTypeRestControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setUp() {
        restTemplate.getRestTemplate().setInterceptors(Arrays.asList(new LoggingInterceptor(), new JsonInterceptor()));
    }

    private QuizType build(final QuestionType questionType, final AnswerType... answerTypes) {
        final QuizType type = new QuizType();
        type.setQuestionType(questionType);
        type.setAnswerTypes(Arrays.asList(answerTypes));
        return type;
    }

    @Test
    public void getQuizTypes_should_return_all_possible_types() {
        final ResponseEntity<List<QuizType>> response = restTemplate.exchange("/api/quiz-types", HttpMethod.GET,
                null, new ParameterizedTypeReference<List<QuizType>>() {});

        assertThat(response.getStatusCode()).isEqualTo(OK);

        final List<QuizType> quizTypes = response.getBody();
        assertThat(quizTypes)
                .containsExactlyInAnyOrder(
                        build(QuestionType.CAPITAL, AnswerType.ANSWER, AnswerType.MULTIPLE_CHOICE),
                        build(QuestionType.TOTAL_AREA, AnswerType.MULTIPLE_CHOICE),
                        build(QuestionType.LAND_AREA, AnswerType.MULTIPLE_CHOICE),
                        build(QuestionType.WATER_AREA, AnswerType.MULTIPLE_CHOICE),
                        build(QuestionType.FLAG, AnswerType.ANSWER, AnswerType.MULTIPLE_CHOICE),
                        build(QuestionType.SILHOUETTE, AnswerType.ANSWER, AnswerType.MULTIPLE_CHOICE)
                );
    }

}
