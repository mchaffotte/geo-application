package fr.chaffotm.geobase;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wildfly.swarm.jaxrs.JAXRSArchive;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.net.URI;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static org.assertj.core.api.Assertions.*;

@RunWith(Arquillian.class)
@RunAsClient
public class ExceptionEndpointIT {

    @Deployment(testable = false)
    public static Archive createDeployment() {
        System.setProperty("swarm.http.port", "8090");

        final JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class);
        deployment.addPackages(true, "fr.chaffotm.geobase");
        deployment.addAsResource("META-INF/persistence-test.xml", "META-INF/persistence.xml");
        deployment.addAsResource("META-INF/sql/create.sql");
        deployment.addAsResource("META-INF/sql/drop.sql");
        deployment.addAsResource("project-defaults.yml");
        return deployment;
    }

    @ArquillianResource
    private URI baseURL;

    @Test
    public void if_an_exception_occurs_an_internal_server_error_is_returned() {
        final Client client = ClientBuilder.newClient();
        final WebTarget webTarget = client.target(baseURL).path("api/exceptions/exception");

        final Response response = webTarget.request(APPLICATION_JSON_TYPE).get();

        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.INTERNAL_SERVER_ERROR);
    }

    @Test
    public void if_a_runtime_exception_occurs_an_internal_server_error_is_returned() {
        final Client client = ClientBuilder.newClient();
        final WebTarget webTarget = client.target(baseURL).path("api/exceptions/runtime-exception");

        final Response response = webTarget.request(APPLICATION_JSON_TYPE).get();

        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.INTERNAL_SERVER_ERROR);
    }

    @Test
    public void if_an_illegal_argument_exception_occurs_a_bad_request_is_returned() {
        final Client client = ClientBuilder.newClient();
        final WebTarget webTarget = client.target(baseURL).path("api/exceptions/illegal-argument-exception");

        final Response response = webTarget.request(APPLICATION_JSON_TYPE).get();

        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.BAD_REQUEST);
    }

    @Test
    public void if_an_entity_exists_exception_occurs_a_conflict_is_returned() {
        final Client client = ClientBuilder.newClient();
        final WebTarget webTarget = client.target(baseURL).path("api/exceptions/entity-exists-exception");

        final Response response = webTarget.request(APPLICATION_JSON_TYPE).get();

        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.CONFLICT);
    }

    @Test
    public void if_an_entity_not_found_exception_occurs_a_not_found_is_returned() {
        final Client client = ClientBuilder.newClient();
        final WebTarget webTarget = client.target(baseURL).path("api/exceptions/entity-not-found-exception");

        final Response response = webTarget.request(APPLICATION_JSON_TYPE).get();

        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.NOT_FOUND);
    }

    @Test
    public void if_a_constraint_validation_exception_occurs_a_bad_request_is_returned() {
        final Client client = ClientBuilder.newClient();
        final WebTarget webTarget = client.target(baseURL).path("api/exceptions/constraint-violation-exception");

        final Response response = webTarget.request(APPLICATION_JSON_TYPE).get();

        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.BAD_REQUEST);
    }

}
