package fr.chaffotm.quizzify.service.descriptor;

import fr.chaffotm.geodata.entity.CountryEntity;
import fr.chaffotm.querify.criteria.FieldOrder;
import fr.chaffotm.querify.criteria.QueryCriteria;
import fr.chaffotm.quizzify.service.ColumnType;

public class SilhouetteQuestionDescriptor implements QuestionDescriptor<CountryEntity> {

    @Override
    public QueryCriteria<CountryEntity> getQueryCriteria() {
        final QueryCriteria<CountryEntity> criteria = new QueryCriteria<>(CountryEntity.class);
        criteria.addSort("id", FieldOrder.DESC);
        return criteria;
    }

    @Override
    public String getQuestionType() {
        return "SILHOUETTE";
    }

    @Override
    public ColumnType getAttributeColumnType() {
        return ColumnType.VARCHAR;
    }

    @Override
    public String getQuestionImage(final CountryEntity entity) {
        return "silhouettes/" + entity.getCode().toLowerCase() + ".png";
    }

    @Override
    public String getQuestion(final CountryEntity entity) {
        return "What country does this silhouette belong to?";
    }

    @Override
    public String getAttributeValue(final CountryEntity entity) {
        return entity.getName();
    }

}
