package fr.chaffotm.querify.criteria.filter;

import fr.chaffotm.querify.criteria.FieldExpression;
import fr.chaffotm.querify.criteria.ExpressionVisitor;

import java.util.List;

public class CollectionFieldExpression implements FieldExpression {

    private final String fieldName;

    private final CollectionOperator operator;

    private final List<String> elements;

    public CollectionFieldExpression(final String fieldName, final CollectionOperator operator, final String... elements) {
        this.fieldName = fieldName;
        this.operator = operator;
        this.elements = List.of(elements);
    }

    @Override
    public String getFieldName() {
        return fieldName;
    }

    public CollectionOperator getOperator() {
        return operator;
    }

    public List<String> getElements() {
        return elements;
    }

    @Override
    public void accept(final ExpressionVisitor visitor) {
        visitor.visit(this);
    }

}
