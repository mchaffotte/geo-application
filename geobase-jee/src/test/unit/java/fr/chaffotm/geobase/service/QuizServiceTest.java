package fr.chaffotm.geobase.service;

import fr.chaffotm.geobase.domain.QuestionEntity;
import fr.chaffotm.geobase.domain.QuizEntity;
import fr.chaffotm.geobase.repository.QuizMakerRepository;
import fr.chaffotm.geobase.repository.QuizRepository;
import fr.chaffotm.geobase.web.domain.QuizAnswers;
import fr.chaffotm.geobase.web.domain.QuizResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class QuizServiceTest {

    @Mock
    private QuizRepository quizRepository;

    @Mock
    private QuizMakerRepository quizMakerRepository;

    @InjectMocks
    private QuizService quizService;

    @Test
    public void answer_should_validate_correct_answer() {
        final long quizId = 465;
        final QuizEntity quiz = new QuizEntity();
        quiz.addQuestion(build("London"));

        when(quizRepository.get(quizId)).thenReturn(quiz);
        final QuizAnswers quizAnswers = new QuizAnswers();
        quizAnswers.setAnswers(Collections.singletonList("London"));

        final QuizResult result = quizService.answer(quizId, quizAnswers);
        assertThat(result.getNbOfCorrectAnswers()).isEqualTo(1);
        assertThat(result.getNbOfQuestions()).isEqualTo(1);
    }

    @Test
    public void answer_should_not_validate_incorrect_answer() {
        final long quizId = 465;
        final QuizEntity quiz = new QuizEntity();
        quiz.addQuestion(build("London"));

        when(quizRepository.get(quizId)).thenReturn(quiz);
        final QuizAnswers quizAnswers = new QuizAnswers();
        quizAnswers.setAnswers(Collections.singletonList("Edinburgh"));

        final QuizResult result = quizService.answer(quizId, quizAnswers);
        assertThat(result.getNbOfCorrectAnswers()).isEqualTo(0);
        assertThat(result.getNbOfQuestions()).isEqualTo(1);
    }

    @Test
    public void answer_should_ignore_case() {
        final long quizId = 465;
        final QuizEntity quiz = new QuizEntity();
        quiz.addQuestion(build("London"));
        quiz.addQuestion(build("Paris"));
        quiz.addQuestion(build("Dublin"));

        when(quizRepository.get(quizId)).thenReturn(quiz);
        final QuizAnswers quizAnswers = new QuizAnswers();
        quizAnswers.setAnswers(Arrays.asList("london", "PARIS", "Cork"));

        final QuizResult result = quizService.answer(quizId, quizAnswers);
        assertThat(result.getNbOfCorrectAnswers()).isEqualTo(2);
        assertThat(result.getNbOfQuestions()).isEqualTo(3);
    }

    @Test
    public void answer_should_ignore_accent() {
        final long quizId = 465;
        final QuizEntity quiz = new QuizEntity();
        quiz.addQuestion(build("Laguna del Carbón"));

        when(quizRepository.get(quizId)).thenReturn(quiz);
        final QuizAnswers quizAnswers = new QuizAnswers();
        quizAnswers.setAnswers(Arrays.asList("Laguna del Carbon"));

        final QuizResult result = quizService.answer(quizId, quizAnswers);
        assertThat(result.getNbOfCorrectAnswers()).isEqualTo(1);
        assertThat(result.getNbOfQuestions()).isEqualTo(1);
    }

    private QuestionEntity build(String answer) {
        final QuestionEntity question = new QuestionEntity();
        question.setAnswer(answer);
        return question;
    }

}