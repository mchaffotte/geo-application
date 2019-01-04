package fr.chaffotm.geobase.web.domain;

import java.util.List;
import java.util.Objects;

public class QuizType {

    private QuestionType questionType;

    private List<ResponseType> responseTypes;

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public List<ResponseType> getResponseTypes() {
        return responseTypes;
    }

    public void setResponseTypes(List<ResponseType> responseTypes) {
        this.responseTypes = responseTypes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuizType quizType = (QuizType) o;
        return questionType == quizType.questionType &&
                Objects.equals(responseTypes, quizType.responseTypes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionType, responseTypes);
    }

    @Override
    public String toString() {
        return "QuizType{" +
                "questionType=" + questionType +
                ", responseTypes=" + responseTypes +
                '}';
    }

}
