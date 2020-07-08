package fr.chaffotm.querify.criteria.filter;

import fr.chaffotm.querify.criteria.FieldExpression;

public abstract class SetFieldExpression implements FieldExpression {

    private final String fieldName;

    private final SetOperator operator;

    public SetFieldExpression(final String fieldName, final SetOperator operator) {
        this.fieldName = fieldName;
        this.operator = operator;
    }

    @Override
    public String getFieldName() {
        return fieldName;
    }

    public SetOperator getOperator() {
        return operator;
    }

}
