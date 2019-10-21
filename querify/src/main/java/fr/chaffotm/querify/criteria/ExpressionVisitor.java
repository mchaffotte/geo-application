package fr.chaffotm.querify.criteria;

import fr.chaffotm.querify.criteria.filter.CollectionFieldExpression;
import fr.chaffotm.querify.criteria.filter.LogicalExpression;
import fr.chaffotm.querify.criteria.filter.NoValueFieldExpression;
import fr.chaffotm.querify.criteria.filter.ValueFieldExpression;

public interface ExpressionVisitor {

    void visit(CollectionFieldExpression expression);

    void visit(NoValueFieldExpression expression);

    void visit(ValueFieldExpression expression);

    void visit(LogicalExpression expression);
}
