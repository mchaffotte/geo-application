package fr.chaffotm.geodata.mapper;

import fr.chaffotm.geodata.entity.CityEntity;
import fr.chaffotm.geodata.resource.City;

public class CityMapper {

    private CityMapper() {
    }

    public static City map(final CityEntity entity) {
        if (entity == null) {
            return null;
        }
        final City city = new City();
        city.setName(entity.getName());
        return city;
    }

    public static CityEntity unMap(final City resource) {
        if (resource == null) {
            return null;
        }
        final CityEntity city = new CityEntity();
        city.setName(resource.getName());
        return city;
    }

}
