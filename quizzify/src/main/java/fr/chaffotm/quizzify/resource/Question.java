package fr.chaffotm.quizzify.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Question {

    private String imagePath;

    private String wording;

    private List<String> choices = new ArrayList<>();

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getWording() {
        return wording;
    }

    public void setWording(String wording) {
        this.wording = wording;
    }

    public List<String> getChoices() {
        return choices;
    }

    public void setChoices(List<String> choices) {
        this.choices = choices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return Objects.equals(imagePath, question.imagePath) &&
                Objects.equals(wording, question.wording) &&
                Objects.equals(choices, question.choices);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imagePath, wording, choices);
    }

}
