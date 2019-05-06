package fr.chaffotm.geobase.service;

import fr.chaffotm.quizzify.service.descriptor.QuestionDescriptorService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class QuestionDescriptorServiceImplTest {

    @Test
    @DisplayName("getDescriptor should throw an exception if question type is null")
    public void getDescriptorShouldThrowAnExceptionIfQuestionTypeIsNull() {
        final QuestionDescriptorService service = new QuestionDescriptorServiceImpl();

        final Throwable thrown = catchThrowable(() -> service.getDescriptor(null));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

}
