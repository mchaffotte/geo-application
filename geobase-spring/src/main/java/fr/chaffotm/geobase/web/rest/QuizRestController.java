package fr.chaffotm.geobase.web.rest;

import fr.chaffotm.geobase.service.QuizService;
import fr.chaffotm.geoquiz.resource.Quiz;
import fr.chaffotm.geoquiz.resource.QuizAnswer;
import fr.chaffotm.geoquiz.resource.QuizConfiguration;
import fr.chaffotm.geoquiz.resource.QuizResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/quizzes")
public class QuizRestController {

    private final QuizService quizService;

    public QuizRestController(final QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody(required = false) @Valid QuizConfiguration configuration) {
        final long quizId = quizService.create(configuration);
        final URI location = ServletUriComponentsBuilder
                .fromCurrentServletMapping().path("/api/quizzes/{id}").build()
                .expand(quizId).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    public Quiz get(@PathVariable("id") final long id) {
        return quizService.get(id, ServletUriComponentsBuilder
                .fromCurrentServletMapping().path("/api").build().toUriString());
    }

    @PutMapping(value = "{id}")
    public QuizResult answer(@PathVariable("id") final long id, @RequestBody @Valid final QuizAnswer quizAnswer) {
        return quizService.answer(id, quizAnswer);
    }

}
