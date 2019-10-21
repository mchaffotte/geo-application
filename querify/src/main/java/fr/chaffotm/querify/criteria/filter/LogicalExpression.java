package fr.chaffotm.querify.criteria.filter;

import fr.chaffotm.querify.criteria.Expression;
import fr.chaffotm.querify.criteria.ExpressionVisitor;
import fr.chaffotm.querify.criteria.FieldExpression;

public class LogicalExpression implements Expression {
    private FieldExpression left;

    private LogicalOperator operator;

    private FieldExpression right;

    public LogicalExpression(final FieldExpression left, final LogicalOperator operator, final FieldExpression right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    public FieldExpression getLeft() {
        return left;
    }

    public LogicalOperator getOperator() {
        return operator;
    }

    public FieldExpression getRight() {
        return right;
    }

    @Override
    public void accept(final ExpressionVisitor visitor) {
        visitor.visit(this);
    }

}
