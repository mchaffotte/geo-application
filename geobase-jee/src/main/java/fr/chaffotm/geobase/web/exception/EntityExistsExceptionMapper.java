package fr.chaffotm.geobase.web.exception;

import javax.persistence.EntityExistsException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class EntityExistsExceptionMapper implements ExceptionMapper<EntityExistsException> {

    @Override
    public Response toResponse(final EntityExistsException e) {
        return Response.status(Response.Status.CONFLICT).build();
    }

}
