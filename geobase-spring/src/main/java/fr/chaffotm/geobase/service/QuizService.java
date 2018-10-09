package fr.chaffotm.geobase.service;

import fr.chaffotm.geobase.domain.QuizEntity;
import fr.chaffotm.geobase.mapper.QuizMapper;
import fr.chaffotm.geobase.repository.QuizMakerRepository;
import fr.chaffotm.geobase.repository.QuizRepository;
import fr.chaffotm.geobase.service.quiz.QuizResponseChecker;
import fr.chaffotm.geobase.web.domain.Quiz;
import fr.chaffotm.geobase.web.domain.QuizResponse;
import fr.chaffotm.geobase.web.domain.QuizConfiguration;
import fr.chaffotm.geobase.web.domain.QuizResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class QuizService {

    private final QuizRepository quizRepository;

    private final QuizMakerRepository quizMakerRepository;

    private final QuizResponseChecker checker;

    public QuizService(final QuizRepository quizRepository, final QuizMakerRepository quizMakerRepository) {
        this.quizRepository = quizRepository;
        this.quizMakerRepository = quizMakerRepository;
        checker = new QuizResponseChecker();
    }

    public long create(final QuizConfiguration configuration) {
        final QuizEntity newQuiz = quizMakerRepository.build(configuration);
        final QuizEntity createdQuiz = quizRepository.create(newQuiz);
        return createdQuiz.getId();
    }

    public Quiz get(final long id, final String baseUri) {
        final QuizEntity entity = quizRepository.get(id);
        return QuizMapper.map(entity, baseUri);
    }

    public QuizResult answer(final long id, final QuizResponse response) {
        final QuizEntity quizEntity = quizRepository.get(id);
        return checker.score(quizEntity, response);
    }

}
