package fr.chaffotm.geobase.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "City")
@Table(name = "city")
public class CityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "city_generator")
    @SequenceGenerator(name="city_generator", sequenceName = "city_sequence", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
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
        final CityEntity other = (CityEntity) obj;
        return Objects.equals(id, other.id);
    }
    
}
