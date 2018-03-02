package fr.chaffotm.geobase.web.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Question {

    private String wording;

    private List<String> suggestions = new ArrayList<>();

    public String getWording() {
        return wording;
    }

    public void setWording(String wording) {
        this.wording = wording;
    }

    public List<String> getSuggestions() {
        return suggestions;
    }

    // Used by JAX-RS
    protected void setSuggestions(List<String> suggestions) {
        this.suggestions = suggestions;
    }

    public void addSuggestion(final String suggestion) {
        this.suggestions.add(suggestion);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return Objects.equals(wording, question.wording) &&
                Objects.equals(suggestions, question.suggestions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(wording, suggestions);
    }

}
