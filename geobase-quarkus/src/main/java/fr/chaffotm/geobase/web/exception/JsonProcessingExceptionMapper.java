package fr.chaffotm.geobase.web.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class JsonProcessingExceptionMapper implements ExceptionMapper<JsonProcessingException> {

    private static final Logger LOGGER = LoggerFactory.getLogger("Exceptions");

    @Override
    public Response toResponse(final JsonProcessingException e) {
        LOGGER.warn("JsonProcessingException occurs", e);
        final BadRequestBody body = new BadRequestBody();
        body.addMessage("Required request body is missing");
        return Response.status(Response.Status.BAD_REQUEST).entity(body).build();
    }

}
