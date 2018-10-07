package fr.chaffotm.geobase.service;

import fr.chaffotm.geobase.domain.CountryEntity;
import fr.chaffotm.geobase.mapper.CountryMapper;
import fr.chaffotm.geobase.repository.CountryRepository;
import fr.chaffotm.geobase.repository.QueryCriteria;
import fr.chaffotm.geobase.repository.SortConverter;
import fr.chaffotm.geobase.repository.criteria.Sort;
import fr.chaffotm.geobase.web.Frame;
import fr.chaffotm.geobase.web.domain.Country;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CountryService {

    private final CountryRepository countryRepository;

    private final SortConverter converter;

    public CountryService(final CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
        converter = new SortConverter();
    }

    public Frame<Country> findAll(final int offset, final Integer limit, final String sort) {
        final List<Sort> sorts = converter.getAsList(sort);
        final QueryCriteria criteria = new QueryCriteria();
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
