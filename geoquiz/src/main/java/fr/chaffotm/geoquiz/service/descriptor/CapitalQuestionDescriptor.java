package fr.chaffotm.geoquiz.service.descriptor;

import fr.chaffotm.geodata.entity.CountryEntity;
import fr.chaffotm.query.QueryCriteria;
import fr.chaffotm.query.criteria.Order;

public class CapitalQuestionDescriptor extends QueryCriteriaQuestionDescriptor {

    @Override
    public QueryCriteria getQueryCriteria() {
        final QueryCriteria criteria = new QueryCriteria();
        criteria.setJoin("capital");
        criteria.addSort("id", Order.DESC);
        return criteria;
    }

    @Override
    public String getQuestion(final CountryEntity country) {
        return "What is the capital of " + country.getName() + "?";
    }

    @Override
    public String getAttributeValue(final CountryEntity country) {
        return country.getCapital().getName();
    }

}
