package fr.chaffotm.geobase.web.domain;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class QuizConfiguration {

    @NotNull
    private QuestionType questionType;

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuizConfiguration that = (QuizConfiguration) o;
        return questionType == that.questionType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionType);
    }
}
