package fr.chaffotm.geoquiz.service.generator;

import fr.chaffotm.geodata.entity.CountryEntity;
import fr.chaffotm.geoquiz.resource.AnswerType;
import fr.chaffotm.geoquiz.service.MultipleChoice;

import java.util.List;

public interface Generator {

    List<MultipleChoice> generate(List<CountryEntity> countries, AnswerType answerType);

}
