package fr.chaffotm.quizzify.service.descriptor;

import fr.chaffotm.geodata.entity.CountryEntity;
import fr.chaffotm.querify.criteria.FieldOrder;
import fr.chaffotm.querify.criteria.QueryCriteria;

public class FlagQuestionDescriptor extends QueryCriteriaQuestionDescriptor {

    @Override
    public QueryCriteria<CountryEntity> getQueryCriteria() {
        final QueryCriteria<CountryEntity> criteria = new QueryCriteria<>(CountryEntity.class);
        criteria.addSort("id", FieldOrder.DESC);
        return criteria;
    }

    @Override
    public String getQuestionType() {
        return "FLAG";
    }

    @Override
    public String getQuestionImage(final CountryEntity country) {
        return "flags/" + country.getCode() + ".png";
    }

    @Override
    public String getQuestion(final CountryEntity country) {
        return "What country does this flag belong to?";
    }

}
