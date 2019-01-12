package fr.chaffotm.geobase.service.quiz.descriptor;

import fr.chaffotm.geobase.domain.CountryEntity;
import fr.chaffotm.geobase.repository.QueryCriteria;
import fr.chaffotm.geobase.repository.Repository;
import fr.chaffotm.geobase.service.quiz.ColumnType;

import java.util.List;

public abstract class QueryCriteriaQuestionDescriptor implements QuestionDescriptor {

    abstract QueryCriteria getQueryCriteria();

    @Override
    public List<CountryEntity> getQuizCountries(final Repository repository) {
        return repository.findAll(1, null, getQueryCriteria(), CountryEntity.class);
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
    public String getAttributeValue(final CountryEntity country) {
        return country.getName();
    }

}
