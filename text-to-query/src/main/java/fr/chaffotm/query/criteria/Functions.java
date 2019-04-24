package fr.chaffotm.query.criteria;

public class Functions {

    private Functions() {
    }

    public static Function sum(final String alias, final String firstFieldName, final String secondFieldName) {
        return new Function(alias, FunctionOperator.SUM, firstFieldName, secondFieldName);
    }

}
