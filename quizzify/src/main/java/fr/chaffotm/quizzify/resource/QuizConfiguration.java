package fr.chaffotm.quizzify.resource;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class QuizConfiguration {

    @NotNull
    private String questionType;

    @NotNull
    private AnswerType answerType;

    @Valid
    private Filter filter;

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

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuizConfiguration that = (QuizConfiguration) o;
        return Objects.equals(questionType, that.questionType) &&
                answerType == that.answerType &&
                Objects.equals(filter, that.filter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionType, answerType, filter);
    }

}
