package fr.chaffotm.geoquiz.service;

import fr.chaffotm.geoquiz.builder.QuizAnswerBuilder;
import fr.chaffotm.geoquiz.builder.QuizEntityBuilder;
import fr.chaffotm.geoquiz.entity.QuizEntity;
import fr.chaffotm.geoquiz.resource.AnswerType;
import fr.chaffotm.geoquiz.resource.QuizAnswer;
import fr.chaffotm.geoquiz.resource.QuizResult;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class QuizAnswerCheckerTest {

    private QuizAnswerChecker checker;

    @Before
    public void setUp() {
        checker = new QuizAnswerChecker();
    }

    @Test
    public void score_should_validate_correct_answer() {
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
    public void score_should_not_validate_incorrect_answer() {
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
    public void score_should_ignore_case() {
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
    public void score_should_ignore_accent() {
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
    public void score_should_not_validate_with_not_all_correct_answers() {
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
    public void score_should_not_validate_with_more_than_all_correct_answers() {
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
