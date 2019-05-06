package fr.chaffotm.quizzify.service.descriptor;

import fr.chaffotm.geodata.entity.CountryEntity;
import fr.chaffotm.querify.criteria.FieldOrder;
import fr.chaffotm.querify.criteria.QueryCriteria;
import fr.chaffotm.quizzify.service.ColumnType;

public class LandAreaQuestionDescriptor extends QueryCriteriaQuestionDescriptor {

    @Override
    public QueryCriteria<CountryEntity> getQueryCriteria() {
        final QueryCriteria<CountryEntity> criteria = new QueryCriteria<>(CountryEntity.class);
        criteria.setJoin("area");
        criteria.addSort("area.land", FieldOrder.DESC);
        return criteria;
    }

    @Override
    public String getQuestionType() {
        return "LAND_AREA";
    }

    @Override
    public ColumnType getAttributeColumnType() {
        return ColumnType.NUMERIC;
    }

    @Override
    public String getQuestion(final CountryEntity country) {
        return "Which country has the largest land area?";
    }

}
