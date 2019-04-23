package fr.chaffotm.geoquiz.mapper;

import fr.chaffotm.geoquiz.entity.AnswerEntity;
import fr.chaffotm.geoquiz.entity.QuestionEntity;
import fr.chaffotm.geoquiz.entity.QuizEntity;
import fr.chaffotm.geoquiz.resource.AnswerType;
import fr.chaffotm.geoquiz.resource.Question;
import fr.chaffotm.geoquiz.resource.Quiz;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class QuizMapper {

    private static final Random RANDOM = new Random(System.currentTimeMillis());

    private QuizMapper() {
    }

    public static Quiz map(final QuizEntity entity, final String baseURI) {
        final Quiz quiz = new Quiz();
        quiz.setId(entity.getId());
        for (QuestionEntity questionEntity : entity.getQuestions()) {
            final Question question = map(questionEntity, entity.getAnswerType(), baseURI);
            quiz.addQuestion(question);
        }
        return quiz;
    }

    private static Question map(final QuestionEntity entity, final AnswerType answerType, final String baseURI) {
        final Question question = new Question();
        if (entity.getImage() != null) {
            question.setImagePath(baseURI + "images/" + entity.getImage().getUuid());
        }
        question.setWording(entity.getWording());
        if (AnswerType.MULTIPLE_CHOICE == answerType) {
            final List<String> choices = getRandomOrderChoices(entity);
            question.setChoices(choices);
        }
        return question;
    }

    private static List<String> getRandomOrderChoices(final QuestionEntity entity) {
        final List<String> suggestions = entity.getAnswers().stream()
                .map(AnswerEntity::getAnswer)
                .collect(Collectors.toList());
        Collections.shuffle(suggestions, RANDOM);
        return suggestions;
    }

}
