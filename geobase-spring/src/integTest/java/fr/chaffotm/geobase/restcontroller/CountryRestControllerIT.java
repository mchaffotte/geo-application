package fr.chaffotm.geobase.restcontroller;

import fr.chaffotm.geobase.interceptor.JsonInterceptor;
import fr.chaffotm.geobase.interceptor.LoggingInterceptor;
import fr.chaffotm.geobase.web.Frame;
import fr.chaffotm.geobase.web.domain.Area;
import fr.chaffotm.geobase.web.domain.City;
import fr.chaffotm.geobase.web.domain.Country;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CountryRestControllerIT {

    private static final String API_COUNTRIES = "/api/countries";

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setUp() {
        restTemplate.getRestTemplate().setInterceptors(Arrays.asList(new LoggingInterceptor(), new JsonInterceptor()));
    }

    @Test
    public void getCountries_should_return_the_all_list() {
        ResponseEntity<Frame<Country>> response = restTemplate.exchange(API_COUNTRIES, HttpMethod.GET, null, new ParameterizedTypeReference<Frame<Country>>() {});

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getResources()).hasSize(45);
    }

    @Test
    public void crud_Country() {
        ResponseEntity<Frame<Country>> response = restTemplate.exchange(API_COUNTRIES, HttpMethod.GET, null, new ParameterizedTypeReference<Frame<Country>>() {});

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getResources()).hasSize(45);

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
        final ResponseEntity<Void> response1 = restTemplate.postForEntity(API_COUNTRIES, france, Void.class);
        assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        final URI location = response1.getHeaders().getLocation();

        String id = location.getPath().substring(location.getPath().lastIndexOf('/') + 1);
        france.setId(Long.valueOf(id));

        final ResponseEntity<Country> responseEntity = restTemplate.getForEntity(location, Country.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        final Country fr = responseEntity.getBody();
        assertThat(fr).isEqualTo(france);

        fr.getArea().setWater(15);
        final ResponseEntity<Country> exchange = restTemplate.exchange(location, HttpMethod.PUT, new HttpEntity<>(fr), Country.class);
        assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.OK);
        final Country fre = exchange.getBody();
        assertThat(fre.getArea().getTotal()).isEqualTo(60);

        ResponseEntity<Void> delete = restTemplate.exchange(location, HttpMethod.DELETE, null, Void.class);
        assertThat(delete.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        delete = restTemplate.exchange(location, HttpMethod.DELETE, null, Void.class);
        assertThat(delete.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

}
