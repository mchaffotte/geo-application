package fr.chaffotm.querify.criteria;

public class Function {

    private final String alias;

    private final FunctionOperator operator;

    private final String attribute1;

    private final String attribute2;

    public Function(final String alias, final FunctionOperator operator, final String attribute1, final String attribute2) {
        this.alias = alias;
        this.operator = operator;
        this.attribute1 = attribute1;
        this.attribute2 = attribute2;
    }

    public String getAlias() {
        return alias;
    }

    public FunctionOperator getOperator() {
        return operator;
    }

    public String getAttribute1() {
        return attribute1;
    }

    public String getAttribute2() {
        return attribute2;
    }

}
