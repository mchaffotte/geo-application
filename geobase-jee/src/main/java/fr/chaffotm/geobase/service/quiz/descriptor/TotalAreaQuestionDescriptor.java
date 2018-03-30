package fr.chaffotm.geobase.service.quiz.descriptor;

import fr.chaffotm.geobase.domain.CountryEntity;
import fr.chaffotm.geobase.service.quiz.ColumnType;

public class TotalAreaQuestionDescriptor implements QuestionDescriptor {

    @Override
    public String getSortColumn() {
        return "totalArea";
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
    public String getQuestion(final CountryEntity country) {
        return "Which country has the largest area?";
    }

    @Override
    public String getAttributeValue(final CountryEntity country) {
        return country.getName();
    }

}
