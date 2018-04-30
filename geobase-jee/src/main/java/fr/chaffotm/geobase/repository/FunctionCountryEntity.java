package fr.chaffotm.geobase.repository;

import fr.chaffotm.geobase.domain.CountryEntity;

public class FunctionCountryEntity {

    private CountryEntity entity;

    private double value;

    public FunctionCountryEntity(CountryEntity entity, double value) {
        this.entity = entity;
        this.value = value;
    }

    public FunctionCountryEntity(CountryEntity entity, int value) {
        this.entity = entity;
        this.value = value;
    }

    public CountryEntity getEntity() {
        return entity;
    }

    public void setEntity(CountryEntity entity) {
        this.entity = entity;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

}
