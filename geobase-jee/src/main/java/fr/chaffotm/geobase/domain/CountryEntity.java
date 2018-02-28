package fr.chaffotm.geobase.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "Country")
@Table(
        name = "country",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_country_code", columnNames = "code"),
                @UniqueConstraint(name = "uk_capital_id", columnNames = "capital_id")
        }
)
public class CountryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "country_generator")
    @SequenceGenerator(name="country_generator", sequenceName = "country_sequence", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "code", nullable = false, length = 6)
    private String code;

    @Column(name = "name", nullable = false, length = 25)
    private String name;

    @Column(name = "total_area", nullable = false)
    private int totalArea;

    @Column(name = "population", nullable =false)
    private int population;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "capital_id", foreignKey = @ForeignKey(name = "fk_country_capital_id"))
    private CityEntity capital;

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public CityEntity getCapital() {
        return capital;
    }

    public void setCapital(CityEntity capital) {
        this.capital = capital;
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
        final CountryEntity other = (CountryEntity) obj;
        return Objects.equals(id, other.id);
    }

}