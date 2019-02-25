package fr.chaffotm.geobase.web.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ValidationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {

    private static final Logger LOGGER = LoggerFactory.getLogger("Exceptions");

    @Override
    public Response toResponse(final ValidationException e) {
        LOGGER.warn("ValidationException occurs: ", e);
        final BadRequestBody body = new BadRequestBody();
       body.addMessage(e.getMessage());
       return Response.status(Response.Status.BAD_REQUEST).entity(body).build();
   }

}
