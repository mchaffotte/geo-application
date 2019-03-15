package fr.chaffotm.geoquiz.service.descriptor;

import fr.chaffotm.geodata.entity.CountryEntity;
import fr.chaffotm.query.QueryCriteria;
import fr.chaffotm.query.criteria.Order;

public class SilhouetteQuestionDescriptor extends QueryCriteriaQuestionDescriptor {

    @Override
    public QueryCriteria getQueryCriteria() {
        final QueryCriteria criteria = new QueryCriteria();
        criteria.addSort("id", Order.DESC);
        return criteria;
    }

    @Override
    public ImageType getImageType() {
        return ImageType.SILHOUETTE;
    }

    @Override
    public String getQuestion(final CountryEntity country) {
        return "What country does this silhouette belong to?";
    }

}
