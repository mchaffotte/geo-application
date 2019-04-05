package fr.chaffotm.geoquiz.service.descriptor;

import fr.chaffotm.geodata.entity.CountryEntity;
import fr.chaffotm.query.criteria.FieldOrder;
import fr.chaffotm.query.criteria.QueryCriteria;

public class SilhouetteQuestionDescriptor extends QueryCriteriaQuestionDescriptor {

    @Override
    public QueryCriteria<CountryEntity> getQueryCriteria() {
        final QueryCriteria<CountryEntity> criteria = new QueryCriteria<>(CountryEntity.class);
        criteria.addSort("id", FieldOrder.DESC);
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
