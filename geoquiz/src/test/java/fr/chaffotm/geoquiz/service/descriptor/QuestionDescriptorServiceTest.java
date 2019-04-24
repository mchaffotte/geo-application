package fr.chaffotm.geoquiz.service.descriptor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class QuestionDescriptorServiceTest {

    @Test
    @DisplayName("getDescriptor should throw an exception if question type is null")
    public void getDescriptorShouldThrowAnExceptionIfQuestionTypeIsNull() {
        final QuestionDescriptorService service = new QuestionDescriptorService();

        final Throwable thrown = catchThrowable(() -> service.getDescriptor(null));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

}
