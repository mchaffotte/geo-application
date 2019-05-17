package fr.chaffotm.geobase.restcontroller;

import fr.chaffotm.geobase.assertion.ResponseEntityAssert;
import fr.chaffotm.geobase.interceptor.JsonInterceptor;
import fr.chaffotm.geobase.interceptor.LoggingInterceptor;
import fr.chaffotm.quizzify.resource.AnswerType;
import fr.chaffotm.quizzify.resource.Quiz;
import fr.chaffotm.quizzify.resource.QuizConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ImageRestControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        final RestTemplate template = this.restTemplate.getRestTemplate();
        template.setInterceptors(
                List.of(new LoggingInterceptor(), new JsonInterceptor())
        );
        final ByteArrayHttpMessageConverter imageConverter = new ByteArrayHttpMessageConverter();
        imageConverter.setSupportedMediaTypes(List.of(MediaType.IMAGE_PNG));
        final List<HttpMessageConverter<?>> converters = new ArrayList<>(template.getMessageConverters());
        converters.add(imageConverter);
        template.setMessageConverters(converters);
    }

    @Test
    @DisplayName("get image should retrieve the correct image")
    public void getImageShouldRetrieveTheCorrectImage() {
        final QuizConfiguration configuration = new QuizConfiguration();
        configuration.setAnswerType(AnswerType.MULTIPLE_CHOICE);
        configuration.setQuestionType("SILHOUETTE");

        final ResponseEntity<String> response = restTemplate.postForEntity("/api/quizzes", configuration, String.class);

        ResponseEntityAssert.assertThat(response)
                .hasStatus(CREATED)
                .hasNoBody();

        final URI location = response.getHeaders().getLocation();
        final ResponseEntity<Quiz> quizResponse = restTemplate.getForEntity(location, Quiz.class);
        assertThat(quizResponse.getStatusCode()).isEqualTo(OK);
        final Quiz quiz = quizResponse.getBody();
        assertThat(quiz.getQuestions())
                .hasSize(10);

        final ResponseEntity<byte[]> imageResponse = restTemplate.getForEntity(quiz.getQuestions().get(0).getImagePath(), byte[].class);
        ResponseEntityAssert.assertThat(imageResponse)
                .hasStatus(OK);
    }

}
