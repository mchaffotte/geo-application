package fr.chaffotm.geobase.mapper;

import fr.chaffotm.geobase.domain.QuestionEntity;
import fr.chaffotm.geobase.domain.QuizEntity;
import fr.chaffotm.geobase.web.domain.Question;
import fr.chaffotm.geobase.web.domain.Quiz;

public class QuizMapper {

    public static Quiz map(final QuizEntity entity) {
        final Quiz quiz = new Quiz();
        quiz.setId(entity.getId());
        for (QuestionEntity questionEntity : entity.getQuestions()) {
            final Question question = new Question();
            question.setExpression(questionEntity.getExpression());
            /*for (String choice : questionEntity.getChoices()) {
                question.addChoice(choice);
            }*/
            quiz.addQuestion(question);
        }
        return quiz;
    }

}
