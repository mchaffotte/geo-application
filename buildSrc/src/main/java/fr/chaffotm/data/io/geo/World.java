package fr.chaffotm.data.io.geo;

import javax.validation.Valid;
import java.util.List;

public class World {

    private List<Region> regions;

    @Valid
    private List<Country> countries;

    public List<Region> getRegions() {
        return regions;
    }

    public void setRegions(List<Region> regions) {
        this.regions = regions;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

}
