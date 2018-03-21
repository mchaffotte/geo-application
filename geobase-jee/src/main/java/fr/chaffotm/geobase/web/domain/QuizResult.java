package fr.chaffotm.geobase.web.domain;

import java.util.Objects;

public class QuizResult {

    private int nbOfRightAnswers;

    private int totalNumberOfQuestions;

    private String message;

    public int getNbOfRightAnswers() {
        return nbOfRightAnswers;
    }

    public void setNbOfRightAnswers(int nbOfRightAnswers) {
        this.nbOfRightAnswers = nbOfRightAnswers;
    }

    public int getTotalNumberOfQuestions() {
        return totalNumberOfQuestions;
    }

    public void setTotalNumberOfQuestions(int totalNumberOfQuestions) {
        this.totalNumberOfQuestions = totalNumberOfQuestions;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuizResult that = (QuizResult) o;
        return nbOfRightAnswers == that.nbOfRightAnswers &&
                totalNumberOfQuestions == that.totalNumberOfQuestions &&
                Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nbOfRightAnswers, totalNumberOfQuestions, message);
    }

}
