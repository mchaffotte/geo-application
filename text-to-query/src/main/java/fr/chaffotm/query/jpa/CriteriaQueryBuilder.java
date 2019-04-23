package fr.chaffotm.query.jpa;

import fr.chaffotm.query.criteria.*;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CriteriaQueryBuilder<T> {

    private final CriteriaBuilder builder;

    private final CriteriaQuery<FunctionEntity> query;

    private final Root<T> mainEntity;

    private Expression<?> functionExpression;

    private Map<String, Join<Object, Object>> joinAliases;

    private List<Order> orders;

    public CriteriaQueryBuilder(final CriteriaBuilder builder, final Class<T> queryClass) {
        this.builder = builder;
        query = builder.createQuery(FunctionEntity.class);
        functionExpression = null;
        mainEntity = query.from(queryClass);
        joinAliases = new HashMap<>();
        orders = new ArrayList<>();
    }

    public CriteriaQuery<FunctionEntity> build() {
        if (functionExpression == null) {
            query.multiselect(mainEntity);
        } else {
            query.multiselect(mainEntity, functionExpression);
        }
        query.orderBy(orders);
        return query;
    }

    public CriteriaQueryBuilder<T> function(final Function function) {
        if (FunctionOperator.SUM == function.getOperator()) {
            final Path<Number> path1 = getPath(function.getAttribute1());
            final Path<Number> path2 = getPath(function.getAttribute2());
            functionExpression = builder.sum(path1, path2);
            functionExpression.alias(function.getAlias());
        } else {
            throw new IllegalArgumentException();
        }
        return this;
    }

    private Path<Number> getPath(final String fieldPath) {
        final String fieldName;
        final Path<?> selection;
        final FieldAccessor accessor = new FieldAccessor(fieldPath);
        if (accessor.hasSubField()) {
            fieldName = accessor.getSubFieldName();
            selection = addJoin(accessor.getFieldName());
        } else {
            fieldName = accessor.getFieldName();
            selection = mainEntity;
        }
        return selection.get(fieldName);
    }

    public CriteriaQueryBuilder<T> join(final String fieldName) {
        addJoin(fieldName);
        return this;
    }

    public CriteriaQueryBuilder<T> sort(final Sort sort) {
        final FieldOrder order = sort.getOrder();
        final FieldAccessor accessor = new FieldAccessor(sort.getFieldName());
        Expression path = null;
        if (accessor.hasSubField()) {
            final Join join = addJoin(accessor.getFieldName());
            path = join.get(accessor.getSubFieldName());
        } else if (accessor.hasField()) {
            if (functionExpression == null || !functionExpression.getAlias().equals(accessor.getFieldName())) {
                path = mainEntity.get(accessor.getFieldName());
            } else {
                path = functionExpression;
            }
        }
        if (path != null) {
            addSort(path, order);
        } else {
            throw new IllegalArgumentException();
        }
        return this;
    }

    private Join<Object, Object> addJoin(final String fieldName) {
        return joinAliases.computeIfAbsent(fieldName, join -> mainEntity.join(fieldName));
    }

    private void addSort(final Expression expression, final FieldOrder fieldOrder) {
        final Order order;
        if (FieldOrder.DESC == fieldOrder) {
            order = builder.desc(expression);
        } else {
            order = builder.asc(expression);
        }
        orders.add(order);
    }

}
