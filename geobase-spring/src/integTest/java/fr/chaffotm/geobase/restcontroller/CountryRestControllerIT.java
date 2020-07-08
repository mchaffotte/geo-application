package fr.chaffotm.geobase.restcontroller;

import fr.chaffotm.geobase.interceptor.JsonInterceptor;
import fr.chaffotm.geobase.interceptor.LoggingInterceptor;
import fr.chaffotm.geobase.web.resource.Frame;
import fr.chaffotm.geodata.resource.Area;
import fr.chaffotm.geodata.resource.City;
import fr.chaffotm.geodata.resource.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CountryRestControllerIT {

    private static final String API_COUNTRIES = "/api/countries";

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        restTemplate.getRestTemplate().setInterceptors(
                List.of(new LoggingInterceptor(), new JsonInterceptor())
        );
    }

    @Test
    @DisplayName("getCountries should return the list of all countries")
    public void getCountriesShouldReturnTheListOfAllCountries() {
        ResponseEntity<Frame<Country>> response = restTemplate.exchange(API_COUNTRIES, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getResources()).hasSize(14);
    }

    @Test
    @DisplayName("crud the country")
    public void crudTheCountry() {
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
        final ResponseEntity<Void> response1 = restTemplate.postForEntity(API_COUNTRIES, finland, Void.class);
        assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        final URI location = response1.getHeaders().getLocation();

        String id = location.getPath().substring(location.getPath().lastIndexOf('/') + 1);
        finland.setId(Long.valueOf(id));

        final ResponseEntity<Country> responseEntity = restTemplate.getForEntity(location, Country.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        final Country fr = responseEntity.getBody();
        assertThat(fr).isEqualTo(finland);

        fr.getArea().setWater(34330);
        final ResponseEntity<Country> exchange = restTemplate.exchange(location, HttpMethod.PUT, new HttpEntity<>(fr), Country.class);
        assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.OK);
        final Country fi = exchange.getBody();
        assertThat(fi.getArea().getTotal()).isEqualTo(338145);

        ResponseEntity<Void> delete = restTemplate.exchange(location, HttpMethod.DELETE, null, Void.class);
        assertThat(delete.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        delete = restTemplate.exchange(location, HttpMethod.DELETE, null, Void.class);
        assertThat(delete.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

}
