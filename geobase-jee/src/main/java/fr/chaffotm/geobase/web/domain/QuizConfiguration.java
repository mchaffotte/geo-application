package fr.chaffotm.geobase.web.domain;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class QuizConfiguration {

    @NotNull
    private QuestionType questionType;

    private boolean multipleChoice;

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public boolean isMultipleChoice() {
        return multipleChoice;
    }

    public void setMultipleChoice(boolean multipleChoice) {
        this.multipleChoice = multipleChoice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuizConfiguration that = (QuizConfiguration) o;
        return multipleChoice == that.multipleChoice &&
                questionType == that.questionType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionType, multipleChoice);
    }

}
