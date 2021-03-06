package fr.chaffotm.geobase.service;

import fr.chaffotm.quizzify.service.descriptor.*;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class QuestionDescriptorServiceImpl implements QuestionDescriptorService {

    private final Map<String, QuestionDescriptor<?>> descriptors;

    public QuestionDescriptorServiceImpl() {
        descriptors = List.of(new CapitalQuestionDescriptor(),
                new TotalAreaQuestionDescriptor(),
                new LandAreaQuestionDescriptor(),
                new WaterAreaQuestionDescriptor(),
                new FlagQuestionDescriptor(),
                new SilhouetteQuestionDescriptor())
                .stream()
                .collect(Collectors.toMap(QuestionDescriptor::getQuestionType, o -> o));
    }

    @Override
    public Map<String, QuestionDescriptor<?>> getDescriptors() {
        return descriptors;
    }

    @Override
    public <T> QuestionDescriptor<T> getDescriptor(final String questionType) {
        if (questionType == null) {
            throw new IllegalArgumentException("Question type is null");
        }
        final QuestionDescriptor descriptor = descriptors.get(questionType);
        if (descriptor == null) {
            throw new IllegalArgumentException("Unknown question type:" + questionType);
        }
        return descriptor;
    }

}
