package fr.chaffotm.geobase.service.quiz;

import fr.chaffotm.geobase.domain.CountryEntity;
import fr.chaffotm.geobase.domain.ImageEntity;
import fr.chaffotm.geobase.domain.QuestionEntity;
import fr.chaffotm.geobase.domain.QuizEntity;
import fr.chaffotm.geobase.repository.Repository;
import fr.chaffotm.geobase.service.quiz.descriptor.ImageType;
import fr.chaffotm.geobase.service.quiz.descriptor.QuestionDescriptor;
import fr.chaffotm.geobase.service.quiz.descriptor.QuestionDescriptorService;
import fr.chaffotm.geobase.service.quiz.generator.Generator;
import fr.chaffotm.geobase.service.quiz.generator.GeneratorFactory;
import fr.chaffotm.geobase.web.domain.QuestionType;
import fr.chaffotm.geobase.web.domain.QuizConfiguration;
import fr.chaffotm.geobase.web.domain.ResponseType;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class QuizMaker {

    private final Repository repository;

    private final QuestionDescriptorService descriptorService;

    private final GeneratorFactory generatorFactory;

   public QuizMaker(final Repository repository) {
       this.repository = repository;
       descriptorService = new QuestionDescriptorService();
       generatorFactory = new GeneratorFactory();
   }

   public QuizEntity build(final QuizConfiguration configuration) {
       QuizConfiguration quizConfiguration = configuration;
       if (quizConfiguration == null) {
           quizConfiguration = buildDefaultConfiguration();
       }
       final QuestionDescriptor descriptor = descriptorService.getDescriptor(quizConfiguration.getQuestionType());
       if (ColumnType.NUMERIC.equals(descriptor.getAttributeColumnType()) && !ResponseType.MULTIPLE_CHOICE.equals(quizConfiguration.getResponseType())) {
           throw new IllegalArgumentException("Quiz supports only multiple choice");
       }
       final Generator generator = generatorFactory.getGenerator(descriptor.getAttributeColumnType());
       final List<CountryEntity> countries = descriptor.getQuizCountries(repository);
       final List<MultipleChoice> multipleChoices = generator.generate(countries, quizConfiguration.getResponseType());
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
       configuration.setResponseType(ResponseType.MULTIPLE_CHOICE);
       return  configuration;
   }

   private QuestionEntity buildQuestion(final QuestionDescriptor descriptor, final MultipleChoice multipleChoice) {
       final CountryEntity answer = multipleChoice.getAnswer();
       final QuestionEntity question = new QuestionEntity();
       question.setAnswer(descriptor.getAttributeValue(answer));
       final ImageType imageType = descriptor.getImageType();
       if (!ImageType.NONE.equals(imageType)) {
           final ImageEntity image = new ImageEntity();
           image.setUuid(UUID.randomUUID());
           image.setCountry(answer);
           image.setImageType(imageType);
           question.setImage(image);
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

