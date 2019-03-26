package fr.chaffotm.geoquiz.builder;

import fr.chaffotm.geoquiz.entity.AnswerEntity;
import fr.chaffotm.geoquiz.entity.QuestionEntity;
import fr.chaffotm.geoquiz.entity.QuizEntity;
import fr.chaffotm.geoquiz.resource.AnswerType;

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
