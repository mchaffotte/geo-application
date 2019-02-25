package fr.chaffotm.geobase.restcontroller;

import fr.chaffotm.geobase.configuration.BadRequestBody;
import fr.chaffotm.geobase.configuration.ErrorBody;
import fr.chaffotm.geobase.interceptor.JsonInterceptor;
import fr.chaffotm.geobase.interceptor.LoggingInterceptor;
import fr.chaffotm.geobase.web.rest.domain.Todo;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static fr.chaffotm.geobase.assertion.ResponseEntityAssert.assertThat;
import static org.springframework.http.HttpStatus.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ExceptionRestControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setUp() {
        restTemplate.getRestTemplate().setInterceptors(Arrays.asList(new LoggingInterceptor(), new JsonInterceptor()));
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(new MappingJackson2HttpMessageConverter());
        messageConverters.add(new Jaxb2RootElementHttpMessageConverter());
        restTemplate.getRestTemplate().setMessageConverters(messageConverters);
    }

    @Test
    public void if_an_exception_occurs_an_internal_server_error_is_returned() {
        final ErrorBody errorBody = new ErrorBody();

        final ResponseEntity<ErrorBody> response = restTemplate.getForEntity("/api/exceptions/exception", ErrorBody.class);

        assertThat(response)
                .hasStatus(INTERNAL_SERVER_ERROR)
                .hasBody(errorBody);
    }

    @Test
    public void if_a_runtime_exception_occurs_an_internal_server_error_is_returned() {
        final ErrorBody errorBody = new ErrorBody();
        errorBody.setMessage("It does not work");

        final ResponseEntity<ErrorBody> response = restTemplate.getForEntity("/api/exceptions/runtime-exception", ErrorBody.class);

        assertThat(response)
                .hasStatus(INTERNAL_SERVER_ERROR)
                .hasBody(errorBody);
    }

    @Test
    public void if_an_illegal_argument_exception_occurs_a_bad_request_is_returned() {
        final BadRequestBody errorBody = new BadRequestBody();
        errorBody.addMessage("wrong argument");

        final ResponseEntity<BadRequestBody> response = restTemplate.getForEntity("/api/exceptions/illegal-argument-exception", BadRequestBody.class);

        assertThat(response)
                .hasStatus(BAD_REQUEST)
                .hasBody(errorBody);
    }

    @Test
    public void if_an_entity_exists_exception_occurs_a_conflict_is_returned() {
        final ErrorBody errorBody = new ErrorBody();
        errorBody.setMessage("resource with id 5 does not exist");

        final ResponseEntity<ErrorBody> response = restTemplate.getForEntity("/api/exceptions/entity-exists-exception", ErrorBody.class);

        assertThat(response)
                .hasStatus(CONFLICT)
                .hasBody(errorBody);
    }

    @Test
    public void if_an_entity_not_found_exception_occurs_a_not_found_is_returned() {
        final ResponseEntity<String> response = restTemplate.getForEntity("/api/exceptions/entity-not-found-exception", String.class);

        assertThat(response)
                .hasStatus(NOT_FOUND)
                .hasNoBody();
    }

    @Test
    public void if_a_constraint_validation_exception_occurs_a_bad_request_is_returned_with_explanation() {
        final Todo todo = new Todo();
        todo.setEmail("todo@");
        final BadRequestBody errorBody = new BadRequestBody();
        errorBody.addMessage("email must be a well-formed email address");
        errorBody.addMessage("Title cannot be null");
        errorBody.addMessage("message must not be blank");
        errorBody.addMessage("priority must be greater than 0");

        final ResponseEntity<BadRequestBody> response = restTemplate.postForEntity("/api/exceptions/todos", todo, BadRequestBody.class);

        assertThat(response)
                .hasStatus(BAD_REQUEST)
                .hasBody(errorBody);
    }

    @Test
    public void if_the_payload_does_not_use_a_good_media_type_an_unsupported_media_type_is_returned() {
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
    public void if_the_accept_header_is_not_supported_by_the_server_the_server_returns_ok_in_a_supported_representation() {
        final HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_PDF));
        final HttpEntity<Todo> entity = new HttpEntity<>(null, headers);
        final Todo todo = new Todo();

        final ResponseEntity<Todo> response = restTemplate.exchange("/api/exceptions/todos/45", HttpMethod.GET, entity, Todo.class);

        assertThat(response)
                .hasStatus(OK)
                .hasContentType(MediaType.APPLICATION_JSON_UTF8)
                .hasBody(todo);
    }

}
