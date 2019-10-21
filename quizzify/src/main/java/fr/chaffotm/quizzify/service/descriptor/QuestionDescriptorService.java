package fr.chaffotm.quizzify.service.descriptor;

import java.util.Map;

public interface QuestionDescriptorService {

    Map<String, QuestionDescriptor> getDescriptors();

    <T> QuestionDescriptor<T> getDescriptor(final String questionType);

}
