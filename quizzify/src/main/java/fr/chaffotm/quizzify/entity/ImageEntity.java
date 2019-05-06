package fr.chaffotm.quizzify.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;
import java.util.UUID;

@Entity(name = "Image")
@Table(name = "image")
public class ImageEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID uuid;

    @Column(name = "path", length = 50, nullable = false)
    private String filename;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
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
