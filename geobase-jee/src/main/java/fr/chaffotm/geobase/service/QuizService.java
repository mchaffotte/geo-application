package fr.chaffotm.geobase.service;

import fr.chaffotm.geobase.domain.QuestionEntity;
import fr.chaffotm.geobase.domain.QuizEntity;
import fr.chaffotm.geobase.mapper.QuizMapper;
import fr.chaffotm.geobase.repository.QuizMakerRepository;
import fr.chaffotm.geobase.repository.QuizRepository;
import fr.chaffotm.geobase.web.domain.Quiz;
import fr.chaffotm.geobase.web.domain.QuizAnswers;
import fr.chaffotm.geobase.web.domain.QuizConfiguration;
import fr.chaffotm.geobase.web.domain.QuizResult;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@RequestScoped
@Transactional
public class QuizService {

    private final QuizRepository quizRepository;

    private final QuizMakerRepository quizMakerRepository;

    // Used by CDI
    protected QuizService() {
        this(null, null);
    }

    @Inject
    public QuizService(final QuizRepository quizRepository, final QuizMakerRepository quizMakerRepository) {
        this.quizRepository = quizRepository;
        this.quizMakerRepository = quizMakerRepository;
    }

    public long create(final QuizConfiguration configuration) {
        final QuizEntity newQuiz = quizMakerRepository.build(configuration);
        final QuizEntity createdQuiz = quizRepository.create(newQuiz);
        return createdQuiz.getId();
    }

    public Quiz get(final long id, final UriInfo uriInfo) {
        final QuizEntity entity = quizRepository.get(id);
        return QuizMapper.map(entity, uriInfo.getBaseUri().toString());
    }

    public QuizResult answer(final long id, final QuizAnswers answers) {
        final QuizEntity quizEntity = quizRepository.get(id);
        final List<QuestionEntity> questions = quizEntity.getQuestions();
        final List<String> answers1 = answers.getAnswers();
        int nbOfCorrectAnswers = 0;
        for (int i = 0; i < questions.size(); i++) {
            final QuestionEntity question = questions.get(i);
            final String answer = answers1.get(i);
            if (question.getAnswer().equals(answer)) {
                nbOfCorrectAnswers++;
            }
        }
        final QuizResult result = new QuizResult();
        result.setNbOfCorrectAnswers(nbOfCorrectAnswers);
        result.setNbOfQuestions(questions.size());
        return result;
    }

}
