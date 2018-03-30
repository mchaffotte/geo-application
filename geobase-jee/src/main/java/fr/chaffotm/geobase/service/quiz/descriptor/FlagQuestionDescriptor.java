package fr.chaffotm.geobase.service.quiz.descriptor;

import fr.chaffotm.geobase.domain.CountryEntity;
import fr.chaffotm.geobase.service.quiz.ColumnType;

public class FlagQuestionDescriptor implements QuestionDescriptor {

    @Override
    public String getSortColumn() {
        return "id";
    }

    @Override
    public ColumnType getAttributeColumnType() {
        return ColumnType.VARCHAR;
    }

    @Override
    public ImageType getImageType() {
        return ImageType.FLAG;
    }

    @Override
    public String getQuestion(final CountryEntity country) {
        return "What country does this flag belong to?";
    }

    @Override
    public String getAttributeValue(final CountryEntity country) {
        return country.getName();
    }

}
