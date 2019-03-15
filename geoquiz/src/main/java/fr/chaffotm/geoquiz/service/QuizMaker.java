package fr.chaffotm.geoquiz.service;

import fr.chaffotm.geodata.entity.CountryEntity;
import fr.chaffotm.geoquiz.entity.ImageEntity;
import fr.chaffotm.geoquiz.entity.QuestionEntity;
import fr.chaffotm.geoquiz.entity.QuizEntity;
import fr.chaffotm.query.Repository;
import fr.chaffotm.geoquiz.service.descriptor.ImageType;
import fr.chaffotm.geoquiz.service.descriptor.QuestionDescriptor;
import fr.chaffotm.geoquiz.service.descriptor.QuestionDescriptorService;
import fr.chaffotm.geoquiz.service.generator.Generator;
import fr.chaffotm.geoquiz.service.generator.GeneratorFactory;
import fr.chaffotm.geoquiz.resource.QuestionType;
import fr.chaffotm.geoquiz.resource.QuizConfiguration;
import fr.chaffotm.geoquiz.resource.ResponseType;

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
        return configuration;
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

