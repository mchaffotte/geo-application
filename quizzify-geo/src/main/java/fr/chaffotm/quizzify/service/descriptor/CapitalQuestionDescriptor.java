package fr.chaffotm.quizzify.service.descriptor;

import fr.chaffotm.geodata.entity.CountryEntity;
import fr.chaffotm.querify.criteria.FieldOrder;
import fr.chaffotm.querify.criteria.QueryCriteria;

public class CapitalQuestionDescriptor extends QueryCriteriaQuestionDescriptor {

    @Override
    public QueryCriteria<CountryEntity> getQueryCriteria() {
        final QueryCriteria<CountryEntity> criteria = new QueryCriteria<>(CountryEntity.class);
        criteria.setJoin("capital");
        criteria.addSort("id", FieldOrder.DESC);
        return criteria;
    }

    @Override
    public String getQuestionType() {
        return "CAPITAL";
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
