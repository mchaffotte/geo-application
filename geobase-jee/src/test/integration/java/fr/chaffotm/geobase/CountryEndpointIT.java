package fr.chaffotm.geobase;

import fr.chaffotm.geobase.web.domain.City;
import fr.chaffotm.geobase.web.domain.Country;
import fr.chaffotm.geobase.web.domain.Frame;
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
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.net.URI;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Arquillian.class)
@RunAsClient
public class CountryEndpointIT {

    @Deployment(testable = false)
    public static Archive createDeployment() {
        System.setProperty("swarm.http.port", "8090");

        JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class);
        deployment.addPackages(true, "fr.chaffotm.geobase");
        deployment.addAsResource("META-INF/persistence-test.xml", "META-INF/persistence.xml");
        deployment.addAsResource("META-INF/sql/create.sql");
        deployment.addAsResource("META-INF/sql/data.sql");
        deployment.addAsResource("META-INF/sql/drop.sql");
        deployment.addAsResource("project-defaults.yml");
        return deployment;
    }

    @ArquillianResource
    private URI baseURL;

    @Test
    public void crud_Country() {
        final Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(baseURL).path("api/countries");

        Response response = webTarget.request(APPLICATION_JSON_TYPE)
                .get();
        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
        Frame<Country> frame = response.readEntity(new GenericType<Frame<Country>>() {});
        assertThat(frame.getTotal()).isEqualTo(0);
        assertThat(frame.getResources()).isEmpty();


        Country france = new Country();
        france.setName("France");
        france.setCode("FR");
        france.setTotalArea(465);
        france.setPopulation(12);
        City city = new City();
        city.setName("Paris");
        france.setCapital(city);
        response = webTarget.request(APPLICATION_JSON_TYPE).post(Entity.json(france));
        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.CREATED);
        final String location = response.getHeaderString("Location");
        assertThat(location).startsWith(baseURL + "api/countries/");
        String id = location.substring(location.lastIndexOf('/') + 1);
        france.setId(Long.valueOf(id));

        response = webTarget.path(id).request(APPLICATION_JSON_TYPE).get();
        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
        Country fr = response.readEntity(Country.class);
        assertThat(fr).isEqualTo(france);

        france.setPopulation(89796);
        response = webTarget.path(id).request(APPLICATION_JSON_TYPE).put(Entity.json(france));
        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
        Country fre = response.readEntity(Country.class);
        assertThat(fre.getPopulation()).isEqualTo(89796);

        response = webTarget.path(id).request(APPLICATION_JSON_TYPE).delete();
        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.NO_CONTENT);
        response = webTarget.path(id).request(APPLICATION_JSON_TYPE).delete();
        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.NOT_FOUND);
    }

}
