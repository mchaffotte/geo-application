package fr.chaffotm.quizzify.service;

import fr.chaffotm.quizzify.builder.QuizAnswerBuilder;
import fr.chaffotm.quizzify.builder.QuizEntityBuilder;
import fr.chaffotm.quizzify.entity.QuizEntity;
import fr.chaffotm.quizzify.resource.AnswerType;
import fr.chaffotm.quizzify.resource.QuizAnswer;
import fr.chaffotm.quizzify.resource.QuizResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class QuizAnswerCheckerTest {

    private QuizAnswerChecker checker;

    @BeforeEach
    public void setUp() {
        checker = new QuizAnswerChecker();
    }

    @Test
    @DisplayName("score should validate correct answer")
    public void scoreShouldValidateCorrectAnswer() {
        final QuizEntity quiz = new QuizEntityBuilder(AnswerType.MULTIPLE_CHOICE)
                .question("What is the capital of England?", "London")
                .getQuizEntity();
        final QuizAnswer quizAnswer = new QuizAnswerBuilder()
                .questionAnswer("London")
                .getQuizAnswer();

        final QuizResult result = checker.score(quiz, quizAnswer);

        assertThat(result.getNbOfCorrectAnswers()).isEqualTo(1);
        assertThat(result.getNbOfQuestions()).isEqualTo(1);
    }

    @Test
    @DisplayName("score should not validate incorrect answer")
    public void scoreShouldNotValidateIncorrectAnswer() {
        final QuizEntity quiz = new QuizEntityBuilder()
                .question("What is the capital of England?", "London")
                .getQuizEntity();
        final QuizAnswer quizAnswer = new QuizAnswerBuilder()
                .questionAnswer("Edinburgh")
                .getQuizAnswer();

        final QuizResult result = checker.score(quiz, quizAnswer);

        assertThat(result.getNbOfCorrectAnswers()).isEqualTo(0);
        assertThat(result.getNbOfQuestions()).isEqualTo(1);
    }

    @Test
    @DisplayName("score should ignore case")
    public void scoreShouldIgnoreCase() {
        final QuizEntity quiz = new QuizEntityBuilder()
                .question("What is the capital of England?", "London")
                .question("What is the capital of France?", "Paris")
                .question("What is the capital of Ireland?", "Dublin")
                .getQuizEntity();
        final QuizAnswer quizAnswer = new QuizAnswerBuilder()
                .questionAnswer("london")
                .questionAnswer("PARIS")
                .questionAnswer("Cork")
                .getQuizAnswer();

        final QuizResult result = checker.score(quiz, quizAnswer);

        assertThat(result.getNbOfCorrectAnswers()).isEqualTo(2);
        assertThat(result.getNbOfQuestions()).isEqualTo(3);
    }

    @Test
    @DisplayName("score should ignore accent")
    public void scoreShouldIgnoreAccent() {
        final QuizEntity quiz = new QuizEntityBuilder()
                .question("What is the lowest point of Argentina?", "Laguna del Carbón")
                .question("What is the capital of Moldova?", "Chișinău")
                .getQuizEntity();
        final QuizAnswer quizAnswer = new QuizAnswerBuilder()
                .questionAnswer("Laguna del Carbon")
                .questionAnswer("chisinau")
                .getQuizAnswer();

        final QuizResult result = checker.score(quiz, quizAnswer);

        assertThat(result.getNbOfCorrectAnswers()).isEqualTo(2);
        assertThat(result.getNbOfQuestions()).isEqualTo(2);
    }

    @Test
    @DisplayName("score should not validate with not all correct answers")
    public void scoreShouldNotValidateWithNotAllCorrectAnswers() {
        final QuizEntity quiz = new QuizEntityBuilder()
                .question("Which country has Mount Everest as highest point?", "China", "Nepal")
                .getQuizEntity();
        final QuizAnswer quizAnswer = new QuizAnswerBuilder()
                .questionAnswer("Nepal")
                .getQuizAnswer();
        final QuizResult result = checker.score(quiz, quizAnswer);

        assertThat(result.getNbOfCorrectAnswers()).isEqualTo(0);
        assertThat(result.getNbOfQuestions()).isEqualTo(1);
    }

    @Test
    @DisplayName("score should not validate with more than all correct answers")
    public void scoreShouldNotValidateWithMoreThanAllCorrectAnswers() {
        final QuizEntity quiz = new QuizEntityBuilder()
                .question("Which country has Mount Everest as highest point?", "China", "Nepal")
                .getQuizEntity();
        final QuizAnswer quizAnswer = new QuizAnswerBuilder()
                .questionAnswer("Nepal", "China", "India")
                .getQuizAnswer();
        final QuizResult result = checker.score(quiz, quizAnswer);

        assertThat(result.getNbOfCorrectAnswers()).isEqualTo(0);
        assertThat(result.getNbOfQuestions()).isEqualTo(1);
    }

}
