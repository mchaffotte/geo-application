package fr.chaffotm.data.io.geo;

import fr.chaffotm.data.io.validation.AlreadyUsed;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class Country {

    @NotBlank
    private String name;

    @NotBlank
    private String shortName;

    @AlreadyUsed
    private String alpha2Code;

    @AlreadyUsed
    private String alpha3Code;

    private String capital;

    private Area area;

    private List<Border> borders;

    private int region;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getAlpha2Code() {
        return alpha2Code;
    }

    public void setAlpha2Code(String alpha2Code) {
        this.alpha2Code = alpha2Code;
    }

    public String getAlpha3Code() {
        return alpha3Code;
    }

    public void setAlpha3Code(String alpha3Code) {
        this.alpha3Code = alpha3Code;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public List<Border> getBorders() {
        return borders;
    }

    public void setBorders(List<Border> borders) {
        this.borders = borders;
    }

    public int getRegion() {
        return region;
    }

    public void setRegion(int region) {
        this.region = region;
    }

}
