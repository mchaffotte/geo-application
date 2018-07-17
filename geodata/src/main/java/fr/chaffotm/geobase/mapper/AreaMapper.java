package fr.chaffotm.geobase.mapper;

import fr.chaffotm.geobase.domain.AreaEntity;
import fr.chaffotm.geobase.service.AreaConverter;
import fr.chaffotm.geobase.web.domain.Area;

public class AreaMapper {

    public static Area map(final AreaEntity entity) {
        final Area area = new Area();
        area.setLand(AreaConverter.convertSquareHectometerToSquareKilometer(entity.getLand()));
        area.setWater(AreaConverter.convertSquareHectometerToSquareKilometer(entity.getWater()));
        return area;
    }

    public static AreaEntity unMap(final Area resource) {
        final AreaEntity area = new AreaEntity();
        area.setLand(AreaConverter.convertSquareKilometerToSquareHectometer(resource.getLand()));
        area.setWater(AreaConverter.convertSquareKilometerToSquareHectometer(resource.getWater()));
        return area;
    }

}
