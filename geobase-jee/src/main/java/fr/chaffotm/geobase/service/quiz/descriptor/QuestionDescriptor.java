package fr.chaffotm.geobase.service.quiz.descriptor;

import fr.chaffotm.geobase.domain.CountryEntity;
import fr.chaffotm.geobase.repository.CountryRepository;
import fr.chaffotm.geobase.service.quiz.ColumnType;

import java.util.List;

public interface QuestionDescriptor {

    List<CountryEntity> getQuizCountries(CountryRepository countryRepository);

    ColumnType getAttributeColumnType();

    ImageType getImageType();

    String getQuestion(CountryEntity country);

    String getAttributeValue(CountryEntity country);

}
