package fr.chaffotm.geobase.web.resource;

import java.util.ArrayList;
import java.util.List;

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

}
