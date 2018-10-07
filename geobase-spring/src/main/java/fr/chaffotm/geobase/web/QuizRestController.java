package fr.chaffotm.geobase.web;

import fr.chaffotm.geobase.service.QuizService;
import fr.chaffotm.geobase.web.domain.Quiz;
import fr.chaffotm.geobase.web.domain.QuizAnswers;
import fr.chaffotm.geobase.web.domain.QuizConfiguration;
import fr.chaffotm.geobase.web.domain.QuizResult;
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
    public QuizResult answer(@PathVariable("id") final long id, @RequestBody final QuizAnswers answers) {
        return quizService.answer(id, answers);
    }

}
