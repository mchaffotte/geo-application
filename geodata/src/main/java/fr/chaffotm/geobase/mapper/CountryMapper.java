package fr.chaffotm.geobase.mapper;

import fr.chaffotm.geobase.domain.CountryEntity;
import fr.chaffotm.geobase.web.domain.Country;

import java.util.ArrayList;
import java.util.List;

public class CountryMapper {

    private CountryMapper() {
    }

    public static List<Country> map(final List<CountryEntity> entities) {
        final List<Country> countries = new ArrayList<>();
        for (CountryEntity entity : entities) {
                countries.add(map(entity));
            }
        return countries;
    }

    public static Country map(final CountryEntity entity) {
        final Country country = new Country();
        country.setId(entity.getId());
        country.setCode(entity.getCode());
        country.setName(entity.getName());
        country.setArea(AreaMapper.map(entity.getArea()));
        country.setCapital(CityMapper.map(entity.getCapital()));
        return country;
    }

    public static List<CountryEntity> unMap(final List<Country> resources) {
        final List<CountryEntity> countries = new ArrayList<>();
        for (Country resource : resources) {
                countries.add(unMap(resource));
            }
        return countries;
    }

    public static CountryEntity unMap(final Country resource) {
        final CountryEntity country = new CountryEntity();
        country.setCode(resource.getCode());
        country.setName(resource.getName());
        country.setArea(AreaMapper.unMap(resource.getArea()));
        country.setCapital(CityMapper.unMap(resource.getCapital()));
        return country;
    }
    
}
