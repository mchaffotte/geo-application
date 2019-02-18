package fr.chaffotm.geobase.web.domain;

import javax.validation.constraints.NotNull;
import java.util.List;

public class QuizResponse {

    @NotNull
    private List<@NotNull String> answers;

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

}
