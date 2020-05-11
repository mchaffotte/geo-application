package fr.chaffotm.geobase.endpoint;

import fr.chaffotm.geobase.assertion.ResponseAssert;
import fr.chaffotm.quizzify.resource.AnswerType;
import fr.chaffotm.quizzify.resource.Quiz;
import fr.chaffotm.quizzify.resource.QuizConfiguration;
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
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.net.URI;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.OK;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Arquillian.class)
@RunAsClient
public class ImageEndpointIT {

    @Deployment(testable = false)
    public static Archive createDeployment() {
        final JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class);
        deployment.addPackages(true, "fr.chaffotm");
        deployment.addAsResource("META-INF/persistence.xml");
        deployment.addAsResource("META-INF/sql/create.sql");
        deployment.addAsResource("META-INF/sql/data.sql");
        deployment.addAsResource("META-INF/sql/drop.sql");
        deployment.addAsResource("project-defaults-test.yml", "project-defaults.yml");
        deployment.addAsResource("silhouettes");
        return deployment;
    }

    @ArquillianResource
    private URI baseURL;

    @Test
    public void getImage_should_retrieve_the_correct_image() {
        final QuizConfiguration configuration = new QuizConfiguration();
        configuration.setAnswerType(AnswerType.MULTIPLE_CHOICE);
        configuration.setQuestionType("SILHOUETTE");

        final Client client = TestConfiguration.buildClient();
        WebTarget webTarget = client.target(baseURL).path("api/quizzes");

        final Response response = webTarget.request(APPLICATION_JSON_TYPE).post(Entity.json(configuration));
        ResponseAssert.assertThat(response)
                .hasStatus(CREATED)
                .hasNoBody();

        final String location = response.getHeaderString("location");
        final Response quizResponse = client.target(location).request(APPLICATION_JSON_TYPE).get();
        ResponseAssert.assertThat(quizResponse).hasStatus(OK);
        final Quiz quiz = quizResponse.readEntity(Quiz.class);
        assertThat(quiz.getQuestions())
                .hasSize(10);

        final Response imageResponse = client.target(quiz.getQuestions().get(0).getImagePath()).request("image/png").get();
        ResponseAssert.assertThat(imageResponse)
                .hasStatus(OK);
    }

}
