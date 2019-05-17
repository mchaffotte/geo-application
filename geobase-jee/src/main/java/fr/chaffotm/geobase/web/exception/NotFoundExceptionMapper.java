package fr.chaffotm.geobase.web.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    private static final Logger LOGGER = LoggerFactory.getLogger("Exceptions");

    @Override
    public Response toResponse(final NotFoundException e) {
        LOGGER.warn("NotFoundException occurs", e);
        return Response.status(Response.Status.NOT_FOUND).build();
    }

}
