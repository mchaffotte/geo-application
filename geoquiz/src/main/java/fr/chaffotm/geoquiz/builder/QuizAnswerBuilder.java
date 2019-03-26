package fr.chaffotm.geoquiz.builder;

import fr.chaffotm.geoquiz.resource.QuestionAnswer;
import fr.chaffotm.geoquiz.resource.QuizAnswer;

public class QuizAnswerBuilder {

    private final QuizAnswer quizAnswer;

    public QuizAnswerBuilder() {
        this.quizAnswer = new QuizAnswer();
    }

    public QuizAnswer getQuizAnswer() {
        return quizAnswer;
    }

    public QuizAnswerBuilder questionAnswer(final String answer, final String... multipleAnswers) {
        final QuestionAnswer questionAnswer = new QuestionAnswer();
        questionAnswer.addAnswer(answer);
        for (String multipleAnswer : multipleAnswers) {
            questionAnswer.addAnswer(multipleAnswer);
        }
        quizAnswer.addQuestionAnswer(questionAnswer);
        return this;
    }

}
