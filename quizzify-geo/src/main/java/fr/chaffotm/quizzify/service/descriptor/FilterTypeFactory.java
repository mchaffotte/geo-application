package fr.chaffotm.quizzify.service.descriptor;

import fr.chaffotm.geodata.entity.RegionEntity;
import fr.chaffotm.querify.CriteriaRepository;
import fr.chaffotm.quizzify.resource.FilterType;
import fr.chaffotm.quizzify.resource.Possibility;

import java.util.ArrayList;
import java.util.List;

public class FilterTypeFactory {

    public FilterType build(final CriteriaRepository repository) {
        final List<RegionEntity> subRegions = repository.findAll("getFirstSubRegions", RegionEntity.class);
        final FilterType type = new FilterType();
        type.setName("region.id");
        type.setLabel("Region");
        final List<Possibility> values = new ArrayList<>();
        for (RegionEntity subRegion : subRegions) {
            final Possibility possibility = new Possibility();
            possibility.setLabel(subRegion.getName());
            possibility.setValue(subRegion.getId());
            values.add(possibility);
        }
        type.setValues(values);
        return type;
    }

}
