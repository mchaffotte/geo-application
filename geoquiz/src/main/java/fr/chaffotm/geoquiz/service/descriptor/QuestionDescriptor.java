package fr.chaffotm.geoquiz.service.descriptor;

import fr.chaffotm.geodata.entity.CountryEntity;
import fr.chaffotm.geoquiz.service.ColumnType;
import fr.chaffotm.querify.CriteriaRepository;

import java.util.List;

public interface QuestionDescriptor {

    List<CountryEntity> getQuizCountries(CriteriaRepository repository);

    ColumnType getAttributeColumnType();

    ImageType getImageType();

    String getQuestion(CountryEntity country);

    String getAttributeValue(CountryEntity country);

}
