package fr.chaffotm.geodata.entity;

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

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "land", column = @Column(name = "land_area")),
            @AttributeOverride(name = "water", column = @Column(name = "water_area"))
    })
    private AreaEntity area;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "capital_id", foreignKey = @ForeignKey(name = "fk_country_capital_id"))
    private CityEntity capital;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", foreignKey = @ForeignKey(name = "fk_country_region_id"))
    private RegionEntity region;

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

    public AreaEntity getArea() {
        return area;
    }

    public void setArea(AreaEntity area) {
        this.area = area;
    }

    public CityEntity getCapital() {
        return capital;
    }

    public void setCapital(CityEntity capital) {
        this.capital = capital;
    }

    public RegionEntity getRegion() {
        return region;
    }

    public void setRegion(RegionEntity region) {
        this.region = region;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CountryEntity)) return false;
        CountryEntity that = (CountryEntity) o;
        return Objects.equals(getCode(), that.getCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCode());
    }

}