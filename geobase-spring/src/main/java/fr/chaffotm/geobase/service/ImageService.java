package fr.chaffotm.geobase.service;

import fr.chaffotm.geobase.repository.QuizRepository;
import fr.chaffotm.quizzify.entity.ImageEntity;
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
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        return classLoader.getResourceAsStream("/" + image.getFilename());
    }

}
