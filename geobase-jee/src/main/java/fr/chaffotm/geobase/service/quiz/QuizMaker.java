package fr.chaffotm.geobase.service.quiz;

import fr.chaffotm.geobase.domain.CountryEntity;
import fr.chaffotm.geobase.domain.QuestionEntity;
import fr.chaffotm.geobase.domain.QuizEntity;
import fr.chaffotm.geobase.repository.CountryRepository;
import fr.chaffotm.geobase.repository.Order;
import fr.chaffotm.geobase.repository.Sort;
import fr.chaffotm.geobase.service.quiz.descriptor.ImageType;
import fr.chaffotm.geobase.service.quiz.descriptor.QuestionDescriptor;
import fr.chaffotm.geobase.service.quiz.descriptor.QuestionDescriptorFactory;
import fr.chaffotm.geobase.service.quiz.generator.Generator;
import fr.chaffotm.geobase.service.quiz.generator.GeneratorFactory;
import fr.chaffotm.geobase.web.domain.QuestionType;
import fr.chaffotm.geobase.web.domain.QuizConfiguration;

import java.util.*;

public class QuizMaker {

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
       final List<MultipleChoice> multipleChoices = generator.generate(countries, quizConfiguration.isMultipleChoice());
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
       configuration.setMultipleChoice(true);
       return  configuration;
   }

   private QuestionEntity buildQuestion(final QuestionDescriptor descriptor, final MultipleChoice multipleChoice) {
       final CountryEntity answer = multipleChoice.getAnswer();
       final QuestionEntity question = new QuestionEntity();
       question.setAnswer(descriptor.getAttributeValue(answer));
       final ImageType imageType = descriptor.getImageType();
       if (ImageType.FLAG.equals(imageType)) {
           question.setImage(answer.getCode());
       }
       question.setWording(descriptor.getQuestion(answer));
       Set<CountryEntity> distractors = multipleChoice.getDistractors();
       if (!distractors.isEmpty()) {
           question.addSuggestion(descriptor.getAttributeValue(answer));
           for (CountryEntity possibleCountry : distractors) {
               question.addSuggestion(descriptor.getAttributeValue(possibleCountry));
           }
       }
       return question;
   }

}

