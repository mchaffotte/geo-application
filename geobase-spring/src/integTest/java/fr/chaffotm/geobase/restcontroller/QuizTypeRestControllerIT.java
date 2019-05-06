package fr.chaffotm.geobase.restcontroller;

import fr.chaffotm.geobase.interceptor.JsonInterceptor;
import fr.chaffotm.geobase.interceptor.LoggingInterceptor;
import fr.chaffotm.quizzify.resource.AnswerType;
import fr.chaffotm.quizzify.resource.QuizType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.OK;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class QuizTypeRestControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        restTemplate.getRestTemplate().setInterceptors(
                List.of(new LoggingInterceptor(), new JsonInterceptor())
        );
    }

    private QuizType build(final String questionType, final AnswerType... answerTypes) {
        final QuizType type = new QuizType();
        type.setQuestionType(questionType);
        type.setAnswerTypes(List.of(answerTypes));
        return type;
    }

    @Test
    @DisplayName("getQuizTypes should return all possible types")
    public void getQuizTypesShouldReturnAllPossibleTypes() {
        final ResponseEntity<List<QuizType>> response = restTemplate.exchange("/api/quiz-types", HttpMethod.GET,
                null, new ParameterizedTypeReference<List<QuizType>>() {});

        assertThat(response.getStatusCode()).isEqualTo(OK);

        final List<QuizType> quizTypes = response.getBody();
        assertThat(quizTypes)
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
