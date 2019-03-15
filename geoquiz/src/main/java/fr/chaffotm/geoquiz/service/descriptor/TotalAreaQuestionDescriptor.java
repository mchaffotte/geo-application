package fr.chaffotm.geoquiz.service.descriptor;

import fr.chaffotm.geodata.entity.CountryEntity;
import fr.chaffotm.query.FunctionCriteria;
import fr.chaffotm.query.criteria.Order;
import fr.chaffotm.query.criteria.SumFunction;
import fr.chaffotm.geoquiz.service.ColumnType;

public class TotalAreaQuestionDescriptor extends FunctionCriteriaQuestionDescriptor {

    public FunctionCriteria getFunctionCriteria() {
        final FunctionCriteria criteria = new FunctionCriteria(new SumFunction("total", "land", "water"));
        criteria.setJoin("area");
        criteria.addSort("total", Order.DESC);
        return criteria;
    }

    @Override
    public ColumnType getAttributeColumnType() {
        return ColumnType.NUMERIC;
    }

    @Override
    public String getQuestion(final CountryEntity country) {
        return "Which country has the largest total area?";
    }

}
