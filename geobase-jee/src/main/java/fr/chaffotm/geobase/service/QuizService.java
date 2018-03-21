package fr.chaffotm.geobase.service;

import fr.chaffotm.geobase.domain.QuestionEntity;
import fr.chaffotm.geobase.domain.QuizEntity;
import fr.chaffotm.geobase.mapper.QuizMapper;
import fr.chaffotm.geobase.repository.QuizRepository;
import fr.chaffotm.geobase.web.domain.Country;
import fr.chaffotm.geobase.web.domain.Quiz;
import fr.chaffotm.geobase.web.domain.QuizAnswers;
import fr.chaffotm.geobase.web.domain.QuizResult;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@RequestScoped
@Transactional
public class QuizService {

    private static final Random RANDOM = new Random(System.currentTimeMillis());

    private final CountryService countryService;

    private final QuizRepository quizRepository;

    // Used by CDI
    protected QuizService() {
        this(null, null);
    }

    @Inject
    public QuizService(final CountryService countryService, final QuizRepository quizRepository) {
        this.countryService = countryService;
        this.quizRepository = quizRepository;
    }

    public long create() {
        final QuizEntity quiz = new QuizEntity();
        final List<Long> excludeIds = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            final Country mainCountry = countryService.getRandom(excludeIds);
            excludeIds.add(mainCountry.getId());

            final List<Country> possibleCountries = countryService.findRandom(3, mainCountry.getId());
            possibleCountries.add(mainCountry);
            Collections.shuffle(possibleCountries, RANDOM);

            final QuestionEntity question = new QuestionEntity();
            question.setAnswer(mainCountry.getCapital().getName());
            question.setWording("What is the capital name of " + mainCountry.getName() + "?");
            for (Country country : possibleCountries) {
                question.addSuggestion(country.getCapital().getName());
            }
            quiz.addQuestion(question);
        }
        final QuizEntity quizEntity = quizRepository.create(quiz);
        return quizEntity.getId();
    }

    public Quiz get(final long id) {
        final QuizEntity entity = quizRepository.get(id);
        return QuizMapper.map(entity);
    }

    public QuizResult answer(final long id, final QuizAnswers answers) {
        final QuizEntity quizEntity = quizRepository.get(id);

        final List<QuestionEntity> questions = quizEntity.getQuestions();
        final List<String> answers1 = answers.getAnswers();
        int rightAnswers = 0;
        for (int i = 0; i < questions.size(); i++) {
            final QuestionEntity question = questions.get(i);
            final String answer = answers1.get(i);
            if (question.getAnswer().equals(answer)) {
                rightAnswers++;
            }
        }
        final QuizResult result = new QuizResult();
        result.setNbOfRightAnswers(rightAnswers);
        result.setTotalNumberOfQuestions(questions.size());
        return result;
    }

}
