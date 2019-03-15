package fr.chaffotm.geobase.web.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Frame<T> {

    private final List<T> resources;

    private final long total;

    // Used by JAX-RS
    public Frame() {
        this(new ArrayList<>(), 0);
    }

    public Frame(final List<T> resources, long total) {
        this.resources = resources;
        this.total = total;
    }

    public List<T> getResources() {
        return resources;
    }

    public long getTotal() {
        return total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Frame<?> frame = (Frame<?>) o;
        return total == frame.total &&
                Objects.equals(resources, frame.resources);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resources, total);
    }

}
