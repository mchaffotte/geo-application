package fr.chaffotm.geobase.service.quiz;

import fr.chaffotm.geobase.service.quiz.descriptor.QuestionDescriptorFactory;
import org.junit.Test;

public class QuestionDescriptorFactoryTest {

    @Test(expected = IllegalArgumentException.class)
    public void getDescriptor_should_throw_an_exception_if_question_type_is_null() {
        //Given
        final QuestionDescriptorFactory factory = new QuestionDescriptorFactory();
        //When
        factory.getDescriptor(null);
    }

}