package fr.chaffotm.geobase.endpoint;

import fr.chaffotm.geobase.web.exception.BadRequestBody;
import fr.chaffotm.geobase.web.exception.ErrorBody;
import fr.chaffotm.geobase.web.rest.domain.Todo;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.time.LocalDate;

import static fr.chaffotm.geobase.assertion.ResponseAssert.assertThat;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static javax.ws.rs.core.MediaType.TEXT_XML_TYPE;
import static javax.ws.rs.core.Response.Status.*;

@QuarkusTest
public class ExceptionEndpointIT {

    @TestHTTPResource("/geobase")
    private URI baseURL;

    private Client client;

    @BeforeEach
    public void setUp() {
        client = TestConfiguration.buildClient();
    }

    @AfterEach
    public void tearDown() {
        client.close();
    }

    @Test
    public void if_an_exception_occurs_an_internal_server_error_is_returned() {
        final WebTarget webTarget = client.target(baseURL).path("api/exceptions/exception");

        final Response response = webTarget.request(APPLICATION_JSON_TYPE).get();

        assertThat(response)
                .hasStatus(INTERNAL_SERVER_ERROR)
                .hasNoBody();
    }

    @Test
    public void if_a_runtime_exception_occurs_an_internal_server_error_is_returned() {
        final WebTarget webTarget = client.target(baseURL).path("api/exceptions/runtime-exception");

        final Response response = webTarget.request(APPLICATION_JSON_TYPE).get();

        assertThat(response)
                .hasStatus(INTERNAL_SERVER_ERROR)
                .hasNoBody();
    }

    @Test
    public void if_an_illegal_argument_exception_occurs_a_bad_request_is_returned() {
        final WebTarget webTarget = client.target(baseURL).path("api/exceptions/illegal-argument-exception");
        final BadRequestBody errorBody = new BadRequestBody();
        errorBody.addMessage("wrong argument");

        final Response response = webTarget.request(APPLICATION_JSON_TYPE).get();

        assertThat(response)
                .hasStatus(BAD_REQUEST)
                .hasBody(errorBody);
    }

    @Test
    public void if_an_entity_exists_exception_occurs_a_conflict_is_returned() {
        final WebTarget webTarget = client.target(baseURL).path("api/exceptions/entity-exists-exception");
        final ErrorBody errorBody = new ErrorBody();
        errorBody.setMessage("resource with id 5 does not exist");

        final Response response = webTarget.request(APPLICATION_JSON_TYPE).get();

        assertThat(response)
                .hasStatus(CONFLICT)
                .hasBody(errorBody);
    }

    @Test
    public void if_an_entity_not_found_exception_occurs_a_not_found_is_returned() {
        final WebTarget webTarget = client.target(baseURL).path("api/exceptions/entity-not-found-exception");

        final Response response = webTarget.request(APPLICATION_JSON_TYPE).get();

        assertThat(response)
                .hasStatus(NOT_FOUND)
                .hasNoBody();
    }

    @Test
    public void if_a_not_found_exception_occurs_a_not_found_is_returned() {
        final WebTarget webTarget = client.target(baseURL).path("api/exceptions/not-found-exception");

        final Response response = webTarget.request(APPLICATION_JSON_TYPE).get();

        assertThat(response)
                .hasStatus(NOT_FOUND)
                .hasNoBody();
    }

    @Test
    public void if_a_constraint_validation_exception_occurs_a_bad_request_is_returned_with_explanation() {
        final Todo todo = new Todo();
        todo.setEmail("todo@fr.");
        todo.setStartDate(LocalDate.now().minusDays(2));
        final WebTarget webTarget = client.target(baseURL).path("api/exceptions/todos");
        final BadRequestBody errorBody = new BadRequestBody();
        errorBody.addMessage("email must be a well-formed email address");
        errorBody.addMessage("Title cannot be null");
        errorBody.addMessage("message must not be blank");
        errorBody.addMessage("priority must be greater than 0");
        errorBody.addMessage("startDate must be a date in the present or in the future");

        final Response response = webTarget.request(APPLICATION_JSON_TYPE).post(Entity.json(todo));

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
        final WebTarget webTarget = client.target(baseURL).path("api/exceptions/todos");

        final Response response = webTarget.request(APPLICATION_JSON_TYPE).post(Entity.text(todo));

        assertThat(response)
                .hasStatus(UNSUPPORTED_MEDIA_TYPE)
                .hasNoBody();
    }

    @Test
    public void if_the_accept_header_is_not_supported_by_the_server_a_not_acceptable_is_returned() {
        final WebTarget webTarget = client.target(baseURL).path("api/exceptions/todos/45");

        final Response response = webTarget.request(TEXT_XML_TYPE).get();

        assertThat(response)
                .hasStatus(NOT_ACCEPTABLE)
                .hasNoBody();
    }

    @Test
    public void if_the_payload_does_not_contains_a_json_object_a_bad_request_is_returned() {
        final WebTarget webTarget = client.target(baseURL).path("api/exceptions/todos");
        final BadRequestBody errorBody = new BadRequestBody();
        errorBody.addMessage("Required request body is missing");

        final Response response = webTarget.request(APPLICATION_JSON_TYPE).post(Entity.json("test"));

        assertThat(response)
                .hasStatus(BAD_REQUEST)
                .hasBody(errorBody);
    }

}
