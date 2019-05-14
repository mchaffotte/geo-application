package fr.chaffotm.querify.criteria;

import fr.chaffotm.querify.criteria.filter.CollectionFieldFilter;
import fr.chaffotm.querify.criteria.filter.CollectionOperator;
import fr.chaffotm.querify.criteria.filter.NoValueFieldFilter;
import fr.chaffotm.querify.criteria.filter.NoValueOperator;

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

}
