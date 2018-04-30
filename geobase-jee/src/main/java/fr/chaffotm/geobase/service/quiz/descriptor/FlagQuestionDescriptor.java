package fr.chaffotm.geobase.service.quiz.descriptor;

import fr.chaffotm.geobase.domain.CountryEntity;
import fr.chaffotm.geobase.repository.QueryCriteria;
import fr.chaffotm.geobase.repository.criteria.Order;

public class FlagQuestionDescriptor extends QueryCriteriaQuestionDescriptor {

    @Override
    public QueryCriteria getQueryCriteria() {
        final QueryCriteria criteria = new QueryCriteria();
        criteria.addSort("id", Order.DESC);
        return criteria;
    }

    @Override
    public ImageType getImageType() {
        return ImageType.FLAG;
    }

    @Override
    public String getQuestion(final CountryEntity country) {
        return "What country does this flag belong to?";
    }

}
