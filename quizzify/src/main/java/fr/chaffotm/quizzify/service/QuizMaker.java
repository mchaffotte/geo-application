package fr.chaffotm.quizzify.service;

import fr.chaffotm.geodata.entity.CountryEntity;
import fr.chaffotm.querify.CriteriaRepository;
import fr.chaffotm.quizzify.entity.AnswerEntity;
import fr.chaffotm.quizzify.entity.ImageEntity;
import fr.chaffotm.quizzify.entity.QuestionEntity;
import fr.chaffotm.quizzify.entity.QuizEntity;
import fr.chaffotm.quizzify.resource.AnswerType;
import fr.chaffotm.quizzify.resource.QuizConfiguration;
import fr.chaffotm.quizzify.service.descriptor.QuestionDescriptor;
import fr.chaffotm.quizzify.service.descriptor.QuestionDescriptorService;
import fr.chaffotm.quizzify.service.generator.Generator;
import fr.chaffotm.quizzify.service.generator.GeneratorFactory;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class QuizMaker {

    private final CriteriaRepository repository;

    private final QuestionDescriptorService descriptorService;

    private final GeneratorFactory generatorFactory;

    public QuizMaker(final CriteriaRepository repository, final QuestionDescriptorService descriptorService) {
        this.repository = repository;
        this.descriptorService = descriptorService;
        generatorFactory = new GeneratorFactory();
    }

    public QuizEntity build(final QuizConfiguration configuration) {
        QuizConfiguration quizConfiguration = configuration;
        if (quizConfiguration == null) {
            quizConfiguration = buildDefaultConfiguration();
        }
        final QuestionDescriptor descriptor = descriptorService.getDescriptor(quizConfiguration.getQuestionType());
        if (ColumnType.NUMERIC == descriptor.getAttributeColumnType()
                && AnswerType.MULTIPLE_CHOICE != quizConfiguration.getAnswerType()) {
            throw new IllegalArgumentException("Quiz supports only multiple choice");
        }
        final Generator generator = generatorFactory.getGenerator(descriptor.getAttributeColumnType());
        final List<CountryEntity> countries = descriptor.getQuizCountries(repository);
        final List<MultipleChoice> multipleChoices = generator.generate(countries, quizConfiguration.getAnswerType());
        final QuizEntity quiz = new QuizEntity();
        quiz.setAnswerType(quizConfiguration.getAnswerType());
        for (MultipleChoice multipleChoice : multipleChoices) {
            QuestionEntity question = buildQuestion(descriptor, multipleChoice);
            quiz.addQuestion(question);
        }
        return quiz;
    }

    private QuizConfiguration buildDefaultConfiguration() {
        final QuizConfiguration configuration = new QuizConfiguration();
        configuration.setQuestionType(descriptorService.getDescriptors().values().iterator().next().getQuestionType());
        configuration.setAnswerType(AnswerType.MULTIPLE_CHOICE);
        return configuration;
    }

    private QuestionEntity buildQuestion(final QuestionDescriptor descriptor, final MultipleChoice multipleChoice) {
        final CountryEntity answer = multipleChoice.getAnswer();
        final QuestionEntity question = new QuestionEntity();
        question.addAnswer(build(descriptor.getAttributeValue(answer), true));
        final String imageFilename = descriptor.getQuestionImage(answer);
        if (imageFilename != null) {
            final ImageEntity image = new ImageEntity();
            image.setUuid(UUID.randomUUID());
            image.setFilename(imageFilename);
            question.setImage(image);
        }
        question.setWording(descriptor.getQuestion(answer));
        final Set<CountryEntity> distractors = multipleChoice.getDistractors();
        for (CountryEntity possibleCountry : distractors) {
            question.addAnswer(build(descriptor.getAttributeValue(possibleCountry), false));
        }
        return question;
    }

    private AnswerEntity build(final String answer, final boolean correct) {
        final AnswerEntity entity = new AnswerEntity();
        entity.setAnswer(answer);
        entity.setCorrect(correct);
        return entity;
    }

}
