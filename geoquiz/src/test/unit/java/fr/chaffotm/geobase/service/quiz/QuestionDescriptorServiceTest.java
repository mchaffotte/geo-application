package fr.chaffotm.geobase.service.quiz;

import fr.chaffotm.geobase.service.quiz.descriptor.QuestionDescriptorService;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class QuestionDescriptorServiceTest {

    @Test
    public void getDescriptor_should_throw_an_exception_if_question_type_is_null() {
        final QuestionDescriptorService service = new QuestionDescriptorService();

        final Throwable thrown = catchThrowable(() -> service.getDescriptor(null));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

}