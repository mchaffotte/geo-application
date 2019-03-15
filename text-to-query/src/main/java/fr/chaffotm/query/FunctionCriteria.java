package fr.chaffotm.query;

import fr.chaffotm.query.criteria.SumFunction;

public class FunctionCriteria extends QueryCriteria {

    private final SumFunction sum;

    public FunctionCriteria(final SumFunction sum) {
        super();
        this.sum = sum;
    }

    public SumFunction getFunction() {
        return sum;
    }

}
