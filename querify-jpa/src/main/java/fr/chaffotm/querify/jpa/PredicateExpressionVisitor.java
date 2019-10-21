package fr.chaffotm.querify.jpa;

import fr.chaffotm.querify.criteria.ExpressionVisitor;
import fr.chaffotm.querify.criteria.filter.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.Stack;

public class PredicateExpressionVisitor implements ExpressionVisitor {

    private final CriteriaBuilder builder;

    private final JoinBuilder joinBuilder;

    private Stack<Predicate> predicates;

    public PredicateExpressionVisitor(final CriteriaBuilder builder, final JoinBuilder joinBuilder) {
        this.builder = builder;
        this.joinBuilder = joinBuilder;
        this.predicates = new Stack<>();
    }

    @Override
    public void visit(final CollectionFieldExpression expression) {
        final Path<String> path = joinBuilder.getPath(expression.getFieldName());
        switch (expression.getOperator()) {
            case NOT_IN:
                predicates.add(builder.not(path.in(expression.getElements())));
                break;
            case IN:
                predicates.add(path.in(expression.getElements()));
                break;
            default:
                predicates.add(null);
                break;
        }
    }

    @Override
    public void visit(final NoValueFieldExpression expression) {
        final Path<String> path = joinBuilder.getPath(expression.getFieldName());
        switch (expression.getOperator()) {
            case NOT_NULL:
                predicates.add(path.isNotNull());
                break;
            case NULL:
                predicates.add(path.isNull());
                break;
            default:
                predicates.add(null);
                break;
        }
    }

    @Override
    public void visit(final ValueFieldExpression expression) {
        final Path<String> path = joinBuilder.getPath(expression.getFieldName());
        switch (expression.getOperator()) {
            case GREATER_THAN:
                predicates.add(builder.greaterThan(path, toStringValue(expression)));
                break;
            case GREATER_THAN_OR_EQUAL:
                predicates.add(builder.greaterThanOrEqualTo(path, toStringValue(expression)));
                break;
            case LESS_THAN:
                predicates.add( builder.lessThan(path, toStringValue(expression)));
                break;
            case LESS_THAN_OR_EQUAL:
                predicates.add(builder.lessThanOrEqualTo(path, toStringValue(expression)));
                break;
            case NOT_EQUAL:
                predicates.add(builder.notEqual(path, expression.getValue()));
                break;
            case EQUAL:
                predicates.add(builder.equal(path, expression.getValue()));
                break;
            default:
                predicates.add(null);
                break;
        }
    }

    @Override
    public void visit(final LogicalExpression expression) {
        expression.getLeft().accept(this);
        expression.getRight().accept(this);
        final Predicate logical;
        if (expression.getOperator() == LogicalOperator.OR) {
            logical = builder.or(predicates.pop(), predicates.pop());
        } else {
            logical = builder.and(predicates.pop(), predicates.pop());

        }
        predicates.add(logical);
    }

    Predicate getPredicate() {
        return predicates.peek();
    }

    private String toStringValue(final ValueFieldExpression expression) {
        return expression.getValue().toString();
    }

}
