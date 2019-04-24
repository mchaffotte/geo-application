package fr.chaffotm.geobase.service;

import fr.chaffotm.geobase.repository.QuizRepository;
import fr.chaffotm.geoquiz.builder.QuizAnswerBuilder;
import fr.chaffotm.geoquiz.resource.QuizAnswer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class QuizServiceTest {

    @Mock
    private QuizRepository quizRepository;

    @InjectMocks
    private QuizService quizService;

    @Test
    @DisplayName("answer should fail if id is unknown")
    public void answerShouldFailIfIdIsUnknown() {
        final long quizId = 465;
        when(quizRepository.get(quizId)).thenThrow(EntityNotFoundException.class);
        final QuizAnswer quizAnswer = new QuizAnswerBuilder()
                .questionAnswer("London")
                .getQuizAnswer();

        final Throwable thrown = catchThrowable(() -> quizService.answer(quizId, quizAnswer));

        assertThat(thrown).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("answer should fail if quiz is null")
    public void answerShouldFailIfQuizIsNull() {
        final long quizId = 465;
        when(quizRepository.get(quizId)).thenReturn(null);
        final QuizAnswer quizAnswer = new QuizAnswerBuilder()
                .questionAnswer("London")
                .getQuizAnswer();

        final Throwable thrown = catchThrowable(() -> quizService.answer(quizId, quizAnswer));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

}
