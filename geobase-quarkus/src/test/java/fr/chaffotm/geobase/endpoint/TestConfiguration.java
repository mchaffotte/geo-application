package fr.chaffotm.geobase.endpoint;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import fr.chaffotm.geobase.component.ClientLoggerFilter;
import fr.chaffotm.geobase.component.ObjectMapperProvider;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

public class TestConfiguration {

    public static Client buildClient() {
        final ObjectMapper mapper = new ObjectMapper()
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
                        SerializationFeature.FAIL_ON_EMPTY_BEANS)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .registerModule(new JavaTimeModule());
        return ClientBuilder.newBuilder()
                .register(new ObjectMapperProvider(mapper))
                .register(new ClientLoggerFilter())
                .build();
    }

}
