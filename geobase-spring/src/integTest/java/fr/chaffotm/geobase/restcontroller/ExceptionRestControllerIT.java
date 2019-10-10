package fr.chaffotm.geobase.restcontroller;

import fr.chaffotm.geobase.interceptor.JsonInterceptor;
import fr.chaffotm.geobase.interceptor.LoggingInterceptor;
import fr.chaffotm.geobase.web.exception.BadRequestBody;
import fr.chaffotm.geobase.web.exception.ErrorBody;
import fr.chaffotm.geobase.web.resource.Todo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static fr.chaffotm.geobase.assertion.ResponseEntityAssert.assertThat;
import static org.springframework.http.HttpStatus.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ExceptionRestControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        restTemplate.getRestTemplate().setInterceptors(
                List.of(new LoggingInterceptor(), new JsonInterceptor())
        );
        restTemplate.getRestTemplate().setMessageConverters(
                List.of(new MappingJackson2HttpMessageConverter(), new Jaxb2RootElementHttpMessageConverter())
        );
    }

    @Test
    @DisplayName("if an exception occurs an internal server error is returned")
    public void ifAnExceptionOccursAnInternalServerErrorIsReturned() {
        final ResponseEntity<ErrorBody> response = restTemplate.getForEntity("/api/exceptions/exception", ErrorBody.class);

        assertThat(response)
                .hasStatus(INTERNAL_SERVER_ERROR)
                .hasNoBody();
    }

    @Test
    @DisplayName("if a runtime exception occurs an internal server error is returned")
    public void ifARuntimeExceptionOccursAnInternalServerErrorIsReturned() {
        final ResponseEntity<ErrorBody> response = restTemplate.getForEntity("/api/exceptions/runtime-exception", ErrorBody.class);

        assertThat(response)
                .hasStatus(INTERNAL_SERVER_ERROR)
                .hasNoBody();
    }

    @Test
    @DisplayName("if an illegal argument exception occurs a bad request is returned")
    public void ifAnIllegalArgumentExceptionOccursABadRequestIsReturned() {
        final BadRequestBody errorBody = new BadRequestBody();
        errorBody.addMessage("wrong argument");

        final ResponseEntity<BadRequestBody> response = restTemplate.getForEntity("/api/exceptions/illegal-argument-exception", BadRequestBody.class);

        assertThat(response)
                .hasStatus(BAD_REQUEST)
                .hasBody(errorBody);
    }

    @Test
    @DisplayName("if an entity exists exception occurs a conflict is returned")
    public void ifAnEntityExistsExceptionOccursAConflictIsReturned() {
        final ErrorBody errorBody = new ErrorBody();
        errorBody.setMessage("resource with id 5 does not exist");

        final ResponseEntity<ErrorBody> response = restTemplate.getForEntity("/api/exceptions/entity-exists-exception", ErrorBody.class);

        assertThat(response)
                .hasStatus(CONFLICT)
                .hasBody(errorBody);
    }

    @Test
    @DisplayName("if an entity not found exception occurs a not found is returned")
    public void ifAnEntityNotFoundExceptionOccursANotFoundIsReturned() {
        final ResponseEntity<String> response = restTemplate.getForEntity("/api/exceptions/entity-not-found-exception", String.class);

        assertThat(response)
                .hasStatus(NOT_FOUND)
                .hasNoBody();
    }

    @Test
    @DisplayName("if a not found exception occurs a not found is returned")
    public void ifANotFoundExceptionOccursANotFoundIsReturned() {
        final ResponseEntity<String> response = restTemplate.getForEntity("/api/exceptions/not-found-exception", String.class);

        assertThat(response)
                .hasStatus(NOT_FOUND)
                .hasNoBody();
    }

    @Test
    @DisplayName("if a constraint validation exception occurs a bad request is returned with explanation")
    public void ifAConstraintValidationExceptionOccursABadRequestIsReturnedWithExplanation() {
        final Todo todo = new Todo();
        todo.setEmail("todo@");
        todo.setStartDate(LocalDate.now().minusDays(2));
        final BadRequestBody errorBody = new BadRequestBody();
        errorBody.addMessage("email must be a well-formed email address");
        errorBody.addMessage("Title cannot be null");
        errorBody.addMessage("message must not be blank");
        errorBody.addMessage("priority must be greater than 0");
        errorBody.addMessage("startDate must be a date in the present or in the future");

        final ResponseEntity<BadRequestBody> response = restTemplate.postForEntity("/api/exceptions/todos", todo, BadRequestBody.class);

        assertThat(response)
                .hasStatus(BAD_REQUEST)
                .hasBody(errorBody);
    }

    @Test
    @DisplayName("if the payload use an invalid media type an unsupported media type is returned")
    public void ifThePayloadUseAnInvalidMediaTypeAnUnsupportedMediaTypeIsReturned() {
        final Todo todo = new Todo();
        todo.setPriority(1);
        todo.setTitle("Important");
        todo.setMessage("Sleeping");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_XML);
        HttpEntity<Todo> entity = new HttpEntity<>(todo, headers);

        final ResponseEntity<Void> response = restTemplate.postForEntity("/api/exceptions/todos", entity, Void.class);

        assertThat(response)
                .hasStatus(UNSUPPORTED_MEDIA_TYPE)
                .hasNoBody();
    }

    @Test
    @DisplayName("if the accept header is not supported by the server the server returns ok in a supported representation")
    public void ifTheAcceptHeaderIsNotSupportedByTheServerTheServerReturnsOkInASupportedRepresentation() {
        final HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_PDF));
        final HttpEntity<Todo> entity = new HttpEntity<>(null, headers);
        final Todo todo = new Todo();

        final ResponseEntity<Todo> response = restTemplate.exchange("/api/exceptions/todos/45", HttpMethod.GET, entity, Todo.class);

        assertThat(response)
                .hasStatus(OK)
                .hasContentType(MediaType.APPLICATION_JSON_UTF8)
                .hasBody(todo);
    }

    @Test
    @DisplayName("if the payload does not contains a json object a bad request is returned")
    public void if_the_payload_does_not_contains_a_json_object_a_bad_request_is_returned() {
        final BadRequestBody errorBody = new BadRequestBody();
        errorBody.addMessage("Required request body is missing");

        final ResponseEntity<BadRequestBody> response = restTemplate.postForEntity("/api/exceptions/todos", "test", BadRequestBody.class);

        assertThat(response)
                .hasStatus(BAD_REQUEST)
                .hasBody(errorBody);
    }

}
