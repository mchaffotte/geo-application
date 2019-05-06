package fr.chaffotm.quizzify.service.descriptor;

import fr.chaffotm.geodata.entity.CountryEntity;
import fr.chaffotm.querify.criteria.FieldOrder;
import fr.chaffotm.querify.criteria.Functions;
import fr.chaffotm.querify.criteria.QueryCriteria;
import fr.chaffotm.quizzify.service.ColumnType;

public class TotalAreaQuestionDescriptor extends QueryCriteriaQuestionDescriptor {

    @Override
    QueryCriteria<CountryEntity> getQueryCriteria() {
        final QueryCriteria<CountryEntity> criteria = new QueryCriteria<>(CountryEntity.class);
        criteria.setJoin("area");
        criteria.setFunction(Functions.sum("total", "area.land", "area.water"));
        criteria.addSort("total", FieldOrder.DESC);
        return criteria;
    }

    @Override
    public String getQuestionType() {
        return "TOTAL_AREA";
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
