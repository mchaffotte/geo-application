package fr.chaffotm.querify.jpa;

import fr.chaffotm.querify.criteria.*;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class CriteriaQueryBuilder<T> {

    private final CriteriaBuilder builder;

    private final CriteriaQuery<FunctionEntity> query;

    private final Root<T> mainEntity;

    private Expression<?> functionExpression;

    private JoinBuilder joinBuilder;

    private List<Order> orders;

    public CriteriaQueryBuilder(final CriteriaBuilder builder, final Class<T> queryClass) {
        this.builder = builder;
        query = builder.createQuery(FunctionEntity.class);
        functionExpression = null;
        mainEntity = query.from(queryClass);
        joinBuilder = new JoinBuilder(mainEntity);
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
            final Path<Number> path1 = joinBuilder.getPath(function.getAttribute1());
            final Path<Number> path2 = joinBuilder.getPath(function.getAttribute2());
            functionExpression = builder.sum(path1, path2);
            functionExpression.alias(function.getAlias());
        } else {
            throw new IllegalArgumentException();
        }
        return this;
    }

    public CriteriaQueryBuilder<T> join(final String fieldName) {
        joinBuilder.addJoin(fieldName);
        return this;
    }

    public CriteriaQueryBuilder<T> filter(final FieldFilter filter) {
        final PredicateFieldFilterVisitor visitor = new PredicateFieldFilterVisitor(builder, joinBuilder);
        filter.accept(visitor);
        final Predicate predicate = visitor.getPredicate();
        query.where(predicate);
        return this;
    }

    public CriteriaQueryBuilder<T> sort(final Sort sort) {
        final FieldOrder order = sort.getOrder();
        final FieldAccessor accessor = new FieldAccessor(sort.getFieldName());
        Expression path = null;
        if (accessor.hasSubField()) {
            final Join join = joinBuilder.addJoin(accessor.getFieldName());
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
