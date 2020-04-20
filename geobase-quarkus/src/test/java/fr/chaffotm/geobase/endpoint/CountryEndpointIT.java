package fr.chaffotm.geobase.endpoint;

import fr.chaffotm.geobase.assertion.ResponseAssert;
import fr.chaffotm.geodata.resource.Area;
import fr.chaffotm.geodata.resource.City;
import fr.chaffotm.geodata.resource.Country;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
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

    @Test
    public void crud_Country() {
        final Client client = TestConfiguration.buildClient();
        WebTarget webTarget = client.target(baseURL).path("api/countries");

        Response response = webTarget.request(APPLICATION_JSON_TYPE).get();
        ResponseAssert.assertThat(response)
                .hasStatus(OK)
                .withBodyFrame(Country.class)
                .extracting("total").isEqualTo(45L);

        final Area area = new Area();
        area.setLand(45.0);
        area.setWater(20.1);
        final Country france = new Country();
        france.setName("France");
        france.setCode("FR1");
        france.setArea(area);
        City city = new City();
        city.setName("Paris");
        france.setCapital(city);
        response = webTarget.request(APPLICATION_JSON_TYPE).post(Entity.json(france));
        ResponseAssert.assertThat(response)
                .hasStatus(CREATED)
                .hasNoBody();

        final String location = response.getHeaderString("Location");
        assertThat(location).startsWith(baseURL + "/api/countries/");
        String id = location.substring(location.lastIndexOf('/') + 1);
        france.setId(Long.valueOf(id));

        response = webTarget.path(id).request(APPLICATION_JSON_TYPE).get();
        assertThat(response.getStatusInfo()).isEqualTo(OK);
        final Country fr = response.readEntity(Country.class);
        assertThat(fr).isEqualTo(france);

        fr.getArea().setWater(15);
        response = webTarget.path(id).request(APPLICATION_JSON_TYPE).put(Entity.json(fr));
        assertThat(response.getStatusInfo()).isEqualTo(OK);
        final Country fre = response.readEntity(Country.class);
        assertThat(fre.getArea().getTotal()).isEqualTo(60);

        response = webTarget.path(id).request(APPLICATION_JSON_TYPE).delete();
        ResponseAssert.assertThat(response)
                .hasStatus(NO_CONTENT);
        response = webTarget.path(id).request(APPLICATION_JSON_TYPE).delete();
        ResponseAssert.assertThat(response)
                .hasStatus(NOT_FOUND)
                .hasNoBody();
    }

}
