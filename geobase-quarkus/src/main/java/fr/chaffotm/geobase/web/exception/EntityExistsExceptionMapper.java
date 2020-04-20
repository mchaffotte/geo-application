package fr.chaffotm.geobase.web.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityExistsException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class EntityExistsExceptionMapper implements ExceptionMapper<EntityExistsException> {

    private static final Logger LOGGER = LoggerFactory.getLogger("Exceptions");

    @Override
    public Response toResponse(final EntityExistsException e) {
        LOGGER.warn("EntityExistsException occurs", e);
        final ErrorBody errorBody = new ErrorBody();
        errorBody.setMessage(e.getMessage());
        return Response.status(Response.Status.CONFLICT).entity(errorBody).build();
    }

}
