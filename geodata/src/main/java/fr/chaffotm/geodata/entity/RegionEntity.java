package fr.chaffotm.geodata.entity;

import javax.persistence.*;
import javax.xml.namespace.QName;
import java.util.List;

@Entity(name = "Region")
@Table(
        name = "region",
        uniqueConstraints = @UniqueConstraint(name = "uk_region_code", columnNames = "numeric_code")
)
@NamedQueries(
        @NamedQuery(
                name = "getFirstSubRegions",
                query = "SELECT r FROM Region r WHERE r.parent = (SELECT o.id FROM Region o WHERE o.parent IS NULL) ORDER BY r.name")
)
@NamedNativeQueries(
        @NamedNativeQuery(
                name = "getSubRegions",
                query = "WITH sub_regions(id, parent_id) AS (SELECT id, parent_id FROM region WHERE id = ? " +
                        "UNION ALL SELECT r.id, r.parent_id FROM sub_regions INNER JOIN region r ON sub_regions.id = r.parent_id) " +
                        "SELECT id FROM sub_regions"
        )
)
public class RegionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "region_generator")
    @SequenceGenerator(name = "region_generator", sequenceName = "region_sequence", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "numeric_code", nullable = false, length = 3)
    private int numericCode;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", foreignKey = @ForeignKey(name = "fk_region_parent_id"))
    private RegionEntity parent;

    @OneToMany(
            mappedBy = "parent",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<RegionEntity> children;

    @OneToMany(
            mappedBy = "region",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<CountryEntity> countries;

    public Long getId() {
        return id;
    }

    public int getNumericCode() {
        return numericCode;
    }

    public void setNumericCode(int numericCode) {
        this.numericCode = numericCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RegionEntity getParent() {
        return parent;
    }

    public void setParent(RegionEntity parent) {
        this.parent = parent;
    }

    public List<RegionEntity> getChildren() {
        return children;
    }

    public void setChildren(List<RegionEntity> children) {
        this.children = children;
    }

    public List<CountryEntity> getCountries() {
        return countries;
    }

    public void setCountries(List<CountryEntity> countries) {
        this.countries = countries;
    }

}
