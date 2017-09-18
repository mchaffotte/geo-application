package fr.chaffotm.geobase.web.domain;

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

}
