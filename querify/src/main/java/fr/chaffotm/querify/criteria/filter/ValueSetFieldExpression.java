package fr.chaffotm.querify.criteria.filter;

import fr.chaffotm.querify.criteria.ExpressionVisitor;

import java.util.List;

public class ValueSetFieldExpression extends SetFieldExpression {

    private final List<String> elements;

    public ValueSetFieldExpression(final String fieldName, final SetOperator operator, final String... elements) {
        super(fieldName, operator);
        this.elements = List.of(elements);
    }

    public List<String> getElements() {
        return elements;
    }

    @Override
    public void accept(final ExpressionVisitor visitor) {
        visitor.visit(this);
    }

}
