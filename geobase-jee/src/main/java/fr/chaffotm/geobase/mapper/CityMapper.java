package fr.chaffotm.geobase.mapper;

import fr.chaffotm.geobase.domain.CityEntity;
import fr.chaffotm.geobase.web.domain.City;

public class CityMapper {

    public static City map(final CityEntity entity) {
        final City city = new City();
        city.setName(entity.getName());
        return city;
    }

    public static CityEntity unMap(final City resource) {
        final CityEntity city = new CityEntity();
        city.setName(resource.getName());
        return city;
    }
    
}
