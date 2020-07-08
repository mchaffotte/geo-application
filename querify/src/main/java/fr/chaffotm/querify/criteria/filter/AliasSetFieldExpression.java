package fr.chaffotm.querify.criteria.filter;

import fr.chaffotm.querify.criteria.ExpressionVisitor;

import java.util.List;

public class AliasSetFieldExpression extends SetFieldExpression {

    private final String alias;

    private final List<Object> parameters;

    public AliasSetFieldExpression(final String fieldName, final SetOperator operator, final String alias, final Object... parameters) {
        super(fieldName, operator);
        this.alias = alias;
        this.parameters = List.of(parameters);
    }

    public String getAlias() {
        return alias;
    }

    public List<Object> getParameters() {
        return parameters;
    }

    @Override
    public void accept(final ExpressionVisitor visitor) {
        visitor.visit(this);
    }

}
