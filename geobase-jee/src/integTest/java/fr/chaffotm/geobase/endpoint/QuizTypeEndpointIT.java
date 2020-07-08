package fr.chaffotm.geobase.endpoint;

import fr.chaffotm.quizzify.resource.AnswerType;
import fr.chaffotm.quizzify.resource.FilterType;
import fr.chaffotm.quizzify.resource.QuizType;
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
import java.util.List;

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

    private QuizType build(final String questionType, final FilterType filter, final AnswerType... answerTypes) {
        final QuizType type = new QuizType();
        type.setQuestionType(questionType);
        type.setAnswerTypes(List.of(answerTypes));
        type.setFilter(filter);
        return type;
    }

    @Test
    public void getQuizTypes_should_return_all_possible_types() {
        final FilterType filter = new FilterType();
        filter.setLabel("Region");
        filter.setName("region.id");
        filter.setValues(List.of());
        final Client client = TestConfiguration.buildClient();
        WebTarget webTarget = client.target(baseURL).path("api/quiz-types");
        Response response = webTarget.request(APPLICATION_JSON_TYPE).get();

        assertThat(response)
                .hasStatus(OK)
                .withBodyList(QuizType.class)
                .containsExactlyInAnyOrder(
                        build("CAPITAL", filter, AnswerType.ANSWER, AnswerType.MULTIPLE_CHOICE),
                        build("TOTAL_AREA", filter, AnswerType.MULTIPLE_CHOICE),
                        build("LAND_AREA", filter, AnswerType.MULTIPLE_CHOICE),
                        build("WATER_AREA", filter, AnswerType.MULTIPLE_CHOICE),
                        build("FLAG", filter, AnswerType.ANSWER, AnswerType.MULTIPLE_CHOICE),
                        build("SILHOUETTE", filter, AnswerType.ANSWER, AnswerType.MULTIPLE_CHOICE)
                );
    }

}
