package fr.chaffotm.geobase.service;

import fr.chaffotm.geobase.repository.QuizRepository;
import fr.chaffotm.geoquiz.entity.ImageEntity;
import fr.chaffotm.geoquiz.service.descriptor.ImageType;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.InputStream;
import java.util.UUID;

@Service
@Transactional
public class ImageService {

    private final QuizRepository quizRepository;

    public ImageService(final QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public InputStream getImageStream(final UUID imageUuid) {
        final ImageEntity image = quizRepository.getQuestionImage(imageUuid);
        final String fileName = image.getCountry().getCode().toLowerCase() + ".png";
        final StringBuilder pathBuilder = new StringBuilder();
        if (ImageType.FLAG == image.getImageType()) {
            pathBuilder.append("flags");
        } else if (ImageType.SILHOUETTE == image.getImageType()) {
            pathBuilder.append("silhouettes");
        }
        pathBuilder.append("/").append(fileName);
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        return classLoader.getResourceAsStream(pathBuilder.toString());
    }

}
