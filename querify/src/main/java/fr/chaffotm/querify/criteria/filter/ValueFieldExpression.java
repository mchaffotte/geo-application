package fr.chaffotm.querify.criteria.filter;

import fr.chaffotm.querify.criteria.FieldExpression;
import fr.chaffotm.querify.criteria.ExpressionVisitor;

public class ValueFieldExpression implements FieldExpression {

    private final String fieldName;

    private final ValueOperator operator;

    private final Object value;

    public ValueFieldExpression(final String fieldName, final ValueOperator operator, final Object value) {
        this.fieldName = fieldName;
        this.operator = operator;
        this.value = value;
    }

    @Override
    public String getFieldName() {
        return fieldName;
    }

    public ValueOperator getOperator() {
        return operator;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public void accept(final ExpressionVisitor visitor) {
        visitor.visit(this);
    }

}
