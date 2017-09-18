package fr.chaffotm.geobase.web.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Question {

    private String expression;

    private List<String> choices = new ArrayList<>();

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public List<String> getChoices() {
        return choices;
    }

    public void setChoices(List<String> choices) {
        this.choices = choices;
    }

    public void addChoice(final String choice) {
        this.choices.add(choice);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return Objects.equals(expression, question.expression) &&
                Objects.equals(choices, question.choices);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expression, choices);
    }


}
