package fr.chaffotm.geoquiz.service.descriptor;

import fr.chaffotm.geodata.entity.CountryEntity;
import fr.chaffotm.query.FunctionCriteria;
import fr.chaffotm.query.Repository;
import fr.chaffotm.geoquiz.service.ColumnType;

import java.util.List;

public abstract class FunctionCriteriaQuestionDescriptor implements QuestionDescriptor {

    abstract FunctionCriteria getFunctionCriteria();

    @Override
    public List<CountryEntity> getQuizCountries(final Repository repository) {
        return repository.findAll(1, null, getFunctionCriteria(), CountryEntity.class);
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
