package fr.chaffotm.geoquiz.resource;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class QuizConfiguration {

    @NotNull
    private QuestionType questionType;

    @NotNull
    private AnswerType answerType;

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public AnswerType getAnswerType() {
        return answerType;
    }

    public void setAnswerType(AnswerType answerType) {
        this.answerType = answerType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuizConfiguration that = (QuizConfiguration) o;
        return questionType == that.questionType &&
                answerType == that.answerType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionType, answerType);
    }

}
