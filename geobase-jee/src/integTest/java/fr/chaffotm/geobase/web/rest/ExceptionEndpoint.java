package fr.chaffotm.geobase.web.rest;

import fr.chaffotm.geobase.web.rest.domain.Todo;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.ws.rs.*;

@Path("exceptions")
public class ExceptionEndpoint implements ResourceEndpoint {

    @GET
    @Path("exception")
    public void getException() throws Exception {
        throw new Exception();
    }

    @GET
    @Path("runtime-exception")
    public void getRuntimeException() {
        throw new RuntimeException("It does not work");
    }

    @GET
    @Path("illegal-argument-exception")
    public void getIllegalArgumentException() {
        throw new IllegalArgumentException("wrong argument");
    }

    @GET
    @Path("entity-exists-exception")
    public void getEntityExistsException() {
        throw new EntityExistsException("resource with id 5 does not exist");
    }

    @GET
    @Path("entity-not-found-exception")
    public void getEntityNotFoundException() {
        throw new EntityNotFoundException();
    }

    @GET
    @Path("not-found-exception")
    public void getNotFoundException() {
        throw new NotFoundException("resource does not exist");
    }

    @POST
    @Path("todos")
    public void createTodo(@Valid final Todo todo) {
    }

    @GET
    @Path("todos/{id}")
    public Todo getTodo(@PathParam("id") final long id) {
        return new Todo();
    }

}
