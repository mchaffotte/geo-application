package fr.chaffotm.querify.criteria;

import fr.chaffotm.querify.criteria.filter.*;

public class Filters {

    private Filters() {
    }

    public static FieldFilter in(final String fieldName, final String... values) {
        return new CollectionFieldFilter(fieldName, CollectionOperator.IN, values);
    }

    public static FieldFilter notIn(final String fieldName, final String... values) {
        return new CollectionFieldFilter(fieldName, CollectionOperator.NOT_IN, values);
    }

    public static FieldFilter isNull(final String fieldName) {
        return new NoValueFieldFilter(fieldName, NoValueOperator.NULL);
    }

    public static FieldFilter isNotNull(final String fieldName) {
        return new NoValueFieldFilter(fieldName, NoValueOperator.NOT_NULL);
    }

    public static FieldFilter equal(final String fieldName, final Object value) {
        return new ValueFieldFilter(fieldName, ValueOperator.EQUAL, value);
    }

    public static FieldFilter notEqual(final String fieldName, final Object value) {
        return new ValueFieldFilter(fieldName, ValueOperator.NOT_EQUAL, value);
    }

    public static FieldFilter greaterThan(final String fieldName, final Object value) {
        return new ValueFieldFilter(fieldName, ValueOperator.GREATER_THAN, value);
    }

    public static FieldFilter greaterThanOrEqual(final String fieldName, final Object value) {
        return new ValueFieldFilter(fieldName, ValueOperator.GREATER_THAN_OR_EQUAL, value);
    }

    public static FieldFilter lessThan(final String fieldName, final Object value) {
        return new ValueFieldFilter(fieldName, ValueOperator.LESS_THAN, value);
    }

    public static FieldFilter lessThanOrEqual(final String fieldName, final Object value) {
        return new ValueFieldFilter(fieldName, ValueOperator.LESS_THAN_OR_EQUAL, value);
    }

}
