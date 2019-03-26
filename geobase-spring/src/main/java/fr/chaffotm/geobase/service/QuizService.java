package fr.chaffotm.geobase.service;

import fr.chaffotm.geobase.repository.QuizMakerRepository;
import fr.chaffotm.geobase.repository.QuizRepository;
import fr.chaffotm.geoquiz.entity.QuizEntity;
import fr.chaffotm.geoquiz.mapper.QuizMapper;
import fr.chaffotm.geoquiz.resource.*;
import fr.chaffotm.geoquiz.service.ColumnType;
import fr.chaffotm.geoquiz.service.QuizAnswerChecker;
import fr.chaffotm.geoquiz.service.descriptor.QuestionDescriptor;
import fr.chaffotm.geoquiz.service.descriptor.QuestionDescriptorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class QuizService {

    private final QuizRepository quizRepository;

    private final QuizMakerRepository quizMakerRepository;

    private final QuizAnswerChecker checker;

    private final QuestionDescriptorService questionService;

    public QuizService(final QuizRepository quizRepository, final QuizMakerRepository quizMakerRepository) {
        this.quizRepository = quizRepository;
        this.quizMakerRepository = quizMakerRepository;
        checker = new QuizAnswerChecker();
        questionService = new QuestionDescriptorService();
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

    public QuizResult answer(final long id, final QuizAnswer quizAnswer) {
        final QuizEntity quizEntity = quizRepository.get(id);
        return checker.score(quizEntity, quizAnswer);
    }

    public List<QuizType> getQuizTypes() {
        final Map<QuestionType, QuestionDescriptor> descriptors = questionService.getDescriptors();
        final List<QuizType> quizTypes = new ArrayList<>();
        for (Map.Entry<QuestionType, QuestionDescriptor> descriptor : descriptors.entrySet()) {
            final List<AnswerType> answerTypes = new ArrayList<>();
            if (!ColumnType.NUMERIC.equals(descriptor.getValue().getAttributeColumnType())) {
                answerTypes.add(AnswerType.ANSWER);
            }
            answerTypes.add(AnswerType.MULTIPLE_CHOICE);
            final QuizType quizType = new QuizType();
            quizType.setQuestionType(descriptor.getKey());
            quizType.setAnswerTypes(answerTypes);
            quizTypes.add(quizType);
        }
        return quizTypes;
    }

}
