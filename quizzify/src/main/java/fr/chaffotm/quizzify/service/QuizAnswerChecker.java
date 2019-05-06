package fr.chaffotm.quizzify.service;

import fr.chaffotm.quizzify.entity.AnswerEntity;
import fr.chaffotm.quizzify.entity.QuestionEntity;
import fr.chaffotm.quizzify.entity.QuizEntity;
import fr.chaffotm.quizzify.resource.QuestionAnswer;
import fr.chaffotm.quizzify.resource.QuizAnswer;
import fr.chaffotm.quizzify.resource.QuizResult;

import java.text.Normalizer;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class QuizAnswerChecker {

    private static final Pattern NORMALIZE = Pattern.compile("[\\p{InCombiningDiacriticalMarks}]");

    public QuizResult score(final QuizEntity quizEntity, final QuizAnswer quizAnswer) {
        final List<QuestionEntity> questions = quizEntity.getQuestions();
        final List<QuestionAnswer> answers = quizAnswer.getQuestionAnswers();
        int nbOfCorrectAnswers = 0;
        for (int i = 0; i < questions.size(); i++) {
            final QuestionEntity question = questions.get(i);
            final QuestionAnswer answer = answers.get(i);
            final Set<String> correctAnswers = getCorrectAnswers(question);
            final Set<String> normalizedAnswers = getNormalizedAnswers(answer);
            if (correctAnswers.equals(normalizedAnswers)) {
                nbOfCorrectAnswers++;
            }
        }
        final QuizResult result = new QuizResult();
        result.setNbOfCorrectAnswers(nbOfCorrectAnswers);
        result.setNbOfQuestions(questions.size());
        return result;
    }

    private Set<String> getNormalizedAnswers(final QuestionAnswer questionAnswer) {
        return questionAnswer.getAnswers().stream()
                .map(this::stripAccents)
                .collect(Collectors.toSet());
    }

    private Set<String> getCorrectAnswers(final QuestionEntity question) {
        return question.getAnswers().stream()
                .filter(AnswerEntity::isCorrect)
                .map(AnswerEntity::getAnswer)
                .map(this::stripAccents)
                .collect(Collectors.toSet());
    }

    private String stripAccents(final String text) {
        final String normalize = Normalizer.normalize(text.trim(), Normalizer.Form.NFD);
        return NORMALIZE.matcher(normalize)
                .replaceAll("")
                .toLowerCase();
    }

}
