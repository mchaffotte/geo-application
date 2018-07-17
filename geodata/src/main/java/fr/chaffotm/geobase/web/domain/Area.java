package fr.chaffotm.geobase.web.domain;

import javax.xml.bind.annotation.XmlTransient;
import java.util.Objects;

public class Area {

    // square kilometer
    private double land;

    // square kilometer
    private double water;

    public double getLand() {
        return land;
    }

    public void setLand(double land) {
        this.land = land;
    }

    public double getWater() {
        return water;
    }

    public void setWater(double water) {
        this.water = water;
    }

    @XmlTransient
    public double getTotal() {
        return land + water;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Area area = (Area) o;
        return Double.compare(area.land, land) == 0 &&
                Double.compare(area.water, water) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(land, water);
    }

    @Override
    public String toString() {
        return "Area{" +
                "land=" + land +
                ", water=" + water +
                '}';
    }
}
