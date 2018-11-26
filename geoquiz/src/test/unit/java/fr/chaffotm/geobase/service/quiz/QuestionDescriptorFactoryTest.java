package fr.chaffotm.geobase.service.quiz;

import fr.chaffotm.geobase.service.quiz.descriptor.QuestionDescriptorFactory;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class QuestionDescriptorFactoryTest {

    @Test
    public void getDescriptor_should_throw_an_exception_if_question_type_is_null() {
        final QuestionDescriptorFactory factory = new QuestionDescriptorFactory();

        final Throwable thrown = catchThrowable(() -> factory.getDescriptor(null));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

}