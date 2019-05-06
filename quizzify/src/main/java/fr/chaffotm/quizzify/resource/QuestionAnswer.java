package fr.chaffotm.quizzify.resource;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class QuestionAnswer {

    @NotNull
    private List<@NotNull String> answers;

    public QuestionAnswer() {
        answers = new ArrayList<>();
    }

    public List<String> getAnswers() {
        return answers;
    }

    protected void setAnswers(final List<String> answers) {
        this.answers = answers;
    }

    public void addAnswer(final String answer) {
        answers.add(answer);
    }

}
