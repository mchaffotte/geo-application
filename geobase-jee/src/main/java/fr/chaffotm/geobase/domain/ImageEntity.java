package fr.chaffotm.geobase.domain;

import fr.chaffotm.geobase.service.quiz.descriptor.ImageType;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity(name = "Image")
@Table(name = "image")
public class ImageEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID uuid;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", foreignKey = @ForeignKey(name = "fk_image_country"))
    private CountryEntity country;

    @Column(name = "type", length = 10)
    private ImageType imageType;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public CountryEntity getCountry() {
        return country;
    }

    public void setCountry(CountryEntity country) {
        this.country = country;
    }

    public ImageType getImageType() {
        return imageType;
    }

    public void setImageType(ImageType imageType) {
        this.imageType = imageType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImageEntity)) return false;
        ImageEntity that = (ImageEntity) o;
        return Objects.equals(getUuid(), that.getUuid());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUuid());
    }

}
