package fr.chaffotm.geobase.endpoint;

import fr.chaffotm.geobase.assertion.ResponseAssert;
import fr.chaffotm.geodata.resource.Area;
import fr.chaffotm.geodata.resource.City;
import fr.chaffotm.geodata.resource.Country;
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

import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static javax.ws.rs.core.Response.Status.*;
import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
public class CountryEndpointIT {

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
    public void crud_Country() {
        WebTarget webTarget = client.target(baseURL).path("api/countries");

        Response response = webTarget.request(APPLICATION_JSON_TYPE).get();
        ResponseAssert.assertThat(response)
                .hasStatus(OK)
                .withBodyFrame(Country.class)
                .extracting("total").isEqualTo(14L);

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
        assertThat(location).startsWith(baseURL + "/api/countries/");
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
