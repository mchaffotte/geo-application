package fr.chaffotm.geobase.service.quiz.descriptor;

import fr.chaffotm.geobase.domain.CountryEntity;
import fr.chaffotm.geobase.repository.FunctionCriteria;
import fr.chaffotm.geobase.repository.criteria.Order;
import fr.chaffotm.geobase.repository.criteria.SumFunction;
import fr.chaffotm.geobase.service.quiz.ColumnType;

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
