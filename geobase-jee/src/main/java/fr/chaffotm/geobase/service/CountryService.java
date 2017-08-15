package fr.chaffotm.geobase.service;

import fr.chaffotm.geobase.domain.CountryEntity;
import fr.chaffotm.geobase.mapper.CountryMapper;
import fr.chaffotm.geobase.repository.CountryRepository;
import fr.chaffotm.geobase.web.domain.Country;
import fr.chaffotm.geobase.web.domain.Frame;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@RequestScoped
@Transactional
public class CountryService {

    private final CountryRepository countryRepository;

    // Used by CDI
    protected CountryService() {
        this(null);
    }

    @Inject
    public CountryService(final CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public Frame<Country> findAll(final int offset, final Integer limit) {
        final List<CountryEntity> countries = countryRepository.findAll(offset, limit);
        final long nbElements = countryRepository.count();
        return new Frame<>(CountryMapper.map(countries), nbElements);
    }

    public long create(final Country country) {
        final CountryEntity entity = CountryMapper.unMap(country);
        return countryRepository.create(entity).getId();
    }

    public Country get(final long id) {
        final CountryEntity entity = countryRepository.get(id);
        return CountryMapper.map(entity);
    }

    public Country update(final long id, final Country country) {
        final CountryEntity update = CountryMapper.unMap(country);
        final CountryEntity entity = countryRepository.update(id, update);
        return CountryMapper.map(entity);
    }

    public void delete(final long id) {
        countryRepository.delete(id);
    }

}
