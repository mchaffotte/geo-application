package fr.chaffotm.geobase.service.quiz.descriptor;

import fr.chaffotm.geobase.domain.CountryEntity;
import fr.chaffotm.geobase.repository.QueryCriteria;
import fr.chaffotm.geobase.repository.criteria.Order;

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
        return "What is the capital name of " + country.getName() + "?";
    }

    @Override
    public String getAttributeValue(final CountryEntity country) {
        return country.getCapital().getName();
    }

}
