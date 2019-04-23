package fr.chaffotm.geoquiz.service.descriptor;

import fr.chaffotm.geoquiz.resource.QuestionType;

import java.util.Map;

public class QuestionDescriptorService {

    private Map<QuestionType, QuestionDescriptor> descriptors;

    public QuestionDescriptorService() {
        descriptors = Map.of(
                QuestionType.CAPITAL, new CapitalQuestionDescriptor(),
                QuestionType.TOTAL_AREA, new TotalAreaQuestionDescriptor(),
                QuestionType.LAND_AREA, new LandAreaQuestionDescriptor(),
                QuestionType.WATER_AREA, new WaterAreaQuestionDescriptor(),
                QuestionType.FLAG, new FlagQuestionDescriptor(),
                QuestionType.SILHOUETTE, new SilhouetteQuestionDescriptor()
        );
    }

    public Map<QuestionType, QuestionDescriptor> getDescriptors() {
        return descriptors;
    }

    public QuestionDescriptor getDescriptor(final QuestionType questionType) {
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
