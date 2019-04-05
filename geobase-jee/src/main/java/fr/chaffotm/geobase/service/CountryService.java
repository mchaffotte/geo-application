package fr.chaffotm.geobase.service;

import fr.chaffotm.geodata.entity.CountryEntity;
import fr.chaffotm.geodata.mapper.CountryMapper;
import fr.chaffotm.geobase.repository.CountryRepository;
import fr.chaffotm.query.criteria.QueryCriteria;
import fr.chaffotm.query.criteria.Sort;
import fr.chaffotm.query.SortConverter;
import fr.chaffotm.geodata.resource.Country;
import fr.chaffotm.geobase.web.resource.Frame;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

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
        final QueryCriteria<CountryEntity> criteria = new QueryCriteria<>(CountryEntity.class);
        criteria.setSorts(sorts);
        final List<CountryEntity> countries = countryRepository.findAll(offset, limit, criteria);
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
