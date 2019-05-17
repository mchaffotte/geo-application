package fr.chaffotm.geobase.web.rest;

import fr.chaffotm.geobase.web.resource.Todo;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

@RestController
@RequestMapping(
        value = "/api/exceptions"
)
public class ExceptionRestController {

    @GetMapping("exception")
    public void getException() throws Exception {
        throw new Exception();
    }

    @GetMapping("runtime-exception")
    public void getRuntimeException() {
        throw new RuntimeException("It does not work");
    }

    @GetMapping("illegal-argument-exception")
    public void getIllegalArgumentException() {
        throw new IllegalArgumentException("wrong argument");
    }

    @GetMapping("entity-exists-exception")
    public void getEntityExistsException() {
        throw new EntityExistsException("resource with id 5 does not exist");
    }

    @GetMapping("entity-not-found-exception")
    public void getEntityNotFoundException() {
        throw new EntityNotFoundException();
    }

    @GetMapping("not-found-exception")
    public void getResourceNotFoundException() {
        throw new ResourceNotFoundException();
    }

    @PostMapping("todos")
    public void createTodo(@RequestBody @Valid final Todo todo) {
    }

    @GetMapping("todos/{id}")
    public Todo getTodo(@PathVariable("id") final long id) {
        return new Todo();
    }

}
