package fr.chaffotm.geobase.service.quiz.descriptor;

import fr.chaffotm.geobase.web.domain.QuestionType;

public class QuestionDescriptorFactory {

    public QuestionDescriptor getDescriptor(final QuestionType questionType) {
        final QuestionDescriptor descriptor;
        if(QuestionType.CAPITAL.equals(questionType)) {
            descriptor = new CapitalQuestionDescriptor();
        } else if (QuestionType.TOTAL_AREA.equals(questionType)) {
            descriptor = new TotalAreaQuestionDescriptor();
        } else if (QuestionType.LAND_AREA.equals(questionType)) {
            descriptor = new LandAreaQuestionDescriptor();
        } else if (QuestionType.WATER_AREA.equals(questionType)) {
            descriptor = new WaterAreaQuestionDescriptor();
        } else if (QuestionType.FLAG.equals(questionType)) {
            descriptor = new FlagQuestionDescriptor();
        } else if (QuestionType.SILHOUETTE.equals(questionType)) {
            descriptor = new SilhouetteQuestionDescriptor();
        } else {
            throw new IllegalArgumentException("Unknown question type:" + questionType);
        }
        return descriptor;
    }

}
