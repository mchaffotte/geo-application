package fr.chaffotm.geobase.service.quiz.descriptor;

import fr.chaffotm.geobase.domain.CountryEntity;
import fr.chaffotm.geobase.repository.Order;
import fr.chaffotm.geobase.repository.QueryCriteria;
import fr.chaffotm.geobase.repository.Sort;
import fr.chaffotm.geobase.service.quiz.ColumnType;

public class SilhouetteQuestionDescriptor implements QuestionDescriptor {

    @Override
    public QueryCriteria getQueryCriteria() {
        final QueryCriteria criteria = new QueryCriteria();
        criteria.addSort(new Sort("id", Order.DESC));
        return criteria;
    }

    @Override
    public ColumnType getAttributeColumnType() {
        return ColumnType.VARCHAR;
    }

    @Override
    public ImageType getImageType() {
        return ImageType.SILHOUETTE;
    }

    @Override
    public String getQuestion(final CountryEntity country) {
        return "What country does this silhouette belong to?";
    }

    @Override
    public String getAttributeValue(final CountryEntity country) {
        return country.getName();
    }

    @Override
    public boolean isMultipleChoiceOnly() {
        return false;
    }

}
