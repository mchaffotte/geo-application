package fr.chaffotm.geobase.service.quiz.descriptor;

import fr.chaffotm.geobase.domain.CountryEntity;
import fr.chaffotm.geobase.repository.Join;
import fr.chaffotm.geobase.repository.Order;
import fr.chaffotm.geobase.repository.QueryCriteria;
import fr.chaffotm.geobase.repository.Sort;
import fr.chaffotm.geobase.service.quiz.ColumnType;

public class WaterAreaQuestionDescriptor implements QuestionDescriptor {

    @Override
    public QueryCriteria getQueryCriteria() {
        final QueryCriteria criteria = new QueryCriteria();
        criteria.setJoin(new Join("area"));
        criteria.addSort(new Sort("water", Order.DESC));
        return criteria;
    }

    @Override
    public ColumnType getAttributeColumnType() {
        return ColumnType.VARCHAR;
    }

    @Override
    public ImageType getImageType() {
        return ImageType.NONE;
    }

    @Override
    public String getQuestion(final CountryEntity country) {
        return "Which country has the largest water area?";
    }

    @Override
    public String getAttributeValue(final CountryEntity country) {
        return country.getName();
    }

}
