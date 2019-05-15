package fr.chaffotm.querify.criteria.filter;

import fr.chaffotm.querify.criteria.FieldFilter;
import fr.chaffotm.querify.criteria.FieldFilterVisitor;

public class ValueFieldFilter implements FieldFilter {

    private final String fieldName;

    private final ValueOperator operator;

    private Object value;

    public ValueFieldFilter(final String fieldName, final ValueOperator operator, final Object value) {
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
    public void accept(final FieldFilterVisitor visitor) {
        visitor.visit(this);
    }

}
