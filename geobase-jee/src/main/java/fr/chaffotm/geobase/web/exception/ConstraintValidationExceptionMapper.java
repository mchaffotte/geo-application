package fr.chaffotm.geobase.web.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Set;

@Provider
public class ConstraintValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    private static final Logger LOGGER = LoggerFactory.getLogger("Exceptions");

    @Override
    public Response toResponse(final ConstraintViolationException exception) {
        LOGGER.info("Validation fails", exception);
        final Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();
        final BadRequestBody body = new BadRequestBody();
        if (constraintViolations != null) {
            for (ConstraintViolation<?> constraintViolation : constraintViolations) {
                StringBuilder builder = new StringBuilder();
                if (constraintViolation.getMessageTemplate().startsWith("{javax.validation.constraints.")) {
                    String propertyName = "";
                    for (Path.Node node : constraintViolation.getPropertyPath()) {
                        propertyName = node.getName();
                    }
                    builder.append(propertyName).append(" ");
                }
                builder.append(constraintViolation.getMessage());
                body.addMessage(builder.toString());
            }
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(body).build();
    }

}
