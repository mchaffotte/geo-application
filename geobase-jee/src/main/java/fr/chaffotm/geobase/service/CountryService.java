package fr.chaffotm.geobase.service;

import fr.chaffotm.geobase.domain.Country;
import fr.chaffotm.geobase.repository.CountryRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.List;

@RequestScoped
public class CountryService {

    private final CountryRepository countryRepository;

    // Weld or CDI issue :(
    protected CountryService() {
        this(null);
    }

    @Inject
    public CountryService(final CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public List<Country> findAll() {
        return countryRepository.findAll();
    }

}
