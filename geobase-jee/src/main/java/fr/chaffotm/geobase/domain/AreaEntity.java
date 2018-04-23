package fr.chaffotm.geobase.domain;

import javax.persistence.Embeddable;

@Embeddable
public class AreaEntity {

    // square hectometer
    private int land;

    // square hectometer
    private int water;

    public int getLand() {
        return land;
    }

    public void setLand(int land) {
        this.land = land;
    }

    public int getWater() {
        return water;
    }

    public void setWater(int water) {
        this.water = water;
    }

    public int getTotal() {
        return land + water;
    }

}
