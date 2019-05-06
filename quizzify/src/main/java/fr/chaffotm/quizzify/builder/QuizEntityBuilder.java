package fr.chaffotm.quizzify.builder;

import fr.chaffotm.quizzify.entity.AnswerEntity;
import fr.chaffotm.quizzify.entity.QuestionEntity;
import fr.chaffotm.quizzify.entity.QuizEntity;
import fr.chaffotm.quizzify.resource.AnswerType;

public class QuizEntityBuilder {

    private final QuizEntity quizEntity;

    public QuizEntityBuilder() {
        this(AnswerType.MULTIPLE_CHOICE);
    }

    public QuizEntityBuilder(AnswerType answerType) {
        quizEntity = new QuizEntity();
        quizEntity.setAnswerType(answerType);
    }

    public QuizEntity getQuizEntity() {
        return quizEntity;
    }

    public QuizEntityBuilder question(final String question, final String answer, final String... multipleAnswers) {
        final QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setWording(question);
        questionEntity.addAnswer(build(answer, true));
        for (String multipleAnswer : multipleAnswers) {
            questionEntity.addAnswer(build(multipleAnswer, true));
        }
        quizEntity.addQuestion(questionEntity);
        return this;
    }

    private AnswerEntity build(final String answer, final boolean correct) {
        final AnswerEntity answerEntity = new AnswerEntity();
        answerEntity.setAnswer(answer);
        answerEntity.setCorrect(correct);
        return answerEntity;
    }

}
