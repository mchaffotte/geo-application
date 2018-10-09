package fr.chaffotm.geobase.service.quiz;

import fr.chaffotm.geobase.domain.QuestionEntity;
import fr.chaffotm.geobase.domain.QuizEntity;
import fr.chaffotm.geobase.web.domain.QuizResponse;
import fr.chaffotm.geobase.web.domain.QuizResult;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class QuizResponseCheckerTest {

    private QuizResponseChecker checker;

    @Before
    public void setUp() {
        checker = new QuizResponseChecker();
    }

    @Test
    public void score_should_validate_correct_answer() {
        final QuizEntity quiz = new QuizEntity();
        quiz.addQuestion(build("London"));
        final QuizResponse quizResponse = new QuizResponse();
        quizResponse.setAnswers(Collections.singletonList("London"));

        final QuizResult result = checker.score(quiz, quizResponse);

        assertThat(result.getNbOfCorrectAnswers()).isEqualTo(1);
        assertThat(result.getNbOfQuestions()).isEqualTo(1);
    }

    @Test
    public void score_should_not_validate_incorrect_answer() {
        final QuizEntity quiz = new QuizEntity();
        quiz.addQuestion(build("London"));
        final QuizResponse quizResponse = new QuizResponse();
        quizResponse.setAnswers(Collections.singletonList("Edinburgh"));

        final QuizResult result = checker.score(quiz, quizResponse);
        assertThat(result.getNbOfCorrectAnswers()).isEqualTo(0);
        assertThat(result.getNbOfQuestions()).isEqualTo(1);
    }

    @Test
    public void score_should_ignore_case() {
        final QuizEntity quiz = new QuizEntity();
        quiz.addQuestion(build("London"));
        quiz.addQuestion(build("Paris"));
        quiz.addQuestion(build("Dublin"));
        final QuizResponse quizResponse = new QuizResponse();
        quizResponse.setAnswers(Arrays.asList("london", "PARIS", "Cork"));

        final QuizResult result = checker.score(quiz, quizResponse);
        assertThat(result.getNbOfCorrectAnswers()).isEqualTo(2);
        assertThat(result.getNbOfQuestions()).isEqualTo(3);
    }

    @Test
    public void score_should_ignore_accent() {
        final QuizEntity quiz = new QuizEntity();
        quiz.addQuestion(build("Laguna del Carbón"));
        final QuizResponse quizResponse = new QuizResponse();
        quizResponse.setAnswers(Collections.singletonList("Laguna del Carbon"));

        final QuizResult result = checker.score(quiz, quizResponse);
        assertThat(result.getNbOfCorrectAnswers()).isEqualTo(1);
        assertThat(result.getNbOfQuestions()).isEqualTo(1);
    }

    private QuestionEntity build(String answer) {
        final QuestionEntity question = new QuestionEntity();
        question.setAnswer(answer);
        return question;
    }

}
