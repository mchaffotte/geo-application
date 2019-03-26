package fr.chaffotm.geobase.endpoint;

import fr.chaffotm.geoquiz.resource.AnswerType;
import fr.chaffotm.geoquiz.resource.QuestionType;
import fr.chaffotm.geoquiz.resource.QuizType;
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
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Arrays;

import static fr.chaffotm.geobase.assertion.ResponseAssert.assertThat;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static javax.ws.rs.core.Response.Status.OK;

@RunWith(Arquillian.class)
@RunAsClient
public class QuizTypeEndpointIT {

    @Deployment(testable = false)
    public static Archive createDeployment() {
        final JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class);
        deployment.addPackages(true, "fr.chaffotm");
        deployment.addAsResource("META-INF/persistence.xml");
        deployment.addAsResource("META-INF/sql/create.sql");
        deployment.addAsResource("META-INF/sql/data.sql");
        deployment.addAsResource("META-INF/sql/drop.sql");
        deployment.addAsResource("project-defaults-test.yml", "project-defaults.yml");
        return deployment;
    }

    @ArquillianResource
    private URI baseURL;

    private QuizType build(final QuestionType questionType, final AnswerType... answerTypes) {
        final QuizType type = new QuizType();
        type.setQuestionType(questionType);
        type.setAnswerTypes(Arrays.asList(answerTypes));
        return type;
    }

    @Test
    public void getQuizTypes_should_return_all_possible_types() {
        final Client client = TestConfiguration.buildClient();
        WebTarget webTarget = client.target(baseURL).path("api/quiz-types");
        Response response = webTarget.request(APPLICATION_JSON_TYPE).get();

        assertThat(response)
                .hasStatus(OK)
                .withBodyList(QuizType.class)
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
