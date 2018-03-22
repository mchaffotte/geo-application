package fr.chaffotm.geobase.service.quiz;

import fr.chaffotm.geobase.domain.CountryEntity;
import fr.chaffotm.geobase.domain.QuestionEntity;
import fr.chaffotm.geobase.domain.QuizEntity;
import fr.chaffotm.geobase.repository.CountryRepository;
import fr.chaffotm.geobase.repository.Order;
import fr.chaffotm.geobase.repository.Sort;
import fr.chaffotm.geobase.service.quiz.descriptor.QuestionDescriptor;
import fr.chaffotm.geobase.service.quiz.descriptor.QuestionDescriptorFactory;
import fr.chaffotm.geobase.service.quiz.generator.Generator;
import fr.chaffotm.geobase.service.quiz.generator.GeneratorFactory;
import fr.chaffotm.geobase.web.domain.QuestionType;
import fr.chaffotm.geobase.web.domain.QuizConfiguration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuizMaker {

    private static final Random RANDOM = new Random(System.currentTimeMillis());

    private final CountryRepository countryRepository;

    private final QuestionDescriptorFactory descriptorFactory;

    private final GeneratorFactory generatorFactory;

   public QuizMaker(final CountryRepository countryRepository) {
       this.countryRepository = countryRepository;
       descriptorFactory = new QuestionDescriptorFactory();
       generatorFactory = new GeneratorFactory();
   }

   public QuizEntity build(final QuizConfiguration configuration) {
       QuizConfiguration quizConfiguration = configuration;
       if (quizConfiguration == null) {
           quizConfiguration = buildDefaultConfiguration();
       }
       final QuestionDescriptor descriptor = descriptorFactory.getDescriptor(quizConfiguration.getQuestionType());
       final Generator generator = generatorFactory.getGenerator(descriptor.getAttributeColumnType());

       final Sort sort = new Sort(descriptor.getSortColumn(), Order.DESC);
       final List<CountryEntity> countries = countryRepository.findAll(1, null, Collections.singletonList(sort));
       final List<MultipleChoice> multipleChoices = generator.generate(countries);
       final QuizEntity quiz = new QuizEntity();
       for (MultipleChoice multipleChoice : multipleChoices) {
           QuestionEntity question = buildQuestion(descriptor, multipleChoice);
           quiz.addQuestion(question);
       }
       return quiz;
   }

   private QuizConfiguration buildDefaultConfiguration() {
       QuizConfiguration configuration = new QuizConfiguration();
       configuration.setQuestionType(QuestionType.CAPITAL);
       return  configuration;
   }

   private QuestionEntity buildQuestion(final QuestionDescriptor descriptor, final MultipleChoice multipleChoice) {
       final CountryEntity answer = multipleChoice.getAnswer();
       final List<CountryEntity> possibleCountries = new ArrayList<>(multipleChoice.getDistractors());
       possibleCountries.add(answer);
       Collections.shuffle(possibleCountries, RANDOM);
       final QuestionEntity question = new QuestionEntity();
       question.setAnswer(descriptor.getAttributeValue(answer));
       question.setWording(descriptor.getQuestion(answer));
       for (CountryEntity possibleCountry : possibleCountries) {
           question.addSuggestion(descriptor.getAttributeValue(possibleCountry));
       }
       return question;
   }

}

