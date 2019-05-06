package fr.chaffotm.quizzify.service.generator;

import fr.chaffotm.geodata.entity.CountryEntity;
import fr.chaffotm.quizzify.resource.AnswerType;
import fr.chaffotm.quizzify.service.MultipleChoice;

import java.util.List;

public interface Generator {

    List<MultipleChoice> generate(List<CountryEntity> countries, AnswerType answerType);

}
