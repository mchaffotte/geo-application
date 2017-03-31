package fr.chaffotm.geobase.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "Country")
@Table(name = "country")
@NamedQueries({
        @NamedQuery(name = "Country.findAll", query = "SELECT c FROM Country c")
})
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "totalArea", nullable = false)
    private int totalArea;

    @Column(name = "population", nullable =false)
    private int population;

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getTotalArea() {
        return totalArea;
    }

    public void setTotalArea(final int totalArea) {
        this.totalArea = totalArea;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(final int population) {
        this.population = population;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Country other = (Country) obj;
        return Objects.equals(id, other.id);
    }

}