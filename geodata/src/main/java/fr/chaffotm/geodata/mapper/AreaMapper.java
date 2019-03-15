package fr.chaffotm.geodata.mapper;

import fr.chaffotm.geodata.entity.AreaEntity;
import fr.chaffotm.geodata.resource.Area;
import fr.chaffotm.measurement.AreaConverter;

import static fr.chaffotm.measurement.AreaUnits.SQUARE_HECTOMETER;
import static fr.chaffotm.measurement.AreaUnits.SQUARE_KILOMETER;

public class AreaMapper {

    private static AreaConverter areaConverter = new AreaConverter(2);

    private AreaMapper() {
    }

    public static Area map(final AreaEntity entity) {
        final Area area = new Area();
        area.setLand(areaConverter.convert(entity.getLand(), SQUARE_HECTOMETER, SQUARE_KILOMETER));
        area.setWater(areaConverter.convert(entity.getWater(), SQUARE_HECTOMETER, SQUARE_KILOMETER));
        return area;
    }

    public static AreaEntity unMap(final Area resource) {
        final AreaEntity area = new AreaEntity();
        area.setLand((int) areaConverter.convert(resource.getLand(), SQUARE_KILOMETER, SQUARE_HECTOMETER));
        area.setWater((int) areaConverter.convert(resource.getWater(), SQUARE_KILOMETER, SQUARE_HECTOMETER));
        return area;
    }

}
