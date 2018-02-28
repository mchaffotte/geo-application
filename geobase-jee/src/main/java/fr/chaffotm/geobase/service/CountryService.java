package fr.chaffotm.geobase.service;

import fr.chaffotm.geobase.domain.CountryEntity;
import fr.chaffotm.geobase.mapper.CountryMapper;
import fr.chaffotm.geobase.repository.CountryRepository;
import fr.chaffotm.geobase.repository.Sort;
import fr.chaffotm.geobase.repository.SortConverter;
import fr.chaffotm.geobase.web.domain.Country;
import fr.chaffotm.geobase.web.domain.Frame;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RequestScoped
@Transactional
public class CountryService {

    private final CountryRepository countryRepository;

    private final SortConverter converter;

    // Used by CDI
    protected CountryService() {
        this(null);
    }

    @Inject
    public CountryService(final CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
        converter = new SortConverter();
    }

    public Frame<Country> findAll(final int offset, final Integer limit, final String sort) {
        final List<Sort> sorts = converter.getAsList(sort);
        final List<CountryEntity> countries = countryRepository.findAll(offset, limit, sorts);
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

    public Country getRandom(final List<Long> excludedIds) {
        final long count = countryRepository.count();
        final long countryId = getRandomId(count, excludedIds);
        return get(countryId);
    }

    private long getRandomId(long count, final List<Long> excludedIds) {
        long countryId;
        do {
            countryId = ThreadLocalRandom.current().nextLong(count) + 1;
        } while (excludedIds.contains(countryId));
        return countryId;
    }

    public List<Country> findRandom(final int number, final long excludedId) {
        final List<Long> excludedIds = new ArrayList<>();
        excludedIds.add(excludedId);
        final List<Country> countries = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            final Country country = getRandom(excludedIds);
            countries.add(country);
            excludedIds.add(country.getId());
        }
        return countries;
    }

}
