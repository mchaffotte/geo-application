package fr.chaffotm.geoquiz.service.descriptor;

import fr.chaffotm.geodata.entity.CountryEntity;
import fr.chaffotm.geoquiz.service.ColumnType;
import fr.chaffotm.query.criteria.FieldOrder;
import fr.chaffotm.query.criteria.QueryCriteria;

public class WaterAreaQuestionDescriptor extends QueryCriteriaQuestionDescriptor {

    @Override
    public QueryCriteria getQueryCriteria() {
        final QueryCriteria<CountryEntity> criteria = new QueryCriteria<>(CountryEntity.class);
        criteria.setJoin("area");
        criteria.addSort("area.water", FieldOrder.DESC);
        return criteria;
    }

    @Override
    public ColumnType getAttributeColumnType() {
        return ColumnType.NUMERIC;
    }

    @Override
    public String getQuestion(final CountryEntity country) {
        return "Which country has the largest water area?";
    }

}
