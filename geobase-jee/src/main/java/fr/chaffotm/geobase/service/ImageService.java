package fr.chaffotm.geobase.service;

import fr.chaffotm.geobase.domain.ImageEntity;
import fr.chaffotm.geobase.repository.QuizRepository;
import fr.chaffotm.geobase.service.quiz.descriptor.ImageType;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.InputStream;
import java.util.UUID;

@RequestScoped
@Transactional
public class ImageService {

    private final QuizRepository quizRepository;

    // Used by CDI
    protected ImageService() {
        this(null);
    }

    @Inject
    public ImageService(final QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public InputStream getImageStream(final UUID imageUuid) {
        final ImageEntity image = quizRepository.getQuestionImage(imageUuid);
        final String fileName = image.getCountry().getCode().toLowerCase() + ".png";
        final StringBuilder pathBuilder = new StringBuilder("/");
        if (ImageType.FLAG.equals(image.getImageType())) {
            pathBuilder.append("flags");
        } else if (ImageType.SILHOUETTE.equals(image.getImageType())) {
            pathBuilder.append("silhouettes");
        }
        pathBuilder.append("/").append(fileName);
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        return classLoader.getResourceAsStream(pathBuilder.toString());
    }

}
