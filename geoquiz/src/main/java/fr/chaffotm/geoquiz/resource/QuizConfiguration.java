package fr.chaffotm.geoquiz.resource;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class QuizConfiguration {

    @NotNull
    private QuestionType questionType;

    @NotNull
    private ResponseType responseType;

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public void setResponseType(ResponseType responseType) {
        this.responseType = responseType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuizConfiguration that = (QuizConfiguration) o;
        return questionType == that.questionType &&
                responseType == that.responseType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionType, responseType);
    }

}
