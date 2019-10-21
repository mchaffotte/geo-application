package fr.chaffotm.quizzify.service.generator;

import fr.chaffotm.quizzify.resource.AnswerType;
import fr.chaffotm.quizzify.service.MultipleChoice;

import java.util.List;

public interface Generator {

    <T> List<MultipleChoice<T>> generate(List<T> entities, AnswerType answerType);

}
