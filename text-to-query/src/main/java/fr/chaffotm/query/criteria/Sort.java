package fr.chaffotm.query.criteria;

import java.util.Objects;

public class Sort {

    private final String fieldName;

    private final FieldOrder order;

    public Sort(final String fieldName, final FieldOrder order) {
        this.fieldName = fieldName;
        this.order = order;
    }

    public String getFieldName() {
        return fieldName;
    }

    public FieldOrder getOrder() {
        return order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sort sort = (Sort) o;
        return Objects.equals(fieldName, sort.fieldName) &&
                order == sort.order;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fieldName, order);
    }

}
