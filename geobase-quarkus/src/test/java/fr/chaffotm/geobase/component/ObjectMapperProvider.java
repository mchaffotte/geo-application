package fr.chaffotm.geobase.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ObjectMapperProvider extends JacksonJsonProvider {

    public ObjectMapperProvider(ObjectMapper mapper) {
        super(mapper);
    }

}
