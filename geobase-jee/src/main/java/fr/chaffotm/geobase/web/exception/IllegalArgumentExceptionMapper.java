package fr.chaffotm.geobase.web.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class IllegalArgumentExceptionMapper implements ExceptionMapper<IllegalArgumentException> {

    @Override
    public Response toResponse(final IllegalArgumentException e) {
        e.printStackTrace();
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

}
