package fr.chaffotm.geobase.web.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.NotSupportedException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotSupportedExceptionMapper implements ExceptionMapper<NotSupportedException> {

    private static final Logger LOGGER = LoggerFactory.getLogger("Exceptions");

    @Override
    public Response toResponse(final NotSupportedException e) {
        LOGGER.info("exception occurs: ", e);
        return Response.status(Response.Status.UNSUPPORTED_MEDIA_TYPE).build();
    }

}
