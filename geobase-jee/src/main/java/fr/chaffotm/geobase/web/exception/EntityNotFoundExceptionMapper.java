package fr.chaffotm.geobase.web.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityNotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class EntityNotFoundExceptionMapper implements ExceptionMapper<EntityNotFoundException> {

    private static final Logger LOGGER = LoggerFactory.getLogger("Exceptions");

    @Override
    public Response toResponse(final EntityNotFoundException e) {
        LOGGER.warn("EntityNotFoundException occurs", e);
        return Response.status(Response.Status.NOT_FOUND).build();
    }

}
