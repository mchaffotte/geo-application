package fr.chaffotm.geoquiz.resource;

import java.util.Objects;

public class QuizResult {

    private int nbOfCorrectAnswers;

    private int nbOfQuestions;

    public int getNbOfCorrectAnswers() {
        return nbOfCorrectAnswers;
    }

    public void setNbOfCorrectAnswers(int nbOfCorrectAnswers) {
        this.nbOfCorrectAnswers = nbOfCorrectAnswers;
    }

    public int getNbOfQuestions() {
        return nbOfQuestions;
    }

    public void setNbOfQuestions(int nbOfQuestions) {
        this.nbOfQuestions = nbOfQuestions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuizResult that = (QuizResult) o;
        return nbOfCorrectAnswers == that.nbOfCorrectAnswers &&
                nbOfQuestions == that.nbOfQuestions;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nbOfCorrectAnswers, nbOfQuestions);
    }

}
