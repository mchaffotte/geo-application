package fr.chaffotm.geobase.web.exception;

import javax.persistence.EntityNotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class EntityNotFoundExceptionMapper implements ExceptionMapper<EntityNotFoundException> {

    @Override
    public Response toResponse(final EntityNotFoundException e) {
        return Response.status(Response.Status.NOT_FOUND).build();
    }

}
