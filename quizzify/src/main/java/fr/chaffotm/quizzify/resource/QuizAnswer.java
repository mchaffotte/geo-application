package fr.chaffotm.quizzify.resource;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class QuizAnswer {

    @NotNull @Valid
    private List<@NotNull @Valid QuestionAnswer> questionAnswers;

    public QuizAnswer() {
        this.questionAnswers = new ArrayList<>();
    }

    public List<QuestionAnswer> getQuestionAnswers() {
        return questionAnswers;
    }

    public void setQuestionAnswers(List<QuestionAnswer> questionAnswers) {
        this.questionAnswers = questionAnswers;
    }

    public void addQuestionAnswer(QuestionAnswer questionAnswer) {
        questionAnswers.add(questionAnswer);
    }

}
