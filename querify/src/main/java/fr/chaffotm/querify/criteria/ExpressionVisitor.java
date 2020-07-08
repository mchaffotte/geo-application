package fr.chaffotm.querify.criteria;

import fr.chaffotm.querify.criteria.filter.*;

public interface ExpressionVisitor {

    void visit(ValueSetFieldExpression expression);

    void visit(AliasSetFieldExpression expression);

    void visit(NoValueFieldExpression expression);

    void visit(ValueFieldExpression expression);

    void visit(LogicalExpression expression);
}
