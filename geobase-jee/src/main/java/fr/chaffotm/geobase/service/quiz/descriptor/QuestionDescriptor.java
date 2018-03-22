package fr.chaffotm.geobase.service.quiz.descriptor;

import fr.chaffotm.geobase.domain.CountryEntity;
import fr.chaffotm.geobase.service.quiz.ColumnType;

public interface QuestionDescriptor {

    String getSortColumn();

    ColumnType getAttributeColumnType();

    String getQuestion(CountryEntity country);

    String getAttributeValue(CountryEntity country);

}
