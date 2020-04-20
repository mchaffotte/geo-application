package fr.chaffotm.geobase.service;

import fr.chaffotm.geobase.repository.QueryCriteriaRepository;
import fr.chaffotm.geobase.repository.QuizRepository;
import fr.chaffotm.quizzify.entity.QuizEntity;
import fr.chaffotm.quizzify.mapper.QuizMapper;
import fr.chaffotm.quizzify.resource.*;
import fr.chaffotm.quizzify.service.ColumnType;
import fr.chaffotm.quizzify.service.QuizAnswerChecker;
import fr.chaffotm.quizzify.service.QuizMaker;
import fr.chaffotm.quizzify.service.descriptor.QuestionDescriptor;
import fr.chaffotm.quizzify.service.descriptor.QuestionDescriptorService;

import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequestScoped
@Transactional
public class QuizService {

    private final QuizRepository quizRepository;

    private final QuizMaker quizMaker;

    private final QuizAnswerChecker checker;

    private final QuestionDescriptorService questionService;

    public QuizService(final QuizRepository quizRepository, final QuestionDescriptorService questionService,
                       final QueryCriteriaRepository queryCriteriaRepository) {
        this.quizRepository = quizRepository;
        this.questionService = questionService;
        quizMaker = new QuizMaker(queryCriteriaRepository, questionService);
        checker = new QuizAnswerChecker();
    }

    public long create(final QuizConfiguration configuration) {
        final QuizEntity newQuiz = quizMaker.build(configuration);
        final QuizEntity createdQuiz = quizRepository.create(newQuiz);
        return createdQuiz.getId();
    }

    public Quiz get(final long id, final UriInfo uriInfo) {
        final QuizEntity entity = quizRepository.get(id);
        return QuizMapper.map(entity, uriInfo.getBaseUri().toString());
    }

    public QuizResult answer(final long id, final QuizAnswer quizAnswer) {
        final QuizEntity quizEntity = quizRepository.get(id);
        return checker.score(quizEntity, quizAnswer);
    }

    public List<QuizType> getQuizTypes() {
        final Map<String, QuestionDescriptor> descriptors = questionService.getDescriptors();
        final List<QuizType> quizTypes = new ArrayList<>();
        for (Map.Entry<String, QuestionDescriptor> descriptor : descriptors.entrySet()) {
            final List<AnswerType> answerTypes = new ArrayList<>();
            if (ColumnType.NUMERIC != descriptor.getValue().getAttributeColumnType()) {
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
