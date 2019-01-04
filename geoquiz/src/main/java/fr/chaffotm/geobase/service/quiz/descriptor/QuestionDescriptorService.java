package fr.chaffotm.geobase.service.quiz.descriptor;

import fr.chaffotm.geobase.web.domain.QuestionType;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

public class QuestionDescriptorService {

    private Map<QuestionType, QuestionDescriptor> descriptors;

    public QuestionDescriptorService() {
        descriptors = new EnumMap<>(QuestionType.class);
        descriptors.put(QuestionType.CAPITAL, new CapitalQuestionDescriptor());
        descriptors.put(QuestionType.TOTAL_AREA, new TotalAreaQuestionDescriptor());
        descriptors.put(QuestionType.LAND_AREA, new LandAreaQuestionDescriptor());
        descriptors.put(QuestionType.WATER_AREA, new WaterAreaQuestionDescriptor());
        descriptors.put(QuestionType.FLAG, new FlagQuestionDescriptor());
        descriptors.put(QuestionType.SILHOUETTE, new SilhouetteQuestionDescriptor());
    }

    public Map<QuestionType, QuestionDescriptor> getDescriptors() {
        return Collections.unmodifiableMap(descriptors);
    }

    public QuestionDescriptor getDescriptor(final QuestionType questionType) {
        final QuestionDescriptor descriptor = descriptors.get(questionType);
        if (descriptor == null) {
            throw new IllegalArgumentException("Unknown question type:" + questionType);
        }
        return descriptor;
    }

}
