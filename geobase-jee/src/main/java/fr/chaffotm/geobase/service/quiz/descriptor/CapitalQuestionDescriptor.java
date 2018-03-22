package fr.chaffotm.geobase.service.quiz.descriptor;

import fr.chaffotm.geobase.domain.CountryEntity;
import fr.chaffotm.geobase.service.quiz.ColumnType;

public class CapitalQuestionDescriptor implements QuestionDescriptor {

    @Override
    public String getSortColumn() {
        return "id";
    }

    @Override
    public ColumnType getAttributeColumnType() {
        return ColumnType.VARCHAR;
    }

    @Override
    public String getQuestion(final CountryEntity country) {
        return "What is the capital name of " + country.getName() + "?";
    }

    @Override
    public String getAttributeValue(final CountryEntity country) {
        return country.getCapital().getName();
    }

}
