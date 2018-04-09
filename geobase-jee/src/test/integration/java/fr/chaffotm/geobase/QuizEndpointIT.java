package fr.chaffotm.geobase;

import fr.chaffotm.geobase.web.domain.*;
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
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import java.net.URI;
import java.util.Arrays;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Arquillian.class)
@RunAsClient
public class QuizEndpointIT {

    @Deployment(testable = false)
    public static Archive createDeployment() {
        System.setProperty("swarm.http.port", "8090");

        JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class);
        deployment.addPackages(true, "fr.chaffotm.geobase");
        deployment.addAsResource("META-INF/persistence.xml");
        deployment.addAsResource("META-INF/sql/create.sql");
        deployment.addAsResource("META-INF/sql/data.sql");
        deployment.addAsResource("META-INF/sql/drop.sql");
        deployment.addAsResource("project-defaults.yml");
        return deployment;
    }

    @ArquillianResource
    private URI baseURL;

    @Test
    public void answerQuiz_should_check_user_answers() {
        final Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(baseURL).path("api/quizzes");
        Response response = webTarget.request(APPLICATION_JSON_TYPE).post(Entity.json(null));

        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.CREATED);
        final String location = response.getHeaderString("location");

        final Response response1 = client.target(location).request(APPLICATION_JSON_TYPE).get();
        assertThat(response1.getStatusInfo()).isEqualTo(Response.Status.OK);
        final Quiz quiz = response1.readEntity(Quiz.class);
        assertThat(quiz).isNotNull();
        assertThat(quiz.getQuestions()).hasSize(10);

        final QuizAnswers answers = new QuizAnswers();
        answers.setAnswers(Arrays.asList("", "", "", "", "", "", "", "", "", ""));
        final Response response2 = webTarget.path(String.valueOf(quiz.getId())).request(APPLICATION_JSON_TYPE).put(Entity.json(answers));
        assertThat(response2.getStatusInfo()).isEqualTo(Response.Status.OK);

        final QuizResult expected = new QuizResult();
        expected.setNbOfCorrectAnswers(0);
        expected.setNbOfQuestions(10);
        final QuizResult result = response2.readEntity(QuizResult.class);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void answerQuiz_should_check_user_answers_using_images() {
        final Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(baseURL).path("api/quizzes");
        final QuizConfiguration configuration = new QuizConfiguration();
        configuration.setMultipleChoice(true);
        configuration.setQuestionType(QuestionType.FLAG);
        Response response = webTarget.request(APPLICATION_JSON_TYPE).post(Entity.json(configuration));

        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.CREATED);
        final String location = response.getHeaderString("location");

        final Response response1 = client.target(location).request(APPLICATION_JSON_TYPE).get();
        assertThat(response1.getStatusInfo()).isEqualTo(Response.Status.OK);
        final Quiz quiz = response1.readEntity(Quiz.class);
        assertThat(quiz).isNotNull();
        assertThat(quiz.getQuestions()).hasSize(10);

        final QuizAnswers answers = new QuizAnswers();
        answers.setAnswers(Arrays.asList("", "", "", "", "", "", "", "", "", ""));
        final Response response2 = webTarget.path(String.valueOf(quiz.getId())).request(APPLICATION_JSON_TYPE).put(Entity.json(answers));
        assertThat(response2.getStatusInfo()).isEqualTo(Response.Status.OK);

        final QuizResult expected = new QuizResult();
        expected.setNbOfCorrectAnswers(0);
        expected.setNbOfQuestions(10);
        final QuizResult result = response2.readEntity(QuizResult.class);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void answerQuiz_should_not_recognize_null_body() {
        final Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(baseURL).path("api/quizzes");

        final Response response = webTarget.path("45").request(APPLICATION_JSON_TYPE).put(null);
        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.UNSUPPORTED_MEDIA_TYPE);
    }

    @Test
    public void answerQuiz_should_not_validate_null_body() {
        final Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(baseURL).path("api/quizzes");

        final Response response = webTarget.path("45").request(APPLICATION_JSON_TYPE).put(Entity.json(null));
        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.BAD_REQUEST);
    }

    @Test
    public void answerQuiz_should_not_validate_empty_body() {
        final Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(baseURL).path("api/quizzes");

        final Response response = webTarget.path("45").request(APPLICATION_JSON_TYPE).put(Entity.json(""));
        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.BAD_REQUEST);
    }

}
