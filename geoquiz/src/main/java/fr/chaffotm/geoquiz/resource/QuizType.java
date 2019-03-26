package fr.chaffotm.geoquiz.resource;

import java.util.List;
import java.util.Objects;

public class QuizType {

    private QuestionType questionType;

    private List<AnswerType> answerTypes;

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
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
        return questionType == quizType.questionType &&
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
