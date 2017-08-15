package fr.chaffotm.geobase.web.exception;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Set;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(final ConstraintViolationException exception) {
       final Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();
       final StringBuilder messageBuilder = new StringBuilder();
       for (ConstraintViolation<?> constraintViolation : constraintViolations) {
           messageBuilder.append(constraintViolation.getMessage());
       }
       return Response.status(422).entity(Entity.json(messageBuilder.toString())).build();
   }

}
