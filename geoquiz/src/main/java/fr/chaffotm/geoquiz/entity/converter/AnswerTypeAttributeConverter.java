package fr.chaffotm.geoquiz.entity.converter;

import fr.chaffotm.geoquiz.resource.AnswerType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

@Converter(autoApply = true)
public class AnswerTypeAttributeConverter implements AttributeConverter<AnswerType, String> {

    private static final Map<String, AnswerType> STRING_ANSWER_TYPE_MAP;

    private static final Map<AnswerType, String> ANSWER_TYPE_STRING_MAP;

    static {
        STRING_ANSWER_TYPE_MAP = new HashMap<>();
        STRING_ANSWER_TYPE_MAP.put("AN", AnswerType.ANSWER);
        STRING_ANSWER_TYPE_MAP.put("MC", AnswerType.MULTIPLE_CHOICE);

        ANSWER_TYPE_STRING_MAP = new EnumMap<>(AnswerType.class);
        for (Map.Entry<String, AnswerType> typeEntry : STRING_ANSWER_TYPE_MAP.entrySet()) {
            ANSWER_TYPE_STRING_MAP.put(typeEntry.getValue(), typeEntry.getKey());
        }
    }

    @Override
    public String convertToDatabaseColumn(final AnswerType attribute) {
        if (attribute == null) {
            return null;
        }
        final String state = ANSWER_TYPE_STRING_MAP.get(attribute);
        if (state == null) {
            throw new IllegalArgumentException("Unknown Answer Type" + attribute);
        }
        return state;
    }

    @Override
    public AnswerType convertToEntityAttribute(final String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }
        final AnswerType answerType = STRING_ANSWER_TYPE_MAP.get(dbData);
        if (answerType == null) {
            throw new IllegalArgumentException("Unknown db answer type " + dbData);
        }
        return answerType;
    }

}
