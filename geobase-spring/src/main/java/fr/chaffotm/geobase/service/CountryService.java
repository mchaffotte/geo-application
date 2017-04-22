package fr.chaffotm.geobase.service;

import fr.chaffotm.geobase.model.Country;
import fr.chaffotm.geobase.repository.CountryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CountryService {

    private final CountryRepository countryRepository;

    public CountryService(final CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public Page<Country> findAll(final Pageable pageable) {
        return countryRepository.findAll(pageable);
    }

}
