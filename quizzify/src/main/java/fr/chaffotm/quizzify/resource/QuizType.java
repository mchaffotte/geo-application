package fr.chaffotm.quizzify.resource;

import java.util.List;
import java.util.Objects;

public class QuizType {

    private String questionType;

    private List<AnswerType> answerTypes;

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public List<AnswerType> getAnswerTypes() {
        return answerTypes;
    }

    public void setAnswerTypes(List<AnswerType> answerTypes) {
        this.answerTypes = answerTypes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuizType quizType = (QuizType) o;
        return Objects.equals(questionType, quizType.questionType) &&
                Objects.equals(answerTypes, quizType.answerTypes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionType, answerTypes);
    }

    @Override
    public String toString() {
        return "QuizType{" +
                "questionType=" + questionType +
                ", answerTypes=" + answerTypes +
                '}';
    }

}
