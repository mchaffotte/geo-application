package fr.chaffotm.querify.criteria.filter;

import fr.chaffotm.querify.criteria.FieldExpression;
import fr.chaffotm.querify.criteria.ExpressionVisitor;

public class NoValueFieldExpression implements FieldExpression {

    private final String fieldName;

    private final NoValueOperator operator;

    public NoValueFieldExpression(String fieldName, NoValueOperator operator) {
        this.fieldName = fieldName;
        this.operator = operator;
    }

    @Override
    public String getFieldName() {
        return fieldName;
    }

    public NoValueOperator getOperator() {
        return operator;
    }

    @Override
    public void accept(ExpressionVisitor visitor) {
        visitor.visit(this);
    }

}
