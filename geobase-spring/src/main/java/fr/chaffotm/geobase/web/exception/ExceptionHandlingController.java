package fr.chaffotm.geobase.web.exception;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@ControllerAdvice
public class ExceptionHandlingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlingController.class);

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<BadRequestBody> handleException(IllegalArgumentException ex) {
        LOGGER.warn(ex.getMessage(), ex);
        final BadRequestBody entity = new BadRequestBody();
        entity.addMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(entity);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BadRequestBody> handleException(final MethodArgumentNotValidException ex) {
        LOGGER.warn("Validation failed", ex);
        final BadRequestBody entity = new BadRequestBody();
        final BindingResult bindingResult = ex.getBindingResult();
        final List<ObjectError> errors = bindingResult.getGlobalErrors();
        for (ObjectError error : errors) {
            entity.addMessage(error.getDefaultMessage());
        }
        final List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            StringBuilder builder = new StringBuilder();
            final ConstraintViolationImpl unwrap = fieldError.unwrap(ConstraintViolationImpl.class);
            if (unwrap.getMessageTemplate().startsWith("{javax.validation.constraints.")) {
                builder.append(fieldError.getField()).append(" ");
            }
            builder.append(fieldError.getDefaultMessage());
            entity.addMessage(builder.toString());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(entity);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Void> handleException(final EntityNotFoundException ex) {
        LOGGER.warn(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<ErrorBody> handleException(final EntityExistsException ex) {
        LOGGER.warn(ex.getMessage(), ex);
        final ErrorBody entity = new ErrorBody();
        entity.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(entity);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity handleException(final HttpMessageNotReadableException ex) {
        LOGGER.warn(ex.getMessage(), ex);
        if (ex.getMessage().startsWith("Required request body is missing")
                || MismatchedInputException.class.equals(ex.getCause().getClass())) {
            final BadRequestBody entity = new BadRequestBody();
            entity.addMessage("Required request body is missing");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(entity);
        }
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorBody> handleException(final Exception ex) {
        LOGGER.error("exception occurs", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
