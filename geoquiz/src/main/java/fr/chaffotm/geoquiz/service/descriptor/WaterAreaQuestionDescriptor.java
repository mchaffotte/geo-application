package fr.chaffotm.geoquiz.service.descriptor;

import fr.chaffotm.geodata.entity.CountryEntity;
import fr.chaffotm.query.QueryCriteria;
import fr.chaffotm.query.criteria.Order;
import fr.chaffotm.geoquiz.service.ColumnType;

public class WaterAreaQuestionDescriptor extends QueryCriteriaQuestionDescriptor {

    @Override
    public QueryCriteria getQueryCriteria() {
        final QueryCriteria criteria = new QueryCriteria();
        criteria.setJoin("area");
        criteria.addSort("area.water", Order.DESC);
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
