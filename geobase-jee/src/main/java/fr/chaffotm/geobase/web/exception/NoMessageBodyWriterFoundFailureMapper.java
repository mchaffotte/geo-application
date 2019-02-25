package fr.chaffotm.geobase.web.exception;

import org.jboss.resteasy.core.NoMessageBodyWriterFoundFailure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NoMessageBodyWriterFoundFailureMapper  implements ExceptionMapper<NoMessageBodyWriterFoundFailure> {

    private static final Logger LOGGER = LoggerFactory.getLogger("Exceptions");

    @Override
    public Response toResponse(final NoMessageBodyWriterFoundFailure e) {
        LOGGER.info("Media type not supported: ", e);
        return Response.status(Response.Status.NOT_ACCEPTABLE).build();
    }

}