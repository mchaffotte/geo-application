package fr.chaffotm.quizzify.service.descriptor;

import fr.chaffotm.geodata.entity.CountryEntity;
import fr.chaffotm.querify.CriteriaRepository;
import fr.chaffotm.quizzify.service.ColumnType;

import java.util.List;

public interface QuestionDescriptor {

    String getQuestionType();

    List<CountryEntity> getQuizCountries(CriteriaRepository repository);

    ColumnType getAttributeColumnType();

    String getQuestionImage(final CountryEntity country);

    String getQuestion(CountryEntity country);

    String getAttributeValue(CountryEntity country);

}
