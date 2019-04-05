package fr.chaffotm.geoquiz.service.descriptor;

import fr.chaffotm.geodata.entity.CountryEntity;
import fr.chaffotm.query.criteria.QueryCriteria;
import fr.chaffotm.query.CriteriaRepository;
import fr.chaffotm.geoquiz.service.ColumnType;

import java.util.List;

public abstract class QueryCriteriaQuestionDescriptor implements QuestionDescriptor {

    abstract QueryCriteria<CountryEntity> getQueryCriteria();

    @Override
    public List<CountryEntity> getQuizCountries(final CriteriaRepository repository) {
        return repository.findAll(1, null, getQueryCriteria());
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
