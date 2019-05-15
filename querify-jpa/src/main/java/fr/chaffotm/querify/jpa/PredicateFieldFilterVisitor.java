package fr.chaffotm.querify.jpa;

import fr.chaffotm.querify.criteria.FieldFilterVisitor;
import fr.chaffotm.querify.criteria.filter.CollectionFieldFilter;
import fr.chaffotm.querify.criteria.filter.NoValueFieldFilter;
import fr.chaffotm.querify.criteria.filter.ValueFieldFilter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

public class PredicateFieldFilterVisitor implements FieldFilterVisitor {

    private final CriteriaBuilder builder;

    private final JoinBuilder joinBuilder;

    private Predicate predicate;

    public PredicateFieldFilterVisitor(final CriteriaBuilder builder, final JoinBuilder joinBuilder) {
        this.builder = builder;
        this.joinBuilder = joinBuilder;
    }

    @Override
    public void visit(final CollectionFieldFilter filter) {
        final Path<String> path = joinBuilder.getPath(filter.getFieldName());
        switch (filter.getOperator()) {
            case NOT_IN:
                predicate = builder.not(path.in(filter.getElements()));
                break;
            case IN:
                predicate = path.in(filter.getElements());
                break;
            default:
                predicate = null;
                break;
        }
    }

    @Override
    public void visit(final NoValueFieldFilter filter) {
        final Path<String> path = joinBuilder.getPath(filter.getFieldName());
        switch (filter.getOperator()) {
            case NOT_NULL:
                predicate = path.isNotNull();
                break;
            case NULL:
                predicate = path.isNull();
                break;
            default:
                predicate = null;
                break;
        }
    }

    @Override
    public void visit(final ValueFieldFilter filter) {
        final Path<String> path = joinBuilder.getPath(filter.getFieldName());
        switch (filter.getOperator()) {
            case GREATER_THAN:
                predicate = builder.greaterThan(path, toStringValue(filter));
                break;
            case GREATER_THAN_OR_EQUAL:
                predicate = builder.greaterThanOrEqualTo(path, toStringValue(filter));
                break;
            case LESS_THAN:
                predicate = builder.lessThan(path, toStringValue(filter));
                break;
            case LESS_THAN_OR_EQUAL:
                predicate = builder.lessThanOrEqualTo(path, toStringValue(filter));
                break;
            case NOT_EQUAL:
                predicate = builder.notEqual(path, filter.getValue());
                break;
            case EQUAL:
                predicate = builder.equal(path, filter.getValue());
                break;
            default:
                predicate = null;
                break;
        }
    }

    Predicate getPredicate() {
        return predicate;
    }

    private String toStringValue(final ValueFieldFilter filter) {
        return filter.getValue().toString();
    }

}
