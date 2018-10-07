package fr.chaffotm.geobase.web;

import fr.chaffotm.geobase.service.CountryService;
import fr.chaffotm.geobase.web.domain.Country;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.net.URI;

@RestController
@RequestMapping("/api/countries")
public class CountryRestController {

    private final CountryService countryService;

    public CountryRestController(final CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping
    public Frame<Country> findAll(@RequestParam(value = "offset", defaultValue = "1") @Min(value = 1, message = "offset cannot be less than 1") final int offset,
                                  @RequestParam(value = "limit", required = false) final Integer limit,
                                  @RequestParam(value = "sort", defaultValue = "id") final String sort) {
        return countryService.findAll(offset, limit, sort);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody @Valid final Country country) {
        final long countryId = countryService.create(country);
         final URI location = ServletUriComponentsBuilder
                .fromCurrentServletMapping().path("/api/countries/{id}").build()
                .expand(countryId).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    public Country get(@PathVariable("id") final long id) {
        return countryService.get(id);
    }

    @PutMapping("{id}")
    public Country update(@PathVariable("id") long id, @RequestBody @Valid final Country country) {
        return countryService.update(id, country);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") final long id) {
        countryService.delete(id);
        return new ResponseEntity<>(new HttpHeaders(), HttpStatus.NO_CONTENT);
    }

}
