package fr.chaffotm.querify.criteria;

import java.util.ArrayList;
import java.util.List;

public class QueryCriteria<T> {

    private final Class<T> entityClass;

    private Function function;

    private String join;

    private Expression filter;

    private List<Sort> sorts;

    public QueryCriteria(final Class<T> entityClass) {
        this.entityClass = entityClass;
        this.sorts = new ArrayList<>();
    }

    public Class<T> getEntityClass() {
        return entityClass;
    }

    public Function getFunction() {
        return function;
    }

    public void setFunction(final Function function) {
        this.function = function;
    }

    public String getJoin() {
        return join;
    }

    public void setJoin(final String fieldName) {
        this.join = fieldName;
    }

    public Expression getFilter() {
        return filter;
    }

    public void setFilter(final Expression filter) {
        this.filter = filter;
    }

    public List<Sort> getSorts() {
        return sorts;
    }

    public void addSort(final String fieldName, final FieldOrder order) {
        final Sort sort = new Sort(fieldName, order);
        this.sorts.add(sort);
    }

    public void addSort(final String fieldName) {
        addSort(fieldName, null);
    }

    public void setSorts(final List<Sort> sorts) {
        this.sorts.clear();
        this.sorts.addAll(sorts);
    }

}
