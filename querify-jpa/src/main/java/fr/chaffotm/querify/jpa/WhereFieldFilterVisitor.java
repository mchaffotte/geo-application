package fr.chaffotm.querify.jpa;

import fr.chaffotm.querify.criteria.FieldFilterVisitor;
import fr.chaffotm.querify.criteria.filter.CollectionFieldFilter;
import fr.chaffotm.querify.criteria.filter.NoValueFieldFilter;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

public class WhereFieldFilterVisitor implements FieldFilterVisitor {

    private final Path<String> path;

    private Predicate predicate;

    public WhereFieldFilterVisitor(Path<String> path) {
        this.path = path;
    }

    @Override
    public void visit(final CollectionFieldFilter filter) {
        switch (filter.getOperator()) {
            case NOT_IN:
                predicate = path.in(filter.getElements()).not();
                break;
            case IN:
            default:
                predicate = path.in(filter.getElements());
                break;
        }
    }

    @Override
    public void visit(final NoValueFieldFilter filter) {
        switch (filter.getOperator()) {
            case NOT_NULL:
                predicate = path.isNotNull();
                break;
            case NULL:
            default:
                predicate = path.isNull();
                break;
        }
    }

    public Predicate getPredicate() {
        return predicate;
    }

}

