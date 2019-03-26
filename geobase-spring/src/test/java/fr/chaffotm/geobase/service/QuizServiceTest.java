package fr.chaffotm.geobase.service;

import fr.chaffotm.geobase.repository.QuizRepository;
import fr.chaffotm.geoquiz.resource.QuizAnswer;
import fr.chaffotm.geoquiz.builder.QuizAnswerBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class QuizServiceTest {

    @Mock
    private QuizRepository quizRepository;

    @InjectMocks
    private QuizService quizService;

    @Test
    public void answer_should_fail_if_id_is_unknown() {
        final long quizId = 465;
        when(quizRepository.get(quizId)).thenThrow(EntityNotFoundException.class);
        final QuizAnswer quizAnswer = new QuizAnswerBuilder()
                .questionAnswer("London")
                .getQuizAnswer();

        final Throwable thrown = catchThrowable(() -> quizService.answer(quizId, quizAnswer));

        assertThat(thrown).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    public void answer_should_fail_if_quiz_is_null() {
        final long quizId = 465;
        when(quizRepository.get(quizId)).thenReturn(null);
        final QuizAnswer quizAnswer = new QuizAnswerBuilder()
                .questionAnswer("London")
                .getQuizAnswer();

        final Throwable thrown = catchThrowable(() -> quizService.answer(quizId, quizAnswer));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

}