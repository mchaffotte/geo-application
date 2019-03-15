package fr.chaffotm.query.criteria;

public class SumFunction {

    private final String alias;

    private final String attribute1;

    private final String attribute2;

    public SumFunction(final String alias, final String attribute1, final String attribute2) {
        this.alias = alias;
        this.attribute1 = attribute1;
        this.attribute2 = attribute2;
    }

    public String getAlias() {
        return alias;
    }

    public String getAttribute1() {
        return attribute1;
    }

    public String getAttribute2() {
        return attribute2;
    }

}
