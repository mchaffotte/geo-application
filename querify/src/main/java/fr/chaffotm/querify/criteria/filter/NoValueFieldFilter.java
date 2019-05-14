package fr.chaffotm.querify.criteria.filter;

import fr.chaffotm.querify.criteria.FieldFilter;
import fr.chaffotm.querify.criteria.FieldFilterVisitor;

public class NoValueFieldFilter implements FieldFilter {

    private final String fieldName;

    private final NoValueOperator operator;

    public NoValueFieldFilter(String fieldName, NoValueOperator operator) {
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
    public void accept(FieldFilterVisitor visitor) {
        visitor.visit(this);
    }

}
