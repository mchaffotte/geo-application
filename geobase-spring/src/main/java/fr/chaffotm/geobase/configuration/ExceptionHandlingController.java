package fr.chaffotm.geobase.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class ExceptionHandlingController {

    private Logger log = LoggerFactory.getLogger(ExceptionHandlingController.class);

    @ExceptionHandler(EntityExistsException.class)
    public void handleEntityExistsException(EntityExistsException ex, HttpServletResponse response) {
        handleResponse(HttpServletResponse.SC_CONFLICT, ex, response);

        log.warn(ex.getMessage());
        response.setStatus(HttpServletResponse.SC_CONFLICT);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public void handleEntityNotFoundException(EntityNotFoundException ex, HttpServletResponse response) {
        handleResponse(HttpServletResponse.SC_NOT_FOUND, ex, response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public void handleIllegalArgumentException(IllegalArgumentException ex, HttpServletResponse response) {
        handleResponse(HttpServletResponse.SC_BAD_REQUEST, ex, response);
    }

    private void handleResponse(int sc, Exception ex, HttpServletResponse response) {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        log.warn(ex.getMessage());
        response.setStatus(sc);
    }

}
