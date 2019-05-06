package fr.chaffotm.quizzify.resource;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class QuizConfiguration {

    @NotNull
    private String questionType;

    @NotNull
    private AnswerType answerType;

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
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
