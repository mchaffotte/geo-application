package fr.chaffotm.querify.criteria;

import fr.chaffotm.querify.criteria.filter.*;

public class Filters {

    private Filters() {
    }

    public static LogicalExpression and(final FieldExpression left, final FieldExpression right) {
        return new LogicalExpression(left, LogicalOperator.AND, right);
    }

    public static LogicalExpression or(final FieldExpression left, final FieldExpression right) {
        return new LogicalExpression(left, LogicalOperator.OR, right);
    }

    public static FieldExpression in(final String fieldName, final String... values) {
        return new CollectionFieldExpression(fieldName, CollectionOperator.IN, values);
    }

    public static FieldExpression notIn(final String fieldName, final String... values) {
        return new CollectionFieldExpression(fieldName, CollectionOperator.NOT_IN, values);
    }

    public static FieldExpression isNull(final String fieldName) {
        return new NoValueFieldExpression(fieldName, NoValueOperator.NULL);
    }

    public static FieldExpression isNotNull(final String fieldName) {
        return new NoValueFieldExpression(fieldName, NoValueOperator.NOT_NULL);
    }

    public static FieldExpression equal(final String fieldName, final Object value) {
        return new ValueFieldExpression(fieldName, ValueOperator.EQUAL, value);
    }

    public static FieldExpression notEqual(final String fieldName, final Object value) {
        return new ValueFieldExpression(fieldName, ValueOperator.NOT_EQUAL, value);
    }

    public static FieldExpression greaterThan(final String fieldName, final Object value) {
        return new ValueFieldExpression(fieldName, ValueOperator.GREATER_THAN, value);
    }

    public static FieldExpression greaterThanOrEqual(final String fieldName, final Object value) {
        return new ValueFieldExpression(fieldName, ValueOperator.GREATER_THAN_OR_EQUAL, value);
    }

    public static FieldExpression lessThan(final String fieldName, final Object value) {
        return new ValueFieldExpression(fieldName, ValueOperator.LESS_THAN, value);
    }

    public static FieldExpression lessThanOrEqual(final String fieldName, final Object value) {
        return new ValueFieldExpression(fieldName, ValueOperator.LESS_THAN_OR_EQUAL, value);
    }

}
