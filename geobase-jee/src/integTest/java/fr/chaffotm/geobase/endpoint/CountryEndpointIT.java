package fr.chaffotm.geobase.endpoint;

import fr.chaffotm.geobase.assertion.ResponseAssert;
import fr.chaffotm.geobase.web.resource.Frame;
import fr.chaffotm.geodata.resource.Area;
import fr.chaffotm.geodata.resource.City;
import fr.chaffotm.geodata.resource.Country;
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
import static javax.ws.rs.core.Response.Status.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Arquillian.class)
@RunAsClient
public class CountryEndpointIT {

    @Deployment(testable = false)
    public static Archive createDeployment() {
        final JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class);
        deployment.addPackages(true, "fr.chaffotm");
        deployment.addAsResource("META-INF/persistence-test.xml", "META-INF/persistence.xml");
        deployment.addAsResource("META-INF/sql/create.sql");
        deployment.addAsResource("META-INF/sql/data.sql");
        deployment.addAsResource("META-INF/sql/drop.sql");
        deployment.addAsResource("project-defaults-test.yml", "project-defaults.yml");
        return deployment;
    }

    @ArquillianResource
    private URI baseURL;

    @Test
    public void crud_Country() {
        final Client client = TestConfiguration.buildClient();
        WebTarget webTarget = client.target(baseURL).path("api/countries");
        Frame<Country> countries = new Frame<>();

        Response response = webTarget.request(APPLICATION_JSON_TYPE).get();
        ResponseAssert.assertThat(response)
                .hasStatus(OK)
                .withBodyFrame(Country.class)
                .isEqualTo(countries);

        final Area area = new Area();
        area.setLand(303815);
        area.setWater(3433);
        final Country finland = new Country();
        finland.setName("Finland");
        finland.setCode("FI");
        finland.setArea(area);
        City city = new City();
        city.setName("Helsinki");
        finland.setCapital(city);
        response = webTarget.request(APPLICATION_JSON_TYPE).post(Entity.json(finland));
        ResponseAssert.assertThat(response)
                .hasStatus(CREATED)
                .hasNoBody();

        final String location = response.getHeaderString("Location");
        assertThat(location).startsWith(baseURL + "api/countries/");
        String id = location.substring(location.lastIndexOf('/') + 1);
        finland.setId(Long.valueOf(id));

        response = webTarget.path(id).request(APPLICATION_JSON_TYPE).get();
        assertThat(response.getStatusInfo()).isEqualTo(OK);
        final Country suomi = response.readEntity(Country.class);
        assertThat(suomi).isEqualTo(finland);

        suomi.getArea().setWater(34330);
        response = webTarget.path(id).request(APPLICATION_JSON_TYPE).put(Entity.json(suomi));
        assertThat(response.getStatusInfo()).isEqualTo(OK);
        final Country fi = response.readEntity(Country.class);
        assertThat(fi.getArea().getTotal()).isEqualTo(338145);

        response = webTarget.path(id).request(APPLICATION_JSON_TYPE).delete();
        ResponseAssert.assertThat(response)
                .hasStatus(NO_CONTENT);
        response = webTarget.path(id).request(APPLICATION_JSON_TYPE).delete();
        ResponseAssert.assertThat(response)
                .hasStatus(NOT_FOUND)
                .hasNoBody();
    }

}
