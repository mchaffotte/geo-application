package fr.chaffotm.query.criteria;

import java.util.Objects;

public class Sort {

    private final String propertyName;

    private final Order order;

    public Sort(final String propertyName, final Order order) {
        this.propertyName = propertyName;
        this.order = order;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public Order getOrder() {
        return order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sort sort = (Sort) o;
        return Objects.equals(propertyName, sort.propertyName) &&
                order == sort.order;
    }

    @Override
    public int hashCode() {
        return Objects.hash(propertyName, order);
    }

}
