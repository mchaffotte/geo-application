package fr.chaffotm.geobase.web.rest;

import javax.persistence.EntityExistsException;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("exceptions")
public class ExceptionEndpoint implements ResourceEndpoint {

    @GET
    @Path("exception")
    public void getException() throws Exception {
        throw new Exception();
    }

    @GET
    @Path("runtime-exception")
    public void RuntimeException() {
        throw new RuntimeException();
    }

    @GET
    @Path("illegal-argument-exception")
    public void getIllegalArgumentException() {
        throw new IllegalArgumentException();
    }

    @GET
    @Path("entity-exists-exception")
    public void getEntityExistsException() {
        throw new EntityExistsException();
    }

    @GET
    @Path("constraint-violation-exception")
    public void getValidationException() {
        throw new ConstraintViolationException("name", null);
    }

}
