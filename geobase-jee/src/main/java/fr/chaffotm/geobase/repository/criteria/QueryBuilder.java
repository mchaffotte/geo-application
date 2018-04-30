package fr.chaffotm.geobase.repository.criteria;

import javax.persistence.criteria.*;
import javax.persistence.criteria.Order;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryBuilder<T, R> {

    private final CriteriaBuilder builder;

    private final CriteriaQuery<T> query;

    private final Root<?> mainEntity;

    private Expression<?> functionExpression;

    private List<Order> orders;

    private Map<String, Join> joinAliases;

    public QueryBuilder(final CriteriaBuilder builder, final Class<T> queryClass, final Class<R> mainClass) {
        this.builder = builder;
        orders = new ArrayList<>();
        functionExpression = null;
        query = builder.createQuery(queryClass);
        mainEntity = query.from(mainClass);
        mainEntity.alias(String.valueOf(mainClass.getSimpleName().charAt(0)).toLowerCase());

        joinAliases = new HashMap<>();
    }

    public QueryBuilder setFunction(final SumFunction function, final JoinEntity joinEntity) {
        final Path<?> selection;
        if (joinEntity != null) {
            selection = buildJoin(joinEntity);
        } else {
            selection = mainEntity;
        }
        functionExpression = builder.sum(selection.get(function.getAttribute1()), selection.get(function.getAttribute2()));
        functionExpression.alias(function.getAlias());
        return this;
    }

    public QueryBuilder addJoin(final JoinEntity joinEntity) {
        final Join join = buildJoin(joinEntity);
        joinAliases.put(joinEntity.getName(), join);
        return this;
    }

    public QueryBuilder addSort(final Sort sort) {
        final String[] properties = sort.getPropertyName().split("\\.");
        if (properties.length == 1) {
            if (functionExpression == null || !functionExpression.getAlias().equals(properties[0])) {
                addSort(sort, mainEntity);
            } else {
                addSort(sort, functionExpression);
            }
        } else if (properties.length == 2) {
            final Join join = joinAliases.get(properties[0]);
            final Sort joinSort = new Sort(properties[1], sort.getOrder());
            addSort(joinSort, join);
        } else {
            throw new IllegalArgumentException();
        }
        return this;
    }

    private Join buildJoin(final JoinEntity joinEntity) {
        final Join join = mainEntity.join(joinEntity.getName());
        join.alias(joinEntity.getName());
        return join;
    }

    private void addSort(final Sort sort, final Path path) {
        final Expression expression = path.get(sort.getPropertyName());
        addSort(sort, expression);
    }

    private void addSort(final Sort sort, final Expression expression) {
        final Order order;
        if (fr.chaffotm.geobase.repository.criteria.Order.ASC.equals(sort.getOrder())) {
            order = builder.asc(expression);
        } else {
            order = builder.desc(expression);
        }
        orders.add(order);
    }

    public CriteriaQuery<T> build() {
        if (functionExpression != null) {
            query.multiselect(mainEntity, functionExpression);
        } else {
            query.select((Selection<? extends T>) mainEntity);
        }
        query.orderBy(orders);
        return query;
    }

}
