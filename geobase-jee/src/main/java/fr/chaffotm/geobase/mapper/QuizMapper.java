package fr.chaffotm.geobase.mapper;

import fr.chaffotm.geobase.domain.QuestionEntity;
import fr.chaffotm.geobase.domain.QuizEntity;
import fr.chaffotm.geobase.web.domain.Question;
import fr.chaffotm.geobase.web.domain.Quiz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuizMapper {

    private static final Random RANDOM = new Random(System.currentTimeMillis());

    public static Quiz map(final QuizEntity entity, final String baseURI) {
        final Quiz quiz = new Quiz();
        quiz.setId(entity.getId());
        for (QuestionEntity questionEntity : entity.getQuestions()) {
            final Question question = map(questionEntity, baseURI);
            quiz.addQuestion(question);
        }
        return quiz;
    }

    private static Question map(final QuestionEntity entity, final String baseURI) {
        final Question question = new Question();
        if (entity.getImage() != null) {
            question.setImagePath(baseURI + "flags/" + entity.getImage());
        }
        question.setWording(entity.getWording());
        final List<String> suggestions = getRandomOrderSuggestions(entity);
        question.setSuggestions(suggestions);
       return question;
    }

    private static List<String> getRandomOrderSuggestions(final QuestionEntity entity) {
        final List<String> suggestions = new ArrayList<>(entity.getSuggestions());
        Collections.shuffle(suggestions, RANDOM);
        return suggestions;
    }

}
