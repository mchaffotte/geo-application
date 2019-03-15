package fr.chaffotm.geoquiz.entity.converter;

import fr.chaffotm.geoquiz.service.descriptor.ImageType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ImageTypeAttributeConverter implements AttributeConverter<ImageType, String> {

    @Override
    public String convertToDatabaseColumn(final ImageType imageType) {
        return imageType.name().toLowerCase();
    }

    @Override
    public ImageType convertToEntityAttribute(final String s) {
        return ImageType.valueOf(s.toUpperCase());
    }

}
