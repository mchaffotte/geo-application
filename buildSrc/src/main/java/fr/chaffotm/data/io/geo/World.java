package fr.chaffotm.data.io.geo;

import javax.validation.Valid;
import java.util.List;

public class World {

    @Valid
    private List<Country> countries;

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

}
