package fr.chaffotm.quizzify.service.descriptor;

import fr.chaffotm.geodata.entity.CountryEntity;
import fr.chaffotm.querify.CriteriaRepository;
import fr.chaffotm.querify.criteria.QueryCriteria;
import fr.chaffotm.quizzify.service.ColumnType;

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
    public String getQuestionImage(final CountryEntity country) {
        return null;
    }

    @Override
    public String getAttributeValue(final CountryEntity country) {
        return country.getName();
    }

}
